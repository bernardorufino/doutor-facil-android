package br.com.drfacil.android.ext.observing;

public interface Observable {

    public void registerObserver(Observer observer);

    public void unregisterObserver(Observer observer);
}
