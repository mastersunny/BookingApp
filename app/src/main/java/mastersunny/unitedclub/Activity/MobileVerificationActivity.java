package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    private String phoneNumber = "";
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private CountDownTimer timer;
    private Handler handler;
    private boolean alreadyRequest = false;

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
        handler = new Handler();
        initLayout();
        updateUI();
    }

    private void updateUI() {
        phone_number.setText(phoneNumber);
    }

    int time = 60;

    private void initLayout() {
        one_time_password = findViewById(R.id.one_time_password);
        phone_number = findViewById(R.id.phone_number);
        timer_text = findViewById(R.id.timer_text);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setAlpha(0.5f);
        btn_next.setClickable(false);
        btn_next.setOnClickListener(this);
        btn_resend_code = findViewById(R.id.btn_resend_code);
        btn_resend_code.setOnClickListener(this);
        btn_resend_code.setClickable(false);
        btn_resend_code.setAlpha(0.5f);
        progressBar = findViewById(R.id.progressBar);

        one_time_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btn_next.setAlpha(1);
                    btn_next.setClickable(true);
                } else {
                    btn_next.setAlpha(0.5f);
                    btn_next.setClickable(false);
                }
            }
        });

        findViewById(R.id.back_button).setOnClickListener(this);

        Constants.showDialog(this, "Verification code will be sent to your phone number.");

        timer = new CountDownTimer(62000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_text.setText("" + time);
                time--;
            }

            public void onFinish() {
                alreadyRequest = false;
                btn_resend_code.setClickable(true);
                btn_resend_code.setAlpha(1f);
                progressBar.setVisibility(View.GONE);
            }

        };
        timer.start();

        btn_resend_code.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (!alreadyRequest) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_code:
                if (!alreadyRequest) {
                    alreadyRequest = true;
                    sendCode();
                }
                break;
            case R.id.btn_next:
                if (!alreadyRequest) {
                    alreadyRequest = true;
                    verifyCode();
                }
                break;
            case R.id.back_button:
                if (!alreadyRequest) {
                    finish();
                }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            alreadyRequest = false;
            if (timer != null) {
                timer.cancel();
            }
            progressBar.setVisibility(View.GONE);
        }
    };

    private void refreshHandler() {
        handler.postDelayed(runnable, 10000);
    }

    protected void sendCode() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            refreshHandler();
            apiInterface.initRegistration(phoneNumber).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    alreadyRequest = false;
                    progressBar.setVisibility(View.GONE);
                    if (response != null && response.isSuccessful() && response.body().isSuccess()) {
                        Constants.showDialog(MobileVerificationActivity.this, "Verification code will be sent to your phone number.");
                    } else {
                        Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                    }
                }

                @Override
                public void onFailure(Call<AccessModel> call, Throwable t) {
                    Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
            progressBar.setVisibility(View.GONE);
            Constants.showDialog(MobileVerificationActivity.this, "Please try again");
        }
    }

    private void verifyCode() {
        String otp = one_time_password.getText().toString().trim();
        if (otp.length() == 0) {
            return;
        }
        try {
            progressBar.setVisibility(View.VISIBLE);
            refreshHandler();
            apiInterface.verifyCode(phoneNumber, one_time_password.getText().toString()).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    alreadyRequest = false;
                    progressBar.setVisibility(View.GONE);
                    if (response != null && response.isSuccessful() && response.body().isSuccess()) {
                        AccessModel accessModel = response.body();
                        if (accessModel.getAccessToken().length() == 0) {
                            editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                            editor.apply();
                            startActivity(new Intent(MobileVerificationActivity.this, RegistrationActivity.class));
                            finish();
                        } else {
                            editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                            editor.putString(Constants.ACCESS_TOKEN, accessModel.getAccessToken());
                            editor.apply();
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
