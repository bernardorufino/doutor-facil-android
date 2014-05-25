package br.com.drfacil.android.fragments.search;

import android.content.Context;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.ProfessionalApi;
import br.com.drfacil.android.ext.loader.AsyncDataLoader;
import br.com.drfacil.android.ext.observing.Observable;
import br.com.drfacil.android.ext.observing.Observer;
import br.com.drfacil.android.helpers.CustomHelper;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.search.Search;
import retrofit.RetrofitError;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class SearchResultsLoader extends AsyncDataLoader<SearchResultsResponse> implements Observer {

    private final Search mSearch;

    private int mCounter = 0;

    public SearchResultsLoader(Context context, Search search) {
        super(context);
        mSearch = search;
    }

    @Override
    public SearchResultsResponse loadInBackground() {
        ProfessionalApi api = ApiManager.getInstance(getContext()).getApi(ProfessionalApi.class);
        try {

            CustomHelper.log("triggered search request " + mCounter);
            List<Professional> profs = api.search(
                    mSearch.getSpecialtyIdsAsCsv(),
                    mSearch.getLocation(),
                    mSearch.getStartDate(),
                    mSearch.getEndDate(),
                    mSearch.getInsuranceIdsAsCsv());
            CustomHelper.log("request " + (mCounter++) + " returned");
            return new SearchResultsResponse(profs);
        } catch (RetrofitError error) {
            CustomHelper.logException(error);
            return new SearchResultsResponse(getContext(), error);
        }
    }

    @Override
    protected void releaseResources(SearchResultsResponse data) {
        /* No-op */
    }

    @Override
    protected void startObserving() {
        mSearch.registerObserver(this);
    }

    @Override
    protected void stopObserving() {
        mSearch.unregisterObserver(this);
    }

    @Override
    public void onChange(Observable subject) {
        checkState(subject == mSearch, "Observable is not the Search object provided for the loader");
        onContentChanged();
    }
}
