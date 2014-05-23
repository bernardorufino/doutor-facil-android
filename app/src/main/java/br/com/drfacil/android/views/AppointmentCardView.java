package br.com.drfacil.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.image.UrlImageView;
import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Appointment;
import br.com.drfacil.android.model.Professional;

public class AppointmentCardView extends FrameLayout {

    private Appointment mAppointment;
    private UrlImageView vUrlImage;
    private TextView vDoctorName;
    private TextView vDateAndTime;
    private TextView vSpecialty;
    private TextView vAddress;

    public AppointmentCardView(Context context) {
        super(context);
        init();
    }

    public AppointmentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppointmentCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_appointment_card, this);
        vUrlImage = (UrlImageView) findViewById(R.id.appointment_card_doctor_image);
        vDoctorName = (TextView) findViewById(R.id.appointment_card_doctor_name);
        vDateAndTime = (TextView) findViewById(R.id.appointment_card_date);
        vAddress = (TextView) findViewById(R.id.appointment_card_location);
        vSpecialty = (TextView) findViewById(R.id.appointment_card_specialty);
    }

    public void setAppointment(Appointment appointment) {
        mAppointment = appointment;
        updateView();
    }

    private void updateView() {
        vDateAndTime.setText(mAppointment.getDate() + ", " + mAppointment.getTime()) ;
        Professional professional = mAppointment.getProfessional();
        vDoctorName.setText(professional.getName());
        vAddress.setText(getAddress(professional));
        vSpecialty.setText(professional.getSpecialty());
        vUrlImage.setUrl(professional.getImageUrl());
    }

    private String getAddress(Professional professional) {
        Address address = professional.getAddress();
        return  address.getStreet() + ", " + address.getNumber() + " - " + address.getZip();
    }

    public Appointment getAppointment() {
        return mAppointment;
    }
}
