package br.com.drfacil.android.fragments.appointments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.drfacil.android.Hardcoded;
import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;
import br.com.drfacil.android.model.Appointment;

import java.util.Collections;
import java.util.List;

public class AppointmentsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Appointment>>{

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.my_appointments_label,
            AppointmentsFragment.class);

    private static final int APPOINTMENTS_LOADER = 0;

    private ListView mAppointmentsList;
    private AppointmentsAdapter mAppointmentsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppointmentsList = (ListView) getView().findViewById(R.id.appointments_list);
        mAppointmentsAdapter = new AppointmentsAdapter(getActivity());
        mAppointmentsList.setAdapter(mAppointmentsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(APPOINTMENTS_LOADER, null, this);
    }

    @Override
    public Loader<List<Appointment>> onCreateLoader(int loader, Bundle args) {
        switch (loader) {
            case APPOINTMENTS_LOADER:
                return new AppointmentsLoader(getActivity());
        }
        throw new AssertionError("Unknown loader code " + loader);
    }

    @Override
    public void onLoadFinished(Loader<List<Appointment>> loader, List<Appointment> data) {
        mAppointmentsAdapter.update(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Appointment>> loader) {
        mAppointmentsAdapter.update(Collections.<Appointment>emptyList());
    }
}
