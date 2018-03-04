package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.Model.RestModel;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 1/29/2018.
 */

public class SplashActivity extends AppCompatActivity {

    public String TAG = SplashActivity.class.getName();
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private Handler handler = new Handler();
    private String accessToken = "abcd";
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private TextView check_network;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        accessToken = sharedpreferences.getString(Constants.ACCESS_TOKEN, "abcd");
        progressBar = findViewById(R.id.progressBar);
        check_network = findViewById(R.id.check_network);
        if (!isOnline()) {
            check_network.setVisibility(View.VISIBLE);
        }

        checkAccessToken();
        if (handler != null) {
            handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void checkAccessToken() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface.isAccessTokenValid(accessToken).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    handler.removeCallbacksAndMessages(null);
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && response.body().getMetaData().isSuccess()) {
                        Constants.debugLog(TAG, response.body().getMetaData().getMessage());
                        HomeActivity.start(SplashActivity.this, true);
                    } else {
                        startActivity(new Intent(SplashActivity.this, MobileLoginActivity.class));
                    }
                    SplashActivity.this.finish();
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    Constants.showDialog(SplashActivity.this, "Please try again later");
                    Constants.debugLog(TAG, t.getMessage());

                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
            Constants.showDialog(SplashActivity.this, "Please try again later");
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                Constants.debugLog(TAG, "Timeout");
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}