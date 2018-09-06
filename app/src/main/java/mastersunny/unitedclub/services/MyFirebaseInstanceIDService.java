package mastersunny.unitedclub.services;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 2/26/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIDService";


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);

//        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra(Constants.FIREBASE_REFRESH_TOKEN, refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        Constants.debugLog(TAG, "sendRegistrationToServer: " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.sendRegistrationToServer(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Constants.debugLog(TAG, "firebase registration successful");
                } else {
                    Constants.debugLog(TAG, "firebase registration failed: " + response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Constants.debugLog(TAG, "firebase registration failed: " + t.getMessage());
            }
        });
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.prefs, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.FIREBASE_REFRESH_TOKEN, token);
        editor.commit();
    }

}
