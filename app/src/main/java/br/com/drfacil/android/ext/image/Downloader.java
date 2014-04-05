package br.com.drfacil.android.ext.image;

import com.google.common.util.concurrent.ListenableFuture;

public interface Downloader<T> {

    public ListenableFuture<T> download(String url);

}
