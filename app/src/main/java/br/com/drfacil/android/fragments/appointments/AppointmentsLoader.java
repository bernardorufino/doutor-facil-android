package br.com.drfacil.android.fragments.appointments;

import android.content.Context;
import br.com.drfacil.android.Hardcoded;
import br.com.drfacil.android.ext.loader.AsyncDataLoader;
import br.com.drfacil.android.model.Appointment;

import java.util.List;

public class AppointmentsLoader extends AsyncDataLoader<List<Appointment>> {

    public AppointmentsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Appointment> loadInBackground() {
        return Hardcoded.APPOINTMENTS;
    }

    @Override
    protected void releaseResources(List<Appointment> data) {
        /* No-op */
    }

    @Override
    protected void startObserving() {
        /* No-op */
    }

    @Override
    protected void stopObserving() {
        /* No-op */
    }
}
