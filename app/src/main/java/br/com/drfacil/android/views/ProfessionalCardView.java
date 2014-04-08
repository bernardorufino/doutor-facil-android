package br.com.drfacil.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.image.UrlImageView;
import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Professional;

public class ProfessionalCardView extends FrameLayout {

    private Professional mProfessional;
    private TextView vName;
    private TextView vAddress;
    private UrlImageView vImage;
    private RatingView vRating;
    private TextView vSpecialty;

    public ProfessionalCardView(Context context) {
        super(context);
        init();
    }

    public ProfessionalCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfessionalCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_professional_card, this);
        vImage = (UrlImageView) findViewById(R.id.professional_card_image);
        vName = (TextView) findViewById(R.id.professional_card_name);
        vAddress = (TextView) findViewById(R.id.professional_card_address);
        vRating = (RatingView) findViewById(R.id.professional_card_rating);
        vSpecialty = (TextView) findViewById(R.id.professional_card_specialty);
    }

    public void setProfessional(Professional professional) {
        mProfessional = professional;
        updateView();
    }

    private void updateView() {
        vName.setText(mProfessional.getName());
        vAddress.setText(getAddress());
        vRating.setRating(mProfessional.getRating());
        vSpecialty.setText(mProfessional.getSpecialty());
        vImage.setUrl(mProfessional.getImageUrl());
    }

    public Professional getProfessional() {
        return mProfessional;
    }

    private String getAddress() {
        Address address = mProfessional.getAddress();
        return address.getStreet() + ", " + address.getNumber() + " - " + address.getZip();
    }
}
