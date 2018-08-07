package mastersunny.unitedclub.activities;

import android.app.ProgressDialog;
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

import mastersunny.unitedclub.models.RestModel;
import mastersunny.unitedclub.models.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = MobileVerificationActivity.class.getSimpleName();
    private EditText one_time_password;
    private TextView phone_number, timer_text;
    private Button btn_next, btn_resend_code;
    private SharedPreferences.Editor editor;
    private String phoneNumber = "";
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private CountDownTimer timer;
    private Handler handler;
    private int time = 60;
    private ProgressDialog pdLoading;
    private boolean alreadyRequest = false;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, MobileVerificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile_verification);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        editor = getSharedPreferences(Constants.prefs, MODE_PRIVATE).edit();
        phoneNumber = getIntent().getStringExtra(Constants.PHONE_NUMBER);

        handler = new Handler();
        initLayout();
        updateUI();
    }

    private void updateUI() {
        phone_number.setText(phoneNumber);
    }


    private void initLayout() {
        one_time_password = findViewById(R.id.one_time_password);
        phone_number = findViewById(R.id.phone_number);
        timer_text = findViewById(R.id.timer_text);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setAlpha(0.4f);
        btn_next.setOnClickListener(this);
        btn_resend_code = findViewById(R.id.btn_resend_code);
        btn_resend_code.setOnClickListener(this);
        btn_resend_code.setAlpha(0.4f);
        btn_resend_code.setClickable(false);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.back_button).setOnClickListener(this);


        pdLoading = new ProgressDialog(this);

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
                    btn_next.setAlpha(0.4f);
                    btn_next.setClickable(false);
                }
            }
        });

        timer = new CountDownTimer(62000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_text.setText("" + time);
                time--;
            }

            public void onFinish() {
                btn_resend_code.setClickable(true);
                btn_resend_code.setAlpha(1f);
                progressBar.setVisibility(View.GONE);
            }

        };
        timer.start();
        Constants.showDialog(this, "Verification code will be sent to your phone number.");
    }

    @Override
    public void onBackPressed() {
        if (!alreadyRequest && !pdLoading.isShowing()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_code:
                if (!alreadyRequest) {
                    sendCode();
                }
                break;
            case R.id.btn_next:
                verifyCode();
                break;
            case R.id.back_button:
                if (!alreadyRequest) {
                    finish();
                }
        }
    }

    private Runnable sendCodeRunnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
            alreadyRequest = false;
            if (timer != null) {
                timer.cancel();
            }
        }
    };

    private Runnable verifyCodeRunnable = new Runnable() {
        @Override
        public void run() {
            if (pdLoading != null) {
                pdLoading.dismiss();
            }
        }
    };

    protected void sendCode() {
        try {
            Constants.debugLog(TAG, "btn_resend_code " + alreadyRequest);
            alreadyRequest = true;
            progressBar.setVisibility(View.VISIBLE);
            handler.postDelayed(sendCodeRunnable, Constants.REQUEST_TIMEOUT);
            apiInterface.getCode(phoneNumber).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    alreadyRequest = false;
                    progressBar.setVisibility(View.GONE);
                    if (response != null && response.isSuccessful() && response.body().getMetaData().isSuccess()) {
                        Constants.showDialog(MobileVerificationActivity.this, "Verification code will be sent to your phone number.");
                    } else {
                        Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    alreadyRequest = false;
                    progressBar.setVisibility(View.GONE);
                    Constants.showDialog(MobileVerificationActivity.this, "Please try again");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "" + e.getMessage());
            alreadyRequest = false;
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
            showProgressDialog();
            handler.postDelayed(verifyCodeRunnable, Constants.REQUEST_TIMEOUT);
            apiInterface.verifyCode(phoneNumber, one_time_password.getText().toString()).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    pdLoading.dismiss();
                    handler.removeCallbacksAndMessages(verifyCodeRunnable);
                    if (response != null && response.isSuccessful() && response.body().getMetaData().isSuccess()) {
                        Constants.debugLog(TAG, response.body().getMetaData() + " re " + response.body().getUserDTO());
                        if (response.body().getMetaData().isData()) {
                            UserDTO userDTO = response.body().getUserDTO();
                            Constants.debugLog(TAG, userDTO.toString());
                            editor.putString(Constants.ACCESS_TOKEN, userDTO.getEmail());
                            editor.apply();
                            startActivity(new Intent(MobileVerificationActivity.this, HomeActivity.class));
                        } else {
                            editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                            editor.apply();
                            startActivity(new Intent(MobileVerificationActivity.this, RegistrationActivity.class));
                        }
                        MobileVerificationActivity.this.finish();
                    } else {
                        Constants.showDialog(MobileVerificationActivity.this, "" + response.body().getMetaData().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    pdLoading.dismiss();
                    handler.removeCallbacksAndMessages(verifyCodeRunnable);
                    Constants.debugLog(TAG, "" + t.getMessage());
                    Constants.showDialog(MobileVerificationActivity.this, "Cannot verify at this moment");
                }
            });

        } catch (Exception e) {
            pdLoading.dismiss();
            handler.removeCallbacksAndMessages(verifyCodeRunnable);
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    public void showProgressDialog() {
        pdLoading = new ProgressDialog(this);
        pdLoading.setMessage("Authorizing");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }
}
