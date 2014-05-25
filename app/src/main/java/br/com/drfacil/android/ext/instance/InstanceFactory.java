package br.com.drfacil.android.ext.instance;

public interface InstanceFactory<B> {

    public <T extends B> T getInstance(Class<T> type);

    public void clear();
}
