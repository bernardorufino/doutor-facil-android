package br.com.drfacil.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;

public class AppointmentsFragment extends Fragment {

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.my_appointments_label,
            AppointmentsFragment.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }
}
