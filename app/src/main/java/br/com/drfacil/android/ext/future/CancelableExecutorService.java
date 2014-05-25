package br.com.drfacil.android.ext.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.ExecutorService;

/**
 * ExecutorService decorator for cancelable tasks created with CancelableCallable
 */
public class CancelableExecutorService extends ForwardingListeningExecutorService {

    public static CancelableExecutorService decorate(ListeningExecutorService executorService) {
        return new CancelableExecutorService(executorService);
    }

    public static CancelableExecutorService decorate(ExecutorService executorService) {
        return new CancelableExecutorService(MoreExecutors.listeningDecorator(executorService));
    }

    private final ListeningExecutorService mDelegate;

    private CancelableExecutorService(ListeningExecutorService executorService) {
        mDelegate = executorService;
    }

    @Override
    protected ListeningExecutorService delegate() {
        return mDelegate;
    }

    public <T> ListenableFuture<T> submit(CancelableCallable<T> task) {
        ListenableFuture<T> future = super.submit(task);
        return new CancelableFuture<>(future, task);
    }

    private static class CancelableFuture<V> extends ForwardingListenableFuture<V> {

        private final ListenableFuture<V> mDelegate;
        private final CancelableCallable<V> mCallable;

        private CancelableFuture(ListenableFuture<V> listenableFuture, CancelableCallable<V> callable) {
            mDelegate = listenableFuture;
            mCallable = callable;
        }

        @Override
        protected ListenableFuture<V> delegate() {
            return mDelegate;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            mCallable.cancel();
            return super.cancel(mayInterruptIfRunning);
        }
    }
}
