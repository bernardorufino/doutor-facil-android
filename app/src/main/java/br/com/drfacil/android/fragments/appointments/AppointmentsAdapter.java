package br.com.drfacil.android.fragments.appointments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import br.com.drfacil.android.model.Appointment;
import br.com.drfacil.android.views.AppointmentCardView;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Appointment> mAppointments = new ArrayList<>();

    public AppointmentsAdapter(Context context) {
        mContext = context;
    }

    public void update(List<Appointment> appointments) {
        mAppointments = ImmutableList.copyOf(appointments);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mAppointments.size();
    }

    @Override
    public Appointment getItem(int position) {
        return mAppointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppointmentCardView card = (convertView instanceof AppointmentCardView)
                ? (AppointmentCardView) convertView
                : new AppointmentCardView(mContext);
        Appointment appointment = getItem(position);
        if (card.getAppointment() != appointment) {
            card.setAppointment(appointment);
        }
        return card;
    }
}
