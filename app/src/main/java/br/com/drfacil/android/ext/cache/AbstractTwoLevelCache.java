package br.com.drfacil.android.ext.cache;

import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import com.google.common.io.Closer;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractTwoLevelCache<K, V> {

    private final Map<String, Lock> mLocks = new HashMap<>();
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final LruCache<K, ValueHolder<V>> mMemoryCache;
    private final DiskLruCache mDiskCache;

    public AbstractTwoLevelCache(
            File diskCacheDirectory,
            int appVersion,
            int maxDiskSizeInBytes,
            int maxMemorySizeInBytes) {
        mMemoryCache = new MemoryCache(maxMemorySizeInBytes);
        DiskLruCache diskCache;
        try {
            diskCache = DiskLruCache.open(diskCacheDirectory, appVersion, 1, maxDiskSizeInBytes);
        } catch (IOException e) {
            diskCache = null;
        }
        mDiskCache = diskCache;
    }

    protected abstract ValueHolder<V> create(K key);

    protected abstract ValueHolder<V> fromStreamToValue(InputStream stream) throws IOException;

    protected abstract void writeToStream(V value, OutputStream outputStream) throws IOException;

    protected abstract String hash(K key);

    /* Override */
    protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
        /* Empty */
    }

    /* Override */
    protected void handleDiskException(IOException e) {
        /* Silently works without disk cache */
    }

    public Location contains(K key) {
        if (mMemoryCache.snapshot().containsKey(key)) return Location.MEMORY;
        try {
            String hashedKey = hash(key);
            if (mDiskCache != null && mDiskCache.get(hashedKey) != null) {
                return Location.DISK;
            }
        } catch (IOException e) {
            /* Empty */
        }
        return Location.NOT_IN_CACHE;
    }

    private Lock getDiskLock(String hashedKey) {
        checkState(mDiskCache != null);

        Lock lock = mLocks.get(hashedKey);
        if (lock == null) {
            lock = new ReentrantLock();
            mLocks.put(hashedKey, lock);
        }
        return lock;
    }

    public V get(K key) {
        return mMemoryCache.get(key).value;
    }

    private void postStoreOnDisk(String hashedKey, V value) {
        checkState(mDiskCache != null);

        //noinspection unchecked
        new StoreOnDiskTask(hashedKey, value).executeOnExecutor(mExecutor);
    }

    private class StoreOnDiskTask extends AsyncTask<Void, Void, Void> {

        private final String mHashedKey;
        private final V mValue;

        private StoreOnDiskTask(String hashedKey, V value) {
            mHashedKey = hashedKey;
            mValue = value;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Because of sync need https://code.google.com/p/guava-libraries/wiki/ClosingResourcesExplained#Closer
            try {
                Closer closer = Closer.create();
                DiskLruCache.Editor editor = null;
                try {
                    getDiskLock(mHashedKey).lock();
                    editor = mDiskCache.edit(mHashedKey);
                    checkState(editor != null, "Multiple edits at same time, not synchronized");
                    // 0 because there is only one value per key
                    OutputStream outputStream = editor.newOutputStream(0);
                    closer.register(outputStream);
                    writeToStream(mValue, outputStream);
                    editor.commit();
                } catch (Throwable t) {
                    throw closer.rethrow(t);
                } finally {
                    if (editor != null) editor.abortUnlessCommitted();
                    getDiskLock(mHashedKey).unlock();
                    closer.close();
                }
            } catch (IOException e) {
                handleDiskException(e);
            }
            return null;
        }
    }

    private class MemoryCache extends LruCache<K, ValueHolder<V>> {

        public MemoryCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected void entryRemoved(boolean evicted, K key, ValueHolder<V> oldValue, ValueHolder<V> newValue) {
            AbstractTwoLevelCache.this.entryRemoved(evicted, key, oldValue.value, newValue.value);
        }

        public ValueHolder<V> tryGetFromDisk(String hashedKey) {
            ValueHolder<V> valueHolder = null;
            Closer closer = Closer.create();
            // Because of sync need https://code.google.com/p/guava-libraries/wiki/ClosingResourcesExplained#Closer
            try {
                try {
                    DiskLruCache.Snapshot snapshot;
                    getDiskLock(hashedKey).lock();
                    snapshot = mDiskCache.get(hashedKey);
                    closer.register(snapshot);
                    if (snapshot != null) {
                        InputStream inputStream = snapshot.getInputStream(0);
                        valueHolder = fromStreamToValue(inputStream);
                    }
                } catch (Throwable t) {
                    throw closer.rethrow(t);
                } finally {
                    getDiskLock(hashedKey).unlock();
                    closer.close();
                }
            } catch (IOException e) {
                handleDiskException(e);
            }
            return valueHolder;
        }

        @Override
        protected ValueHolder<V> create(K key) {
            String hashedKey = hash(key);
            ValueHolder<V> valueHolder;

            // Try to get from the disk
            if (mDiskCache != null) {
                valueHolder = tryGetFromDisk(hashedKey);
                if (valueHolder != null) return valueHolder;
            }

            // If it's not on the disk or it failed, we create it and try to put on disk
            valueHolder = AbstractTwoLevelCache.this.create(key);
            if (mDiskCache != null) {
                // Post to another thread since we no longer have to wait for the disk
                postStoreOnDisk(hashedKey, valueHolder.value);
            }
            return valueHolder;
        }

        @Override
        protected int sizeOf(K key, ValueHolder<V> value) {
            return value.bytes;
        }
    }

    public static class ValueHolder<V> {

        public V value;
        public int bytes;

        public ValueHolder(V value, int bytes) {
            this.value = value;
            this.bytes = bytes;
        }
    }

    public static enum Location { MEMORY, DISK, NOT_IN_CACHE }
}
