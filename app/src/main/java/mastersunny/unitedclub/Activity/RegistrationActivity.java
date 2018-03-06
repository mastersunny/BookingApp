package mastersunny.unitedclub.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.Model.RestModel;
import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, Callback<RestModel> {

    public String TAG = RegistrationActivity.class.getSimpleName();
    private EditText first_name, last_name, email;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private String firstName, lastName, emailAddress, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);
        editor = preferences.edit();
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
        try {
            progressBar.setVisibility(View.VISIBLE);
            firstName = first_name.getText().toString();
            lastName = last_name.getText().toString();
            emailAddress = email.getText().toString();
            phoneNumber = preferences.getString(Constants.PHONE_NUMBER, "");
            Constants.debugLog(TAG,
                    "firstName " + firstName
                            + " lastName " + lastName
                            + " email " + emailAddress
                            + " phoneNumber " + phoneNumber);
            apiInterface.signUp(firstName, lastName, emailAddress, preferences.getString(Constants.PHONE_NUMBER, ""))
                    .enqueue(this);
        } catch (Exception e) {
            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
        }
    }

    @Override
    public void onResponse(Call<RestModel> call, Response<RestModel> response) {
        Constants.debugLog(TAG, "" + response);
        progressBar.setVisibility(View.GONE);

        if (response.isSuccessful() && response.body() != null && response.body().getMetaData().isSuccess()) {
            if (response.body().getMetaData().isData()) {
                UserDTO userDTO = response.body().getUserDTO();
                Constants.debugLog(TAG, userDTO.toString());
                editor.putInt(Constants.STORE_ID, userDTO.getStoreId());
                editor.putString(Constants.FIRST_NAME, userDTO.getFirstName());
                editor.putString(Constants.LAST_NAME, userDTO.getLastName());
                editor.putString(Constants.EMAIL, userDTO.getEmail());
                editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                editor.putString(Constants.COVER_IMAGE_URL, userDTO.getImgUrl());
                editor.putString(Constants.ACCESS_TOKEN, userDTO.getAccessToken());
                editor.apply();
                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                finish();
            }
        } else {
            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
            Constants.debugLog(TAG, "error in registration");
        }
    }

    @Override
    public void onFailure(Call<RestModel> call, Throwable t) {
        Constants.debugLog(TAG, "" + t.getMessage());
        Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
    }
}
