package br.com.drfacil.android.fragments.profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.AppointmentApi;
import br.com.drfacil.android.ext.dialog.ConfirmableDialogFragment;
import br.com.drfacil.android.managers.AppStateManager;
import br.com.drfacil.android.model.Patient;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Slot;

import static com.google.common.base.Preconditions.checkNotNull;

public class CreateAppointmentFragment extends ConfirmableDialogFragment {

    private TextView vMessage;

    private Professional mProfessional;
    private Slot mSlot;

    public void setData(Slot slot, Professional professional) {
        mSlot = slot;
        mProfessional = professional;
        if (getView() != null) updateView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vMessage = (TextView) getView().findViewById(R.id.confirm_appointment_message);
        if (mProfessional != null && mSlot != null) updateView();
    }

    private void updateView() {
        String message = getResources().getString(R.string.confirm_appointment_message);
        message = String.format(message, mProfessional.getName(), mSlot.getStartDate().toString("MMM dd HH:mm"));
        vMessage.setText(message);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm_appointment, container);
    }

    @Override
    protected void onOk() {
        new CreateAppointmentTask().execute();
    }

    private class CreateAppointmentTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            /* TODO: Spinner */
            getOkButton().setEnabled(false);
            getDismissButton().setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ApiManager apiManager = ApiManager.getInstance(getActivity());
            AppointmentApi api = apiManager.getApi(AppointmentApi.class);
            Patient patient = AppStateManager.getInstance().getLoggedInPatient();
            checkNotNull(patient, "There is no patient logged in");
            api.create(
                    "" + patient.getId(),
                    "" + mProfessional.getId(),
                    mSlot.getStartDate(),
                    mSlot.getStartDate().plusMinutes(30));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getOkButton().setEnabled(true);
            getDismissButton().setEnabled(true);
            setFinished();
            dismiss();
        }
    }
}
