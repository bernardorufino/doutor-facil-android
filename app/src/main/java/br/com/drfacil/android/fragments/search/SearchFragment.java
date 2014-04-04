package br.com.drfacil.android.fragments.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.Professional;

import java.util.Collections;
import java.util.List;

public class SearchFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Professional>> {

    private static final int SEARCH_RESULTS_LOADER = 0;

    private ListView vSearchResults;
    private SearchResultsAdapter mResultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vSearchResults = (ListView) getView().findViewById(R.id.search_results);
        mResultsAdapter = new SearchResultsAdapter(getActivity());
        vSearchResults.setAdapter(mResultsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SEARCH_RESULTS_LOADER, null, this);
    }

    @Override
    public Loader<List<Professional>> onCreateLoader(int loader, Bundle args) {
        switch (loader) {
            case SEARCH_RESULTS_LOADER:
                return new SearchResultsLoader(getActivity());
        }
        throw new AssertionError("Unknown loader code " + loader);
    }

    @Override
    public void onLoadFinished(Loader<List<Professional>> loader, List<Professional> results) {
        mResultsAdapter.update(results);
    }

    @Override
    public void onLoaderReset(Loader<List<Professional>> loader) {
        mResultsAdapter.update(Collections.<Professional>emptyList());
    }
}
