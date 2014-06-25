package br.com.drfacil.android.managers;

import com.facebook.Session;

import br.com.drfacil.android.model.Patient;

public class AppStateManager {

    private Patient loggedInPatient;

    private static class AppStateManagerContainer {
        public static AppStateManager INSTANCE = new AppStateManager();
    }

    private static final AppStateManagerContainer CONTAINER = new AppStateManagerContainer();

    public static AppStateManager getInstance() {
        return CONTAINER.INSTANCE;
    }

    private AppStateManager() {
        /* Prevents outside instantiation */
    }

    private LoginState loginState = LoginState.LOGGED_OUT;

    public LoginState getLoginState() {
        return loginState;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public void logIn(Patient patient) {
        loginState = LoginState.LOGGED_IN;
        loggedInPatient = patient;
    }

    public void logOut() {
        loginState = LoginState.LOGGED_OUT;
        loggedInPatient = null;
        Session.getActiveSession().closeAndClearTokenInformation();
    }

    public static enum LoginState { LOGGED_IN, LOGGED_OUT }
}
