package br.com.drfacil.android.fragments.appointments.buttons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.drfacil.android.R;

public class AppointmentEditButtonFragment extends AppointmentButtonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments_button_edit, container, false);
    }
}
