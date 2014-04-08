package br.com.drfacil.android.ext.future;

import android.os.Handler;
import android.os.Looper;
import com.google.common.util.concurrent.FutureCallback;

public class AndroidFutureCallbackWrapper<V> implements FutureCallback<V> {

    private final Handler mHandler;
    private FutureCallback<V> mCallback;

    public AndroidFutureCallbackWrapper(FutureCallback<V> callback, Handler handler) {
        mCallback = callback;
        mHandler = handler;
    }

    public AndroidFutureCallbackWrapper(FutureCallback<V> callback, Looper looper) {
        mCallback = callback;
        mHandler = new Handler(looper);
    }

    @Override
    public void onSuccess(final V result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccess(result);
            }
        });
    }

    @Override
    public void onFailure(final Throwable t) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onFailure(t);
            }
        });
    }
}
