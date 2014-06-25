package br.com.drfacil.android.fragments.appointments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;
import br.com.drfacil.android.ext.instance.InstanceFactory;
import br.com.drfacil.android.ext.instance.LazyWeakFactory;
import br.com.drfacil.android.fragments.appointments.buttons.AppointmentButtonFragment;
import br.com.drfacil.android.fragments.appointments.buttons.AppointmentCancelButtonFragment;
import br.com.drfacil.android.fragments.appointments.buttons.AppointmentEditButtonFragment;
import br.com.drfacil.android.fragments.appointments.buttons.AppointmentLocationButtonFragment;
import br.com.drfacil.android.fragments.profile.ProfileFragment;
import br.com.drfacil.android.model.Address;
import br.com.drfacil.android.model.Appointment;
import br.com.drfacil.android.model.Professional;
import br.com.drfacil.android.views.AppointmentCardView;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.List;

public class AppointmentsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Appointment>>{

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.my_appointments_label,
            AppointmentsFragment.class);

    private static final int APPOINTMENTS_LOADER = 0;

    private ListView mAppointmentsList;
    private AppointmentsAdapter mAppointmentsAdapter;
    private InstanceFactory<AppointmentButtonFragment> mButtonFragmentFactory;
    private InstanceFactory<ProfileFragment> mProfileFragmentFactory;

    public AppointmentsFragment() {
        System.out.println("foo");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mButtonFragmentFactory = new LazyWeakFactory.WithFixedArgumentBuilder().build();
        mProfileFragmentFactory = new LazyWeakFactory.WithFixedArgumentBuilder().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAppointmentsList = (ListView) getView().findViewById(R.id.appointments_list);
        mAppointmentsAdapter = new AppointmentsAdapter(getActivity(), mOnAppointmentCardClickListener);
        mAppointmentsList.setAdapter(mAppointmentsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(APPOINTMENTS_LOADER, null, this);
    }

    // TODO: Add tags
    private final AppointmentCardView.OnAppointmentCardClickListener mOnAppointmentCardClickListener = new AppointmentCardView.OnAppointmentCardClickListener() {
        @Override
        public void onPictureClick(Professional professional) {
            ProfileFragment profileFragment = mProfileFragmentFactory.getInstance(ProfileFragment.class);
            profileFragment
                    .setProfessional(professional)
                    .setScheduleStartDate(DateTime.now())
                    .setScheduleEndDate(DateTime.now().plusDays(7))
                    .tryUpdateView();
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.appointments_container, profileFragment)
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        public void onLocationButtonClick(Address address) {
            AppointmentLocationButtonFragment locationButtonFragment = mButtonFragmentFactory.getInstance(AppointmentLocationButtonFragment.class);
            locationButtonFragment.show(getFragmentManager(), "");
        }

        @Override
        public void onEditButtonClick() {
            AppointmentEditButtonFragment editButtonFragment = mButtonFragmentFactory.getInstance(AppointmentEditButtonFragment.class);
            editButtonFragment.show(getFragmentManager(), "");
        }

        @Override
        public void onCancelButtonClick() {
            AppointmentCancelButtonFragment cancelButtonFragment = mButtonFragmentFactory.getInstance(AppointmentCancelButtonFragment.class);
            cancelButtonFragment.show(getFragmentManager(), "");
        }
    };

    @Override
    public Loader<List<Appointment>> onCreateLoader(int loader, Bundle args) {
        switch (loader) {
            case APPOINTMENTS_LOADER:
                return new AppointmentsLoader(getActivity());
        }
        throw new AssertionError("Unknown loader code " + loader);
    }

    @Override
    public void onLoadFinished(Loader<List<Appointment>> loader, List<Appointment> data) {
        mAppointmentsAdapter.update(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Appointment>> loader) {
        mAppointmentsAdapter.update(Collections.<Appointment>emptyList());
    }
}
