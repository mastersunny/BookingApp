package mastersunny.unitedclub.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import mastersunny.unitedclub.utils.Constants;

/**
 * Created by ASUS on 2/26/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Constants.debugLog(TAG, "" + refreshedToken);

        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);

        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra(Constants.REFRESH_TOKEN, refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        Constants.debugLog(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.prefs, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.REFRESH_TOKEN, token);
        editor.commit();
    }

}