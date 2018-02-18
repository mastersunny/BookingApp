package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "MainActivity";
    private EditText phone_number;
    private Button btn_send_code;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initLayout();
    }

    private void initLayout() {
        phone_number = findViewById(R.id.phone_number);
        btn_send_code = findViewById(R.id.btn_send_code);
        btn_send_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                sendCode();
                startActivity(new Intent(MainActivity.this, MobileVerificationActivity.class));
                break;
        }
    }

    private void sendCode() {
        String phone = phone_number.getText().toString().trim();
        apiInterface.initRegistration(phone).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
