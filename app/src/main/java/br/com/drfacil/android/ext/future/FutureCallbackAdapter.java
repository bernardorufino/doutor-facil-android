package br.com.drfacil.android.ext.future;

import com.google.common.util.concurrent.FutureCallback;

public abstract class FutureCallbackAdapter<V> implements FutureCallback<V> {

    @Override
    public void onSuccess(V result) {
        onFinish(true);
    }

    @Override
    public void onFailure(Throwable t) {
        onFinish(false);
    }

    public void onFinish(boolean success) {
        /* Override */
    }
}
