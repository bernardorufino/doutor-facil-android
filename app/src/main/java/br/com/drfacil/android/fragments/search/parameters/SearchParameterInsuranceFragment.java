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
import br.com.drfacil.android.endpoints.InsuranceApi;
import br.com.drfacil.android.helpers.CustomViewHelper;
import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.search.Search;

import java.util.List;

public class SearchParameterInsuranceFragment extends SearchParameterFragment {

    private ListView vList;
    private ArrayAdapter<Insurance> mAdapter;

    public SearchParameterInsuranceFragment(Search search) {
        super(search);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.search_param_list_item);
        mAdapter.setNotifyOnChange(true); // Just to be explicit
        new LoadInsurancesTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_insurance, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vList = (ListView) getView().findViewById(R.id.search_parameter_insurance_list);
        vList.setAdapter(mAdapter);
    }

    @Override
    protected void onOk() {
        List<Insurance> insurances = CustomViewHelper.getSelectedItems(vList, Insurance.class);
        getSearch().setInsurances(insurances);
        super.onOk();
    }

    private class LoadInsurancesTask extends AsyncTask<Void, Void, List<Insurance>> {

        @Override
        protected List<Insurance> doInBackground(Void... params) {
            InsuranceApi api = ApiManager.getInstance(getActivity()).getApi(InsuranceApi.class);
            return api.all();
        }

        @Override
        protected void onPostExecute(List<Insurance> insurances) {
            mAdapter.clear();
            mAdapter.addAll(insurances);
            CustomViewHelper.selectItems(vList, getSearch().getInsurances());
            super.onPostExecute(insurances);
        }
    }
}
