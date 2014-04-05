package br.com.drfacil.android.helpers;


import android.util.Log;

public class CustomHelper {

    private static final String LOG_TAG = "DRFACIL";

//    public static boolean hasPlayServices(Context context) {
//        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
//    }

    public static void log(String message) {
        Log.d(LOG_TAG, message);
    }

    public static void logException(Throwable e) {
        if (e != null) Log.e(LOG_TAG, e.toString(), e);
        else Log.d(LOG_TAG, "Logging null exception");
    }

    // Prevents instantiation
    private CustomHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
