package mastersunny.unitedclub.Activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

public class MobileVerificationActivity extends AppCompatActivity {

    public String MobileVerificationActivity = "MobileVerificationActivity";
    private EditText one_time_password;
    private TextView phone_number, timer_text;
    private Button btn_next, btn_resend_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);

        initLayout();
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
            }

        }.start();
    }
}
