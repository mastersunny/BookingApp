package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import mastersunny.unitedclub.Model.AccessModel;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        accessToken = sharedpreferences.getString(Constants.ACCESS_TOKEN, "abcd");
        progressBar = findViewById(R.id.progressBar);

        checkAccessToken();
        if (handler != null) {
            handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
        }
    }

    private void checkAccessToken() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface.isAccessTokenValid(accessToken).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    Constants.debugLog(TAG, response.body() + "");
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        startActivity(new Intent(SplashActivity.this, ClientMainActivity.class));
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    SplashActivity.this.finish();
                }

                @Override
                public void onFailure(Call<AccessModel> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());

                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
            Constants.showDialog(SplashActivity.this, "Please try again later");
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}