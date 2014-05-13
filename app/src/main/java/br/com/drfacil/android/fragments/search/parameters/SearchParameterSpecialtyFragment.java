package br.com.drfacil.android.fragments.search.parameters;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.SpecialtyApi;
import br.com.drfacil.android.helpers.CustomViewHelper;
import br.com.drfacil.android.model.Specialty;
import br.com.drfacil.android.model.search.Search;

import java.util.List;

public class SearchParameterSpecialtyFragment extends SearchParameterFragment {

    private ListView vList;
    private ArrayAdapter<Specialty> mAdapter;

    public SearchParameterSpecialtyFragment(Search search) {
        super(search);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.search_param_list_item);
        mAdapter.setNotifyOnChange(true);
        new LoadSpecialtiesTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_specialty, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vList = (ListView) getView().findViewById(R.id.search_parameter_specialty_list);
        vList.setAdapter(mAdapter);
    }

    @Override
    protected void onOk() {
        List<Specialty> specialties = CustomViewHelper.getSelectedItems(vList, Specialty.class);
        getSearch().setSpecialties(specialties);
        super.onOk();
    }

    private class LoadSpecialtiesTask extends AsyncTask<Void, Void, List<Specialty>> {

        @Override
        protected List<Specialty> doInBackground(Void... params) {
            SpecialtyApi api = ApiManager.getInstance(getActivity()).getApi(SpecialtyApi.class);
            return api.all();
        }

        @Override
        protected void onPostExecute(List<Specialty> specialties) {
            mAdapter.clear();
            mAdapter.addAll(specialties);
            CustomViewHelper.selectItems(vList, getSearch().getSpecialties());
            super.onPostExecute(specialties);
        }
    }
}
