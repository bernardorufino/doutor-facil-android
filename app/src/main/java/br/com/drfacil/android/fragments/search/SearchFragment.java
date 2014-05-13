package br.com.drfacil.android.fragments.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;
import br.com.drfacil.android.fragments.search.parameters.*;
import br.com.drfacil.android.helpers.CustomHelper;
import br.com.drfacil.android.helpers.FuturesHelper;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.search.Search;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

public class SearchFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Professional>> {

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.search_label,
            SearchFragment.class);

    private static final Map<Integer, Class<? extends SearchParameterFragment>> PARAMS_FRAGMENTS = ImmutableMap
            .<Integer, Class<? extends SearchParameterFragment>>builder()
            .put(R.id.search_parameter_specialy, SearchParameterSpecialtyFragment.class)
            .put(R.id.search_parameter_date, SearchParameterDateFragment.class)
            .put(R.id.search_parameter_location, SearchParameterLocationFragment.class)
            .put(R.id.search_parameter_insurance, SearchParameterInsuranceFragment.class)
            .build();

    private static final int SEARCH_RESULTS_LOADER = 0;

    private AbsListView vSearchResults;
    private SearchResultsAdapter mResultsAdapter;
    private final Search mSearch = new Search();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vSearchResults = (GridView) getView().findViewById(R.id.search_results);
        for (int id : PARAMS_FRAGMENTS.keySet()) {
            getView().findViewById(id).setOnClickListener(mOnParamClickListener);
        }
        mResultsAdapter = new SearchResultsAdapter(getActivity());
        vSearchResults.setAdapter(mResultsAdapter);
    }


    private FutureCallback<SearchParameterFragment> mOnParamChangeCallback =
            new FutureCallback<SearchParameterFragment>() {
        @Override
        public void onSuccess(SearchParameterFragment result) {
            if (result == null) return; // Just dismissed
            /* TODO: Update search */
            Toast.makeText(getActivity(), "Update", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onFailure(Throwable t) {
            CustomHelper.logException("Error on parameter change", t);
        }
    };

    private final View.OnClickListener mOnParamClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomHelper.log("Search Parameter click");
            Class<? extends SearchParameterFragment> clazz = PARAMS_FRAGMENTS.get(v.getId());
            checkState(clazz != null, "Unidentified click handled as search parameter click");
            SearchParameterFragment fragment = SearchParameterFragment.show(mSearch, clazz, getFragmentManager());
            ListenableFuture<SearchParameterFragment> future = fragment.getTaskFuture();
            FuturesHelper.addCallbackOnUiThread(future, mOnParamChangeCallback);
        }
    };

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
