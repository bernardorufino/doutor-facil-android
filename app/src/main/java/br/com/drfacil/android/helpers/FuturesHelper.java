package br.com.drfacil.android.helpers;

import android.os.Looper;
import br.com.drfacil.android.ext.future.AndroidFutureCallbackWrapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class FuturesHelper {

    public static <V> void addCallbackOnLooper(
            ListenableFuture<V> future,
            FutureCallback<? super V> callback,
            Looper looper) {
        Futures.addCallback(future, new AndroidFutureCallbackWrapper<>(callback, looper));
    }

    public static <V> void addCallbackOnUiThread(
            ListenableFuture<V> future,
            FutureCallback<? super V> callback) {
        addCallbackOnLooper(future, callback, Looper.getMainLooper());
    }

    // Prevents instantiation
    private FuturesHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
