package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by ASUS on 1/29/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);
        SharedPreferences sharedpreferences = getSharedPreferences(Constants.prefs, Context.MODE_PRIVATE);
        final String phoneNumber = sharedpreferences.getString(Constants.PHONE_NUMBER, "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (phoneNumber != null && phoneNumber.length() == 11) {
                    Intent mainIntent = new Intent(SplashActivity.this, ClientMainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}