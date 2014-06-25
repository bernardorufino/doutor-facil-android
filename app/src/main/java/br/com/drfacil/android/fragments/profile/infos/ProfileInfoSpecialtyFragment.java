package br.com.drfacil.android.fragments.profile.infos;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.model.Professional;

public class ProfileInfoSpecialtyFragment extends DialogFragment {

    private final Professional mProfessional;
    private TextView vContent;
    private TextView vTitle;

    public ProfileInfoSpecialtyFragment(Professional professional) {
        mProfessional = professional;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_info_specialty, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vContent = (TextView) getView().findViewById(R.id.profile_info_specialty_content);
        vTitle = (TextView) getView().findViewById(R.id.profile_info_specialty_title);
        updateView();
    }

    private void updateView() {
        vContent.setText(Html.fromHtml(mProfessional.getAboutMeText()));
        vTitle.setText(mProfessional.getSpecialty().toString());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}

