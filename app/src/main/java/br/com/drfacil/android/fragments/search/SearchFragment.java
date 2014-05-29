package br.com.drfacil.android.fragments.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;
import br.com.drfacil.android.ext.instance.InstanceFactory;
import br.com.drfacil.android.ext.instance.LazyWeakFactory;
import br.com.drfacil.android.ext.observing.Observable;
import br.com.drfacil.android.ext.observing.Observer;
import br.com.drfacil.android.fragments.search.parameters.*;
import br.com.drfacil.android.helpers.AsyncHelper;
import br.com.drfacil.android.helpers.CustomHelper;
import br.com.drfacil.android.helpers.CustomViewHelper;
import br.com.drfacil.android.ext.scroll.BottomViewToggleOnScrollListener;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.search.Search;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class SearchFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<SearchResultsResponse>, Observer {

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.search_label,
            SearchFragment.class);

    private static final Map<Integer, Class<? extends SearchParameterFragment>> PARAMS_FRAGMENTS = ImmutableMap
            .<Integer, Class<? extends SearchParameterFragment>>builder()
            .put(R.id.search_parameter_specialty, SearchParameterSpecialtyFragment.class)
            .put(R.id.search_parameter_date, SearchParameterDateFragment.class)
            .put(R.id.search_parameter_location, SearchParameterLocationFragment.class)
            .put(R.id.search_parameter_insurance, SearchParameterInsuranceFragment.class)
            .build();

    private static final String SEARCH_BUNDLE_KEY = Search.class.toString();
    private static final int SEARCH_RESULTS_LOADER = 0;

    private AbsListView vSearchResults;
    private View vSearchParametersContainer;
    private SearchResultsAdapter mResultsAdapter;
    private Search mSearch;
    private InstanceFactory<SearchParameterFragment> mFragmentFactory;
    private ProgressBar vLoadingSpin;
    private Button vRetryButton;
    private ViewGroup vRetryContainer;
    private ViewGroup vLoadingContainer;
    private TextView vRetryMessage;
    private ListenableFuture<Void> mParamsFuture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mSearch = savedInstanceState.getParcelable(SEARCH_BUNDLE_KEY);
        }
        if (mSearch == null) {
            mSearch = new Search();
            mSearch.registerObserver(this);
            mParamsFuture = mSearch.permitEverything(getActivity());
        } else {
            mSearch.registerObserver(this);
        }
        mFragmentFactory = new LazyWeakFactory.WithFixedArgumentBuilder()
                .argument(Search.class, mSearch)
                .build();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vRetryContainer = (ViewGroup) getView().findViewById(R.id.retry_container);
        vRetryMessage = (TextView) getView().findViewById(R.id.retry_message);
        vRetryButton = (Button) getView().findViewById(R.id.retry_button);
        vRetryButton.setOnClickListener(mOnRetryClickListener);
        vLoadingContainer = (ViewGroup) getView().findViewById(R.id.loading_container);
        vLoadingSpin = (ProgressBar) getView().findViewById(R.id.loading_spin);
        vSearchResults = (GridView) getView().findViewById(R.id.search_results);
        for (int id : PARAMS_FRAGMENTS.keySet()) {
            getView().findViewById(id).setOnClickListener(mOnParamClickListener);
        }
        mResultsAdapter = new SearchResultsAdapter(getActivity());
        vSearchResults.setAdapter(mResultsAdapter);
        setSearchViewState(SearchViewState.LOADING);
        vSearchParametersContainer = getView().findViewById(R.id.search_parameters);
        BottomViewToggleOnScrollListener listener = new BottomViewToggleOnScrollListener(vSearchParametersContainer)
                .setReversed(true);
        vSearchResults.setOnScrollListener(listener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SEARCH_RESULTS_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SEARCH_BUNDLE_KEY, mSearch);
    }

    public void setSearchViewState(SearchViewState state) {
        CustomViewHelper.toggleVisibleGone(vLoadingContainer, state == SearchViewState.LOADING);
        CustomViewHelper.toggleVisibleGone(vSearchResults, state == SearchViewState.RESULTS);
        CustomViewHelper.toggleVisibleGone(vRetryContainer, state == SearchViewState.RETRY);
    }

    private final View.OnClickListener mOnRetryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /* TODO: You can do better than this */
            mSearch.triggerUpdate();
        }
    };

    private final View.OnClickListener mOnParamClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Class<? extends SearchParameterFragment> type = PARAMS_FRAGMENTS.get(v.getId());
            checkState(type != null, "Unidentified click handled as search parameter click");
            mFragmentFactory.clear();
            SearchParameterFragment fragment = mFragmentFactory.getInstance(type);
            fragment.show(getFragmentManager(), type.toString());
        }
    };

    @Override
    public Loader<SearchResultsResponse> onCreateLoader(int loader, Bundle args) {
        switch (loader) {
            case SEARCH_RESULTS_LOADER:
                return new SearchResultsLoader(getActivity(), mSearch);
        }
        throw new AssertionError("Unknown loader code " + loader);
    }

    @Override
    public void onLoadFinished(Loader<SearchResultsResponse> loader, SearchResultsResponse response) {
        if (response.isSuccess()) {
            List<Professional> professionals = response.getProfessionals();
            if (professionals.isEmpty() && !mParamsFuture.isDone()) {
                // Leave it on loading state since we have an outstanding request for the parameters, which
                // will update the results once it's done
                // return;
                // Have to account for the case where the outstanding requests don't return or return with errors
            }
            CustomHelper.log("finished loading search, size = " + professionals.size());
            setSearchViewState(SearchViewState.RESULTS);
            mResultsAdapter.update(professionals);
        } else {
            CustomHelper.log("finished loading search, error status = " + response.getStatus() + ", msg = " + response.getMessage());
            setSearchViewState(SearchViewState.RETRY);
            vRetryMessage.setText(response.getMessage());
        }
    }

    @Override
    public void onLoaderReset(Loader<SearchResultsResponse> loader) {
        mResultsAdapter.update(Collections.<Professional>emptyList());
    }

    @Override
    public void onChange(Observable subject) {
        checkArgument(subject == mSearch);
        CustomHelper.log("search change detected");
        // Because calls to onChange() come from Observable, thus this is not necessarily on the UI thread
        AsyncHelper.executeOnUiThread(new Runnable() {
            @Override
            public void run() {
                setSearchViewState(SearchViewState.LOADING);
            }
        });
    }

    private static enum SearchViewState { RESULTS, LOADING, RETRY }
}
