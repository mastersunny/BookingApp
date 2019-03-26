package mastersunny.rooms.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import mastersunny.rooms.models.RestModel;
import mastersunny.rooms.models.UserDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
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
    private boolean alreadyRequest = false;
    private Handler handler = new Handler();

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

        findViewById(R.id.back_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (!alreadyRequest) {
                    alreadyRequest = true;
                    sendRegistration();
                }
                break;
            case R.id.back_button:
                RegistrationActivity.this.finish();
                break;
        }
    }

    private void sendRegistration() {
        try {
            refreshHandler();
            progressBar.setVisibility(View.VISIBLE);
            firstName = first_name.getText().toString().trim();
            lastName = last_name.getText().toString().trim();
            emailAddress = email.getText().toString().trim();
            phoneNumber = preferences.getString(Constants.PHONE_NUMBER, "");
            Constants.debugLog(TAG,
                    "firstName " + firstName
                            + " lastName " + lastName
                            + " email " + emailAddress
                            + " phoneNumber " + phoneNumber);
            apiInterface
                    .signUp(firstName, lastName, emailAddress, preferences.getString(Constants.PHONE_NUMBER, ""))
                    .enqueue(this);
        } catch (Exception e) {
            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
        }
    }

    @Override
    public void onResponse(Call<RestModel> call, Response<RestModel> response) {
        Constants.debugLog(TAG, "" + response);

        if (response.isSuccessful() && response.body() != null && response.body().getMetaData().isSuccess()) {
            if (response.body().getMetaData().isData()) {
                UserDTO userDTO = response.body().getUserDTO();
                Constants.debugLog(TAG, userDTO.toString());
                editor.putInt(Constants.STORE_ID, 1);
                editor.putString(Constants.FIRST_NAME, userDTO.getEmail());
                editor.putString(Constants.LAST_NAME, userDTO.getName());
                editor.putString(Constants.EMAIL, userDTO.getEmail());
                editor.putString(Constants.PHONE_NUMBER, phoneNumber);
                editor.putString(Constants.COVER_IMAGE_URL, userDTO.getProfileImage());
                editor.putString(Constants.ACCESS_TOKEN, userDTO.getEmail());
                editor.apply();
                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                finish();
            }
        } else {
            Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
            Constants.debugLog(TAG, "error in registration");
        }

        progressBar.setVisibility(View.GONE);
        alreadyRequest = false;
    }

    @Override
    public void onFailure(Call<RestModel> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        alreadyRequest = false;
        Constants.debugLog(TAG, "" + t.getMessage());
        Constants.showDialog(RegistrationActivity.this, "Cannot register at this moment");
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
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
