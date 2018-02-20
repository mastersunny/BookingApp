package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "MobileVerificationActivity";
    private EditText one_time_password;
    private TextView phone_number, timer_text;
    private Button btn_next, btn_resend_code;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean isResend = false;
    private String phoneNumber = "";
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private CountDownTimer timer;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, MobileVerificationActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile_verification);

        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);
        editor = preferences.edit();

        phoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initLayout();
        updateUI();
    }

    private void updateUI() {
        isResend = false;
        phone_number.setText(phoneNumber);
    }

    int time = 60;

    private void initLayout() {
        one_time_password = findViewById(R.id.one_time_password);
        phone_number = findViewById(R.id.phone_number);
        timer_text = findViewById(R.id.timer_text);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_resend_code = findViewById(R.id.btn_resend_code);
        btn_resend_code.setOnClickListener(this);
        btn_resend_code.setClickable(false);
        btn_resend_code.setAlpha(0.5f);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.back_button).setOnClickListener(this);

        Constants.showDialog(this, "Verification code will be sent to your phone number.");

        timer = new CountDownTimer(62000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_text.setText("" + time);
                time--;
            }

            public void onFinish() {
                btn_resend_code.setClickable(true);
                btn_resend_code.setAlpha(1f);
                isResend = true;
                progressBar.setVisibility(View.GONE);
            }

        };
        timer.start();

        btn_resend_code.setOnClickListener(this);
    }

    private void sendCode() {
        isResend = false;
        try {
            timer.start();
            apiInterface.initRegistration(phoneNumber).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    Constants.debugLog(TAG, response.body().toString());
                    progressBar.setVisibility(View.GONE);
                    if (response.body().isSuccess()) {
                        Constants.showDialog(MobileVerificationActivity.this,
                                "Verification code will be sent to your phone number.");
                    } else {
                        Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                    }
                    isResend = true;
                }

                @Override
                public void onFailure(Call<AccessModel> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                    Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
            progressBar.setVisibility(View.GONE);
            Constants.showDialog(MobileVerificationActivity.this, "Please try again");
        }
    }

    @Override
    public void onBackPressed() {
        if (isResend) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_code:
                if (isResend) {
                    sendCode();
                }
                break;
            case R.id.btn_next:
                verifyCode();
                break;
            case R.id.back_button:
                if (isResend) {
                    finish();
                }
        }
    }

    private void verifyCode() {
        progressBar.setVisibility(View.VISIBLE);
        Constants.debugLog(TAG, "" + phoneNumber + " " + one_time_password.getText().toString());
        try {
            apiInterface.verifyCode(phoneNumber, one_time_password.getText().toString()).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    Constants.debugLog(TAG, response.body().toString());
                    progressBar.setVisibility(View.GONE);
                    AccessModel accessModel = response.body();
                    if (accessModel.isSuccess()) {
                        if (accessModel.getAccessToken().length() == 0) {
                            startActivity(new Intent(MobileVerificationActivity.this, RegistrationActivity.class));
                            finish();
                        } else {
                            editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                            editor.putString(Constants.ACCESS_TOKEN, accessModel.getAccessToken());
                            startActivity(new Intent(MobileVerificationActivity.this, ClientMainActivity.class));
                            finish();
                        }
                    } else {
                        Constants.showDialog(MobileVerificationActivity.this, "Cannot verify at this moment");
                    }

                }

                @Override
                public void onFailure(Call<AccessModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Constants.debugLog(TAG, "" + t.getMessage());
                    Constants.showDialog(MobileVerificationActivity.this, "Cannot verify at this moment");
                }
            });

        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }


}
