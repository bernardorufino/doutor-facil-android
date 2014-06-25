package br.com.drfacil.android.views;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
    private OnAppointmentCardClickListener mOnAppointmentCardClickListener;
    private ImageView vLocationButton;
    private ImageView vEditButton;
    private ImageView vCancelButton;

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
        mOnAppointmentCardClickListener = new EmptyOnAppointmentCardClickListener();
        initView();
        initListener();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_appointment_card, this);
        vUrlImage = (UrlImageView) findViewById(R.id.appointment_card_doctor_image);
        vDoctorName = (TextView) findViewById(R.id.appointment_card_doctor_name);
        vDateAndTime = (TextView) findViewById(R.id.appointment_card_date);
        vSpecialty = (TextView) findViewById(R.id.appointment_card_specialty);
        vLocationButton = (ImageView) findViewById(R.id.appointment_card_location_button);
        vEditButton = (ImageView) findViewById(R.id.appointment_card_edit_button);
        vCancelButton = (ImageView) findViewById(R.id.appointment_card_cancel_button);
    }

    private void initListener() {
        vUrlImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAppointmentCardClickListener.onPictureClick(mAppointment.getProfessional());
            }
        });
        vLocationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAppointmentCardClickListener.onLocationButtonClick(mAppointment.getAddress());
            }
        });
        vEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAppointmentCardClickListener.onEditButtonClick();
            }
        });
        vCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnAppointmentCardClickListener.onCancelButtonClick();
            }
        });
    }

    public void setAppointment(Appointment appointment) {
        mAppointment = appointment;
        updateView();
    }

    private void updateView() {
        vDateAndTime.setText(mAppointment.getDate() + ", " + mAppointment.getTime()) ;
        Professional professional = mAppointment.getProfessional();
        vDoctorName.setText(professional.getName());
        vSpecialty.setText(professional.getSpecialty().toString());
        vUrlImage.setUrl(professional.getImageUrl());
    }

    public Appointment getAppointment() {
        return mAppointment;
    }

    public void setOnAppointmentCardClickListener(OnAppointmentCardClickListener onAppointmentCardClickListener) {
        mOnAppointmentCardClickListener = onAppointmentCardClickListener;
    }

    public interface OnAppointmentCardClickListener {
        public void onPictureClick(Professional professional);
        public void onLocationButtonClick(Address address);
        public void onEditButtonClick();
        public void onCancelButtonClick();
    }

    public class EmptyOnAppointmentCardClickListener implements OnAppointmentCardClickListener {

        @Override
        public void onPictureClick(Professional professional) {
            /* Do nothing */
        }

        @Override
        public void onLocationButtonClick(Address address) {
            /* Do nothing */
        }

        @Override
        public void onEditButtonClick() {
            /* Do nothing */
        }

        @Override
        public void onCancelButtonClick() {
            /* Do nothing */
        }
    }
}
