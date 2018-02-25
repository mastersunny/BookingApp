package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by ASUS on 1/29/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private Handler handler = new Handler();
    private String accessToken = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE);
        accessToken = sharedpreferences.getString(Constants.ACCESS_TOKEN, "");

        if (handler != null) {
            handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (accessToken != null && accessToken.length() > 0) {
                startActivity(new Intent(SplashActivity.this, ClientMainActivity.class));
                SplashActivity.this.finish();
            } else {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}