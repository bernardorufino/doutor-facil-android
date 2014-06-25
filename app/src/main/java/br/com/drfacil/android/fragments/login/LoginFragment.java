package br.com.drfacil.android.fragments.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import br.com.drfacil.android.R;
import br.com.drfacil.android.activities.MainActivity;
import br.com.drfacil.android.endpoints.ApiManager;
import br.com.drfacil.android.endpoints.LoginApi;
import br.com.drfacil.android.helpers.AsyncHelper;
import br.com.drfacil.android.managers.AppStateManager;
import br.com.drfacil.android.model.Patient;

public class LoginFragment extends Fragment {

    public static final MainActivity.HostInfo HOST_INFO = new MainActivity.HostInfo(
            R.string.login_label,
            LoginFragment.class);

    private LoginButton mLoginWithFacebookButton;
    private Session.StatusCallback mCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            onSessionStateChange(session, sessionState, e);
        }
    };
    private UiLifecycleHelper mUiLifecycleHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUiLifecycleHelper = new UiLifecycleHelper(getActivity(), mCallback);
        mUiLifecycleHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.login_with_facebook_button);
        authButton.setReadPermissions("email");

        authButton.setFragment(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUiLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        mUiLifecycleHelper.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }

        mUiLifecycleHelper.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiLifecycleHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUiLifecycleHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

        /*TODO: refactor the cast to MainActivity*/

        if (state.isOpened()) {
            Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(final GraphUser graphUser, Response response) {
                    if (graphUser == null || response.getError() != null) return;
                    AsyncHelper.executeTask(new Runnable() {
                        @Override
                        public void run() {
                            requestLogInToServer(graphUser);
                        }
                    });
                }
            });
            request.executeAsync();

            // NPE check added because getActivity() was been called on 2nd logout action from a
            // null fragment
        } else if (state.isClosed() && getActivity() != null) {
            ((MainActivity) getActivity()).switchFromAppointmentsToLoginTab();
        }
    }

    private void requestLogInToServer(GraphUser graphUser) {
        LoginApi api = ApiManager.getInstance(getActivity()).getApi(LoginApi.class);

        Patient patient = new Patient(graphUser.getUsername(),
                (String) graphUser.getProperty("email"),
                graphUser.getFirstName(),
                graphUser.getLastName(),
                graphUser.getBirthday(),
                (String) graphUser.getProperty("gender"));

        patient = api.create(patient);

        AppStateManager.getInstance().logIn(patient);
        if (getActivity() != null) {
            ((MainActivity) getActivity()).switchFromLoginToAppointmentsTab();
        }
    }
}
