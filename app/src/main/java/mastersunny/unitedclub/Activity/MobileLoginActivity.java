package mastersunny.unitedclub.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

import mastersunny.unitedclub.Model.RestModel;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileLoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<RestModel> {

    public String TAG = MobileLoginActivity.class.getSimpleName();
    private EditText phone_number;
    private Button btn_send_code;
    private ApiInterface apiInterface;
    private String phoneNumber = "";
    private ProgressBar progressBar;
    private CountryCodePicker countryCodePicker;
    private String countryCode = "";
    private Handler handler;
    private boolean alreadyRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile_login);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        handler = new Handler();
        initLayout();
    }

    @Override
    protected void onResume() {
        phone_number.setText(phoneNumber);
        super.onResume();
    }

    private void initLayout() {
        progressBar = findViewById(R.id.progressBar);
        phone_number = findViewById(R.id.phone_number);
        btn_send_code = findViewById(R.id.btn_send_code);
        btn_send_code.setAlpha(0.4f);
        btn_send_code.setOnClickListener(this);
        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btn_send_code.setAlpha(1);
                    btn_send_code.setClickable(true);
                } else {
                    btn_send_code.setAlpha(0.5f);
                    btn_send_code.setClickable(false);
                }
            }
        });

        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCode();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                if (!alreadyRequest) {
                    alreadyRequest = true;
                    sendCode();
                }
                break;
        }
    }

    protected void sendCode() {
        try {
            refreshHandler();
            progressBar.setVisibility(View.VISIBLE);
            countryCode = countryCodePicker.getSelectedCountryCode();
            phoneNumber = phone_number.getText().toString().trim();
            apiInterface.getCode(countryCode + phoneNumber).enqueue(this);
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
            Constants.showDialog(MobileLoginActivity.this, getResources().getString(R.string.error_login));
        }
    }

    @Override
    public void onResponse(Call<RestModel> call, Response<RestModel> response) {
        Constants.debugLog(TAG, response + "");
        if (response != null && response.isSuccessful() && response.body().getMetaData().isSuccess()) {
            if (response != null && response.isSuccessful() && response.body().getMetaData().isSuccess()) {
                MobileVerificationActivity.start(MobileLoginActivity.this, (countryCode + phoneNumber));
            } else {
                Constants.showDialog(MobileLoginActivity.this, getResources().getString(R.string.error_login));
            }
        }

        alreadyRequest = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<RestModel> call, Throwable t) {
        Constants.debugLog(TAG, t.getMessage());
        Constants.showDialog(MobileLoginActivity.this, getResources().getString(R.string.error_login));
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            alreadyRequest = false;
            progressBar.setVisibility(View.GONE);
        }
    };

    private void refreshHandler() {
        if (handler != null) {
            handler.postDelayed(runnable, Constants.REQUEST_TIMEOUT);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
