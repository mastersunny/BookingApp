package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 1/21/2018.
 */

public class LoginActivity extends AppCompatActivity implements Callback<UserDTO> {

    public String TAG = "LoginActivity";
    private EditText phone_number, password;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUserLogin();

            }
        });
    }

    private void submitUserLogin() {
        progressBar.setVisibility(View.VISIBLE);
        String phone = phone_number.getText().toString().trim();
        String pass = password.getText().toString().trim();

        apiService.logIn(phone, pass).enqueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {

        Log.d(TAG, "" + response.body());
        int userType = 1;
        Intent mainIntent = null;
        switch (userType) {
            case Constants.USER_TYPE_CLIENT:
                mainIntent = new Intent(LoginActivity.this, ClientMainActivity.class);
                break;
            case Constants.USER_TYPE_MERCHANT:
                mainIntent = new Intent(LoginActivity.this, MerchantMainActivity.class);
                break;
            case Constants.USER_TYPE_ADMIN:
                break;
        }
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onFailure(Call<UserDTO> call, Throwable t) {

    }
}
