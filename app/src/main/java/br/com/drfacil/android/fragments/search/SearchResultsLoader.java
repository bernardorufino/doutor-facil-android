package br.com.drfacil.android.fragments.search;

import android.content.Context;
import br.com.drfacil.android.Hardcoded;
import br.com.drfacil.android.ext.loader.AsyncDataLoader;
import br.com.drfacil.android.model.Professional;

import java.util.List;

public class SearchResultsLoader extends AsyncDataLoader<List<Professional>> {

    public SearchResultsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Professional> loadInBackground() {
        return Hardcoded.PROFESSIONALS;
    }

    @Override
    protected void releaseResources(List<Professional> data) {
        /* No-op */
    }

    @Override
    protected void startObserving() {
        /* No-op */
    }

    @Override
    protected void stopObserving() {
        /* No-op */
    }
}
