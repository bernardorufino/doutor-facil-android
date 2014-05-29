package br.com.drfacil.android.fragments.search.parameters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.search.Search;

public class SearchParameterLocationFragment extends SearchParameterFragment {

    public SearchParameterLocationFragment(Search search) {
        super(search);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_location, container, false);
    }
}
