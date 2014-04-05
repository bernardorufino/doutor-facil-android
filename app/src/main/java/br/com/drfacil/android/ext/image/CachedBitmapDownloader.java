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

    public CachedBitmapDownloader(int maxSizeInKb) {
        super(maxSizeInKb);
    }

    @Override
    protected Bitmap create(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setUseCaches(true);
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            CustomHelper.logException(e);
            return null;
        }
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
        new FetchBitmapTask(future).execute(url);
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
                mFuture.setException(new RuntimeException("Bitmap returned is null"));
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
