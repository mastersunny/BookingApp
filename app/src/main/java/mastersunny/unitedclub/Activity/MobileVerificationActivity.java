package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity extends AppCompatActivity {

    public String MobileVerificationActivity = "MobileVerificationActivity";
    private EditText one_time_password;
    private TextView phone_number, timer_text;
    private Button btn_next, btn_resend_code;
    SharedPreferences preferences;
    private boolean isResend = false;
    private String phoneNumber = "";
    private ApiInterface apiInterface;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, MobileVerificationActivity.class);
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);

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
        btn_resend_code = findViewById(R.id.btn_resend_code);
        btn_resend_code.setClickable(false);
        btn_resend_code.setAlpha(0.5f);

        Constants.showDialog(this, "Verification code will be sent to your phone number.");

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_text.setText("" + time);
                time--;
            }

            public void onFinish() {
                btn_resend_code.setClickable(true);
                btn_resend_code.setAlpha(1f);
                isResend = true;
            }

        }.start();

        btn_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResend) {
                    apiInterface.initRegistration(phoneNumber).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isResend) {
            return;
        }
        super.onBackPressed();
    }
}
