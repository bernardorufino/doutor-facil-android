package br.com.drfacil.android.ext.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import br.com.drfacil.android.Params;
import br.com.drfacil.android.ext.cache.AbstractTwoLevelCache;
import br.com.drfacil.android.helpers.AsyncHelper;
import br.com.drfacil.android.helpers.CacheHelper;
import br.com.drfacil.android.helpers.CustomHelper;
import com.google.common.base.Throwables;
import com.google.common.io.BaseEncoding;
import com.google.common.util.concurrent.ListenableFuture;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class CachedBitmapDownloader
        extends AbstractTwoLevelCache<String, Bitmap>
        implements Downloader<CachedBitmapDownloader.BitmapInfo> {

    private static final int DISK_CACHE_SIZE = 16 * 1024 * 1024; // In bytes
    private static final float MEMORY_CACHE_PER_RAM_RATIO = 1/8f;
    private static final int RETRY_TIMES = 3;

    private static CachedBitmapDownloader sInstance;

    public synchronized static CachedBitmapDownloader getInstance(Context context) {
        if (sInstance == null) {
            long maxMemory = Runtime.getRuntime().maxMemory();
            int memoryCacheSize = (int) (maxMemory * MEMORY_CACHE_PER_RAM_RATIO);
            CustomHelper.log("Using " + (memoryCacheSize/1024) + " KB of memory");
            sInstance = new CachedBitmapDownloader(
                    CacheHelper.createCacheDir(context, CachedBitmapDownloader.class.toString()),
                    Params.APP_VERSION,
                    DISK_CACHE_SIZE,
                    memoryCacheSize);
        }
        return sInstance;
    }

    private OkHttpClient mClient = new OkHttpClient();
    private Map<String, ListenableFuture<BitmapInfo>> mPendingRequests = new ConcurrentHashMap<>();

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
            HttpURLConnection connection = null;
            try {
                connection = mClient.open(new URL(url));
                CustomHelper.log("Attempt #" + i + " of downloading bitmap");
                InputStream inputStream = null;
                try {
                    inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap != null) {
                        return new ValueHolder<>(bitmap, bitmap.getByteCount());
                    }
                    CustomHelper.log("null bitmap on attempt #" + i);
                } finally {
                    if (inputStream != null) inputStream.close();
                }
            } catch (IOException e) {
                CustomHelper.log("Error on attempt #" + i);
                CustomHelper.logException(e);
            } finally {
                if (connection != null) connection.disconnect();
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
        /* TODO: Was causing "IllegalStateException: Can't compress a recycled bitmap" on writeToStream, make it work with recycling */
        //oldValue.recycle();
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
    }

    @Override
    public ListenableFuture<BitmapInfo> download(final String url) {
        ListenableFuture<BitmapInfo> future = mPendingRequests.get(url);
        if (future != null) return future;
        future = AsyncHelper.executeTask(new FetchBitmapTask(url));
        mPendingRequests.put(url, future);
        return future;
    }

    private class FetchBitmapTask implements Callable<BitmapInfo> {

        private String mUrl;

        private FetchBitmapTask(String url) {
            mUrl = url;
        }

        @Override
        public BitmapInfo call() throws Exception {
            Location location = contains(mUrl);
            BitmapOrigin origin = locationToOrigin(location);
            Bitmap bitmap = CachedBitmapDownloader.this.get(mUrl);
            mPendingRequests.remove(mUrl);
            if (bitmap != null) {
                CustomHelper.log("Returning bitmap " + hash(mUrl) + " from " + origin);
                return new BitmapInfo(bitmap, origin);
            } else {
                throw new RuntimeException("Bitmap returned from " + origin + " is null");
            }
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
