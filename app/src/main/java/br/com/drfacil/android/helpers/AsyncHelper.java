package br.com.drfacil.android.helpers;

import android.os.AsyncTask;
import android.os.Looper;
import br.com.drfacil.android.ext.future.AndroidFutureCallbackWrapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.Callable;

public class AsyncHelper {

    public static <V> ListenableFuture<V> executeTask(final Callable<V> callable) {
        final SettableFuture<V> future = SettableFuture.create();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    V ans = callable.call();
                    future.set(ans);
                } catch (Exception e) {
                    future.setException(e);
                }
            }
        });
        return future;
    }

    public static ListenableFuture<Void> executeTask(final Runnable runnable) {
        return executeTask(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runnable.run();
                return null;
            }
        });
    }

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
    private AsyncHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
