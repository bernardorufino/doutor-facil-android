package br.com.drfacil.android.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Professional;

public class ProfessionalCardView extends FrameLayout {

    private Professional mProfessional;
    private TextView vName;
    private TextView vAddress;
    private TextView vRating;

    public ProfessionalCardView(Context context) {
        super(context);
        init(null);
    }

    public ProfessionalCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProfessionalCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initView();
        if (attrs != null) {
            initAttributes(attrs);
        }
    }

    private void initAttributes(AttributeSet attrs) {
        /* TODO: Implement */
    }

    private void initView() {
        inflate(getContext(), R.layout.view_professional_card, this);
        vName = (TextView) findViewById(R.id.professional_card_name);
        vAddress = (TextView) findViewById(R.id.professional_card_address);
        vRating = (TextView) findViewById(R.id.professional_card_rating);
    }

    public void setProfessional(Professional professional) {
        mProfessional = professional;
        updateView();
    }

    private void updateView() {
        vName.setText(mProfessional.getName());
        vAddress.setText(getAddress());
        vRating.setText(getRating());
    }

    public Professional getProfessional() {
        return mProfessional;
    }

    private String getAddress() {
        Address address = mProfessional.getAddress();
        return address.getStreet() + ", " + address.getNumber() + " - " + address.getZip();
    }

    private String getRating() {
        int rating = mProfessional.getRating();
        return rating + " / " + Professional.MAX_RATING;
    }
}