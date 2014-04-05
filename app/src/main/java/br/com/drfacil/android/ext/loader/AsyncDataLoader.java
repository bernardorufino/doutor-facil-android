package br.com.drfacil.android.ext.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class AsyncDataLoader<D> extends AsyncTaskLoader<D> {

    public AsyncDataLoader(Context context) {
        super(context);
    }

    private D mData = null;
    private boolean mObserving = false;

    // Even if it's a no-op, it's good to enforce implementing this method
    protected abstract void releaseResources(D data);

    protected abstract void startObserving();

    protected abstract void stopObserving();

    @Override
    public void deliverResult(D data) {
        if (isReset()) {
            releaseResources(data);
            return;
        }
        D oldData = mData;
        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }
        if (!mObserving) {
            startObserving();
            mObserving = true;
        }
        // if registeredObserver is null then startObserving()
        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        // Still monitoring data source, as it should be
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
        if (mObserving) {
            mObserving = false;
            stopObserving();
        }
    }

    @Override
    public void onCanceled(D data) {
        super.onCanceled(data);
        releaseResources(data);
    }
}
