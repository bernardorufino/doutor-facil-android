package br.com.drfacil.android.fragments.search.parameters;

import br.com.drfacil.android.ext.dialog.ConfirmableDialogFragment;
import br.com.drfacil.android.model.search.Search;
import com.google.common.util.concurrent.ListenableFuture;

public class SearchParameterFragment extends ConfirmableDialogFragment {

    private final Search mSearch;

    public SearchParameterFragment(Search search) {
        mSearch = search;
    }

    public Search getSearch() {
        return mSearch;
    }

    @SuppressWarnings("unchecked")
    public ListenableFuture<SearchParameterFragment> getTaskFuture() {
        return (ListenableFuture<SearchParameterFragment>) super.getTaskFuture();
    }
}
