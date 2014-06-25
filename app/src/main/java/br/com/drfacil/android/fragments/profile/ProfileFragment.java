package br.com.drfacil.android.fragments.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.drfacil.android.R;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.ProfessionalApi;
import br.com.drfacil.android.ext.dialog.ConfirmableDialogFragment;
import br.com.drfacil.android.ext.image.UrlImageView;
import br.com.drfacil.android.ext.instance.InstanceFactory;
import br.com.drfacil.android.ext.instance.LazyWeakFactory;
import br.com.drfacil.android.helpers.CustomViewHelper;
import br.com.drfacil.android.managers.AppStateManager;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.model.Slot;
import br.com.drfacil.android.views.ProfileScheduleItemView;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.joda.time.DateTime;
import retrofit.RetrofitError;

import java.util.List;

public class ProfileFragment extends Fragment {

    private UrlImageView vImage;
    private ListView vSchedule;
    private TextView vSpecialty;
    private TextView vEmail;
    private TextView vPhone;
    private View vProgressBar;

    private Professional mProfessional;
    private ProfileScheduleAdapter mScheduleAdapter;
    private DateTime mScheduleStartDate = DateTime.now();
    private DateTime mScheduleEndDate = DateTime.now().plusDays(5);
    private InstanceFactory<ProfileInfoSpecialtyFragment> mSpecialtyFragmentFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vImage = (UrlImageView) getView().findViewById(R.id.profile_image);
        vSchedule = (ListView) getView().findViewById(R.id.profile_schedule);
        mScheduleAdapter = new ProfileScheduleAdapter(getActivity());
        mScheduleAdapter.setOnSlotClickListener(mOnSlotClickListener);
        vSchedule.setAdapter(mScheduleAdapter);
        vProgressBar = getView().findViewById(R.id.profile_progress_spinner);
        vSpecialty = (TextView) getView().findViewById(R.id.profile_specialty);
        ((View) vSpecialty.getParent()).setOnClickListener(mOnSpecialtyInfoClick);
        vEmail = (TextView) getView().findViewById(R.id.profile_email);
        ((View) vEmail.getParent()).setOnClickListener(mOnEmailInfoClick);
        vPhone = (TextView) getView().findViewById(R.id.profile_phone);
        ((View) vPhone.getParent()).setOnClickListener(mOnPhoneInfoClick);
        if (mProfessional != null) tryUpdateView();
    }

    private final View.OnClickListener mOnSpecialtyInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProfileInfoSpecialtyFragment fragment =
                    mSpecialtyFragmentFactory.getInstance(ProfileInfoSpecialtyFragment.class);
            fragment.show(getFragmentManager(), ((Object) fragment).getClass().toString());
        }
    };

    private final View.OnClickListener mOnEmailInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, mProfessional.getEmail());
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.send_email_label)));
        }
    };

    private final View.OnClickListener mOnPhoneInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String phone = mProfessional.getPhone().replaceAll("[^\\d]", "");
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    };

    public ProfileFragment setProfessional(Professional professional) {
        mProfessional = professional;
        mSpecialtyFragmentFactory = new LazyWeakFactory.WithFixedArgumentBuilder()
                .argument(Professional.class, mProfessional)
                .build();
        return this;
    }

    public ProfileFragment setScheduleStartDate(DateTime scheduleStartDate) {
        mScheduleStartDate = scheduleStartDate;
        return this;
    }

    public ProfileFragment setScheduleEndDate(DateTime scheduleEndDate) {
        mScheduleEndDate = scheduleEndDate;
        return this;
    }

    public void tryUpdateView() {
        if (getView() == null) return;
        getActivity().setTitle(mProfessional.getName());
        vImage.setUrl(mProfessional.getImageUrl());
        vSpecialty.setText(mProfessional.getSpecialty().toString());
        vEmail.setText(mProfessional.getEmail());
        vPhone.setText(mProfessional.getPhone());
        new FetchSlotsTask().execute();
    }

    private final ProfileScheduleItemView.OnSlotClickListener mOnSlotClickListener = new ProfileScheduleItemView.OnSlotClickListener() {
        @Override
        public void onClick(Slot slot) {
            AppStateManager.LoginState loginState = AppStateManager.getInstance().getLoginState();
            if (loginState != AppStateManager.LoginState.LOGGED_IN) {
                return;
            }
            // Can't use factory here, fragment has state after being created
            CreateAppointmentFragment fragment = new CreateAppointmentFragment();
            fragment.setData(slot, mProfessional);
            fragment.show(getFragmentManager(), ((Object) fragment).getClass().toString());
            Futures.addCallback(fragment.getTaskFuture(), mOnConfirmAppointment);
        }
    };

    private FutureCallback<ConfirmableDialogFragment> mOnConfirmAppointment = new FutureCallback<ConfirmableDialogFragment>() {

        @Override
        public void onSuccess(ConfirmableDialogFragment fragment) {
            if (fragment == null) return; // Dismissed
            tryUpdateView();
            /* TODO: Extract string */
            Toast.makeText(getActivity(), "Consulta agendada", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Throwable t) {
            /* TODO: Extract string */
            Toast.makeText(getActivity(), "Erro ao agendar consulta", Toast.LENGTH_SHORT).show();
        }
    };

    private class FetchSlotsTask extends AsyncTask<Void, Void, List<Slot>> {

        @Override
        protected void onPreExecute() {
            CustomViewHelper.toggleVisibleGone(vProgressBar, true);
        }

        @Override
        protected List<Slot> doInBackground(Void... params) {
            ApiManager apiManager = ApiManager.getInstance(getActivity());
            ProfessionalApi api = apiManager.getApi(ProfessionalApi.class);
            try {
                return api.slots(mProfessional.getId(), mScheduleStartDate, mScheduleEndDate);
            } catch (RetrofitError e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Slot> slots) {
            CustomViewHelper.toggleVisibleGone(vProgressBar, false);
            if (slots != null) {
                mScheduleAdapter.update(slots);
            } else {
                Toast.makeText(getActivity(), R.string.error_unknown_network_error, Toast.LENGTH_LONG).show();
            }
        }
    }
}
