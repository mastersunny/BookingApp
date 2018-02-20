package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "MainActivity";
    private EditText phone_number;
    private Button btn_send_code;
    private ApiInterface apiInterface;
    private String phoneNumber = "";
    private ProgressBar progressBar;
    private Handler handler;
    private boolean alreadyRequest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        handler = new Handler();
        initLayout();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            alreadyRequest = false;
            progressBar.setVisibility(View.GONE);
        }
    };

    private void refreshHandler() {
        handler.postDelayed(runnable, 10000);
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
        btn_send_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                if (!alreadyRequest) {
                    Constants.debugLog(TAG, "request " + alreadyRequest);
                    alreadyRequest = true;
                    sendCode();
                }
                break;
        }
    }

    protected void sendCode() {
        phoneNumber = phone_number.getText().toString().trim();
        if (phoneNumber.length() == 0) {
            Constants.showDialog(MainActivity.this, "Please enter a valid phone number");
            return;
        } else {
            try {
                progressBar.setVisibility(View.VISIBLE);
                refreshHandler();
                apiInterface.initRegistration(phoneNumber).enqueue(new Callback<AccessModel>() {
                    @Override
                    public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                        alreadyRequest = false;
                        Constants.debugLog(TAG, response.body().toString());
                        progressBar.setVisibility(View.GONE);
                        if (response != null && response.isSuccessful() && response.body().isSuccess()) {
                            MobileVerificationActivity.start(MainActivity.this, phoneNumber);
                        } else {
                            Constants.showDialog(MainActivity.this, "Please try again");
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessModel> call, Throwable t) {
                        Constants.showDialog(MainActivity.this, "Please try again");
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "" + e.getMessage());
                progressBar.setVisibility(View.GONE);
                Constants.showDialog(MainActivity.this, "Please try again");
            }
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
