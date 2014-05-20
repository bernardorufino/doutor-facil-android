package br.com.drfacil.android.ext.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import br.com.drfacil.android.Params;
import br.com.drfacil.android.ext.cache.AbstractTwoLevelCache;
import br.com.drfacil.android.helpers.CustomHelper;
import com.google.common.base.Throwables;
import com.google.common.io.BaseEncoding;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CachedBitmapDownloader
        extends AbstractTwoLevelCache<String, Bitmap>
        implements Downloader<CachedBitmapDownloader.BitmapInfo> {

    private static final int DISK_CACHE_SIZE = 16 * 1024 * 1024; // In bytes
    private static final float MEMORY_CACHE_PER_RAM_RATIO = 1/8f; // In bytes
    private static final int RETRY_TIMES = 3;

    private static CachedBitmapDownloader sInstance;

    public static CachedBitmapDownloader getInstance(Context context) {
        if (sInstance == null) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            int memoryCacheSize = (int) (maxMemory * MEMORY_CACHE_PER_RAM_RATIO);
            sInstance = new CachedBitmapDownloader(
                    context.getCacheDir(),
                    Params.APP_VERSION,
                    DISK_CACHE_SIZE,
                    memoryCacheSize);
        }
        return sInstance;
    }

    private CachedBitmapDownloader(
            File diskCacheDirectory,
            int appVersion,
            int maxDiskSizeInBytes,
            int maxMemorySizeInBytes) {
        super(diskCacheDirectory, appVersion, maxDiskSizeInBytes, maxMemorySizeInBytes);
    }

    @Override
    protected ValueHolder<Bitmap> create(String url) {
        for (int i = 1; i <= RETRY_TIMES; i++) {
            try {
                CustomHelper.log("Attempt #" + i + " of downloading bitmap");
                URLConnection connection = new URL(url).openConnection();
                connection.setUseCaches(true);
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                if (bitmap != null) {
                    CustomHelper.log("Returning bitmap");
                    return new ValueHolder<>(bitmap, bitmap.getByteCount());
                }
                CustomHelper.log("null bitmap on attempt #" + i);
            } catch (IOException e) {
                CustomHelper.log("Error on attempt #" + i);
                CustomHelper.logException(e);
            }
        }
        return null;
    }

    @Override
    protected String hash(String url) {
        MessageDigest md;
        try {
             md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            AssertionError error = new AssertionError();
            error.initCause(e);
            throw error;
        }
        byte[] digest = md.digest(url.getBytes());
        String hash = BaseEncoding.base32().lowerCase().encode(digest);
        return hash.replaceAll("[^a-z0-9_-]", "-");
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        CustomHelper.log("<BMP> " + hash(key) + " recycled");
        oldValue.recycle();
    }

    @Override
    protected ValueHolder<Bitmap> fromStreamToValue(InputStream stream) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return new ValueHolder<>(bitmap, bitmap.getByteCount());
    }

    @Override
    protected void writeToStream(Bitmap bitmap, OutputStream outputStream) throws IOException {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
    }

    @Override
    protected void handleDiskException(IOException e) {
        Throwables.propagate(e);
//        CustomHelper.logException("Disk cache exception!", e);
    }

    @Override
    public ListenableFuture<BitmapInfo> download(String url) {
        SettableFuture<BitmapInfo> future = SettableFuture.create();
        new FetchBitmapTask(future).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        return future;
    }

    private class FetchBitmapTask extends AsyncTask<String, Void, Void> {

        private final SettableFuture<BitmapInfo> mFuture;

        public FetchBitmapTask(SettableFuture<BitmapInfo> future) {
            mFuture = future;
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            Location location = contains(url);
            BitmapOrigin origin = locationToOrigin(location);
            Bitmap bitmap = CachedBitmapDownloader.this.get(url);
            if (bitmap != null) {
                mFuture.set(new BitmapInfo(bitmap, origin));
            } else {
                mFuture.setException(new RuntimeException("Bitmap returned from " + origin + " is null"));
            }
            CustomHelper.log("Returning bitmap " + hash(url) + " from " + origin);
            return null;
        }
    }

    public static class BitmapInfo {

        public final Bitmap bitmap;
        public final BitmapOrigin origin;

        private BitmapInfo(Bitmap bitmap, BitmapOrigin origin) {
            this.bitmap = bitmap;
            this.origin = origin;
        }
    }

    private static BitmapOrigin locationToOrigin(Location location) {
        switch (location) {
            case MEMORY: return BitmapOrigin.MEMORY_CACHE;
            case DISK: return BitmapOrigin.DISK_CACHE;
            case NOT_IN_CACHE: return BitmapOrigin.INTERNET;
        }
        throw new AssertionError();
    }

    public static enum BitmapOrigin { MEMORY_CACHE, DISK_CACHE, INTERNET }
}
