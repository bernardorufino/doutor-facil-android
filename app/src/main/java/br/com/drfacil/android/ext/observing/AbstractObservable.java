package br.com.drfacil.android.ext.observing;

import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class AbstractObservable implements Observable {

    private Collection<Observer> mObservers = new LinkedHashSet<>();

    @Override
    public void registerObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        mObservers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer observer : mObservers) observer.onChange(this);
    }
}
