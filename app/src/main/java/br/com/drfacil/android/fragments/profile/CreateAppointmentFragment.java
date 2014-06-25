package br.com.drfacil.android.fragments.profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.AppointmentApi;
import br.com.drfacil.android.ext.dialog.ConfirmableDialogFragment;
import br.com.drfacil.android.managers.AppStateManager;
import br.com.drfacil.android.model.Patient;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Slot;
import retrofit.RetrofitError;

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
        getDismissButton().setEnabled(true);
        getOkButton().setEnabled(true);
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

    private class CreateAppointmentTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            /* TODO: Spinner */
            getOkButton().setEnabled(false);
            getDismissButton().setEnabled(false);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ApiManager apiManager = ApiManager.getInstance(getActivity());
            AppointmentApi api = apiManager.getApi(AppointmentApi.class);
            Patient patient = AppStateManager.getInstance().getLoggedInPatient();
            checkNotNull(patient, "There is no patient logged in");
            try {
                api.create(
                        "" + patient.getId(),
                        "" + mProfessional.getId(),
                        mSlot.getStartDate(),
                        mSlot.getStartDate().plusMinutes(30));
                return true;
            } catch (RetrofitError e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            getDismissButton().setEnabled(true);
            if (!success) {
                Toast.makeText(getActivity(), R.string.error_unknown_network_error, Toast.LENGTH_LONG).show();
            } else {
                getOkButton().setEnabled(true);
                setFinished();
                dismiss();
            }
        }
    }
}
