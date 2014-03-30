package br.com.drfacil.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.drfacil.android.R;

public class SearchFragment extends Fragment {

    private ListView vSearchResults;
    private ArrayAdapter<String> mResultsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vSearchResults = (ListView) getView().findViewById(R.id.search_results);
        /* TODO: Unhardcode this shit */
        mResultsAdapter = new ArrayAdapter<>(getActivity(), R.layout.search_result_item, R.id.search_result_value);
        mResultsAdapter.addAll(
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Joilson Ferreo",
                "Joseana Almeida",
                "Joao Goulard",
                "Larry Pagina",
                "Steve Trabalhos");
        vSearchResults.setAdapter(mResultsAdapter);
    }

}
