package br.com.drfacil.android.fragments.search.parameters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.drfacil.android.Hardcoded;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.Insurance;
import br.com.drfacil.android.model.search.Search;

public class SearchParameterInsuranceFragment extends SearchParameterFragment {

    private ListView vList;
    private ArrayAdapter<Insurance> mAdapter;

    public SearchParameterInsuranceFragment(Search search) {
        super(search);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_param_insurance, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vList = (ListView) getView().findViewById(R.id.search_parameter_insurance_list);
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.search_param_list_item, Hardcoded.INSURANCES);
        vList.setAdapter(mAdapter);
        vList.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Insurance insurance = mAdapter.getItem(position);
            getSearch().setInsurance(insurance);
            setFinished();
            dismiss();
        }
    };
}
