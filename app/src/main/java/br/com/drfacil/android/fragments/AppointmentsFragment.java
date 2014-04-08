package br.com.drfacil.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.drfacil.android.R;

public class AppointmentsFragment extends Fragment {

    private ListView mAppointmentsList;
    private ArrayAdapter<String> mAppointmentsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppointmentsList = (ListView) getView().findViewById(R.id.appointments_list);

        /*TODO: Unhardcode this */
        mAppointmentsAdapter = new ArrayAdapter<>(getActivity(), R.layout.appointments_item, R.id.appointment_doctor_name);
        mAppointmentsAdapter.addAll("Dr. Pedro Albuquerque", "Dr. Rudolph Green");

        mAppointmentsList.setAdapter(mAppointmentsAdapter);
    }
}
