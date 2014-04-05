package br.com.drfacil.android.ext.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.helpers.CustomHelper;
import br.com.drfacil.android.helpers.FuturesHelper;
import com.google.common.base.Objects;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;

/* TODO: Placeholder */
public class UrlImageView extends ImageView {

    public static final int CACHE_SIZE = 16 * 1024; // In KB
    private static final Downloader<CachedBitmapDownloader.BitmapInfo> DEFAULT_DOWNLOADER =
            new CachedBitmapDownloader(CACHE_SIZE);

    public static final int FADE_DURATION = 400;

    private String mUrl;
    private ColorDrawable mPlaceholderDrawable = new ColorDrawable(0x00000000);
    private Downloader<CachedBitmapDownloader.BitmapInfo> mDownloader = DEFAULT_DOWNLOADER;
    private ListenableFuture<CachedBitmapDownloader.BitmapInfo> mFuture;

    public UrlImageView(Context context) {
        super(context);
    }

    public UrlImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public UrlImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.UrlImageView);
        try {
            setUrl(array.getString(R.styleable.UrlImageView_url));
        } finally {
            array.recycle();
        }
        if (getDrawable() == null) {
            setImageDrawable(mPlaceholderDrawable);
        }
    }

    public void setDownloader(Downloader<CachedBitmapDownloader.BitmapInfo> downloader) {
        mDownloader = downloader;
    }

    public void setUrl(String url) {
        if (Objects.equal(mUrl, url)) return;
        mUrl = url;
        setPlaceholder();
        if (url == null) return;
        if (mFuture != null) {
            mFuture.cancel(true);
        }
        mFuture = mDownloader.download(url);
        FuturesHelper.addCallbackOnUiThread(mFuture, new ImageDownloadedCallback(url));
    }

    public String getUrl() {
        return mUrl;
    }

    private void setPlaceholder() {
        setImageDrawable(mPlaceholderDrawable);
    }

    private class ImageDownloadedCallback implements FutureCallback<CachedBitmapDownloader.BitmapInfo> {

        private final String mUrl;

        public ImageDownloadedCallback(String url) {
            mUrl = url;
        }

        @Override
        public void onSuccess(CachedBitmapDownloader.BitmapInfo bitmapInfo) {
            if (onFinish()) return;
            if (bitmapInfo.origin == CachedBitmapDownloader.BitmapOrigin.INTERNET) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmapInfo.bitmap);
                Drawable[] layers = { getDrawable(), drawable };
                TransitionDrawable transition = new TransitionDrawable(layers);
                setImageDrawable(transition);
                transition.startTransition(FADE_DURATION);
            } else {
                setImageBitmap(bitmapInfo.bitmap);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (onFinish()) return;
            CustomHelper.logException(t);
            setPlaceholder();
        }

        private boolean onFinish() {
            mFuture = null;
            return !mUrl.equals(UrlImageView.this.mUrl);
        }

    }
}
