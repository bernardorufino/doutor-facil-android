package br.com.drfacil.android.ext.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import br.com.drfacil.android.helpers.CustomHelper;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class CachedBitmapDownloader extends LruCache<String, Bitmap>
        implements Downloader<CachedBitmapDownloader.BitmapInfo> {

    private static final int RETRY_TIMES = 3;

    public CachedBitmapDownloader(int maxSizeInKb) {
        super(maxSizeInKb);
    }

    @Override
    protected Bitmap create(String url) {
        for (int i = 1; i <= RETRY_TIMES; i++) {
            try {
                CustomHelper.log("Attempt #" + i + " of downloading bitmap");
                URLConnection connection = new URL(url).openConnection();
                connection.setUseCaches(true);
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                if (bitmap != null) {
                    CustomHelper.log("Returning bitmap");
                    return bitmap;
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
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        oldValue.recycle();
    }

    public boolean contains(String url) {
        return snapshot().containsKey(url);
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
            BitmapOrigin origin = CachedBitmapDownloader.this.contains(url)
                    ? BitmapOrigin.MEMORY_CACHE
                    : BitmapOrigin.INTERNET;
            Bitmap bitmap = CachedBitmapDownloader.this.get(url);
            if (bitmap != null) {
                mFuture.set(new BitmapInfo(bitmap, origin));
            } else {
                mFuture.setException(new RuntimeException("Bitmap returned from " + origin + " is null"));
            }
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

    public static enum BitmapOrigin { MEMORY_CACHE, INTERNET }
}
