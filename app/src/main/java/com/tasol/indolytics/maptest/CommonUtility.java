package com.tasol.indolytics.maptest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by tasol on 13/10/17.
 */

public class CommonUtility {
    private static boolean isNetworkAvailable;

    public static boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
    public static void setNetworkStateAvailability(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        isNetworkAvailable = true;
                        return;
                    }
                }
            }
        }
        isNetworkAvailable = false;
    }
}
