package br.com.drfacil.android.ext.future;

import java.util.concurrent.Callable;

/**
 * To be used when we need to cancel an operation which cannot handle thread interrupts
 *
 * @param <V>
 */
public interface CancelableCallable<V> extends Callable<V> {

    public void cancel();
}
