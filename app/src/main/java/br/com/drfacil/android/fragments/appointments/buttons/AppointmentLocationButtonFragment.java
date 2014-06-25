package br.com.drfacil.android.fragments.appointments.buttons;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import br.com.drfacil.android.R;

public class AppointmentLocationButtonFragment extends AppointmentButtonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments_button_location, container, false);
    }
}
