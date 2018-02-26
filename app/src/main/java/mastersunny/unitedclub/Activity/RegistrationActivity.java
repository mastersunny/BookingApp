package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "RegistrationActivity";

    private EditText first_name, last_name, email;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);
        editor = preferences.edit();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        initLayout();
    }

    private void initLayout() {
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btn_register).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                sendRegistration();
                break;
        }
    }

    private void sendRegistration() {
        progressBar.setVisibility(View.VISIBLE);
        final String firstName = first_name.getText().toString();
        final String lastName = last_name.getText().toString();
        final String emailAddress = email.getText().toString();
        final String phoneNumber = preferences.getString(Constants.PHONE_NUMBER, "");

        Constants.debugLog(TAG, "first " + firstName + " last " + lastName
                + " email " + emailAddress + " phone " + phoneNumber);
        try {
            apiInterface
                    .signUp(firstName, lastName, emailAddress,
                            preferences.getString(Constants.PHONE_NUMBER, ""))
                    .enqueue(new Callback<AccessModel>() {
                        @Override
                        public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                            progressBar.setVisibility(View.GONE);
                            Constants.debugLog(TAG, "" + response);
                            if (response.isSuccessful() && response.body() != null && response.body().getAccessToken().length() > 0) {
                                Log.d(TAG, "" + response.body());
                                AccessModel accessModel = response.body();
                                editor.putString(Constants.FIRST_NAME, firstName);
                                editor.putString(Constants.LAST_NAME, lastName);
                                editor.putString(Constants.EMAIL, emailAddress);
                                editor.putString(Constants.ACCESS_TOKEN, accessModel.getAccessToken());
                                editor.apply();
                                startActivity(new Intent(RegistrationActivity.this, ClientMainActivity.class));
                                finish();
                            } else {
                                Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
                                Constants.debugLog(TAG, "ERROR");
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessModel> call, Throwable t) {
                            Constants.debugLog(TAG, "" + t.getMessage());
                            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");

                        }
                    });
        } catch (Exception e) {
            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
        }
    }
}
