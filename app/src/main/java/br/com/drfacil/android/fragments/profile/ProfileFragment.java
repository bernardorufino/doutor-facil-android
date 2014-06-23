package br.com.drfacil.android.fragments.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.ext.image.UrlImageView;
import br.com.drfacil.android.model.Professional;

public class ProfileFragment extends Fragment {

    private UrlImageView vImage;
    private TextView vName;
    private TextView vSpecialty;
    private TextView vAddress;
    private Professional mProfessional;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vImage = (UrlImageView) getView().findViewById(R.id.profile_image);
        vName = (TextView) getView().findViewById(R.id.profile_name);
        vAddress = (TextView) getView().findViewById(R.id.profile_address);
        vSpecialty = (TextView) getView().findViewById(R.id.profile_specialty);
        if (mProfessional != null) updateView();
    }

    public void setProfessional(Professional professional) {
        mProfessional = professional;
        if (getView() != null) updateView();
    }
    
    private void updateView() {
        vImage.setUrl(mProfessional.getImageUrl());
        vName.setText(mProfessional.getName());
        vAddress.setText(mProfessional.getAddress().toString());
        vSpecialty.setText(mProfessional.getSpecialty().toString());
    }

}
