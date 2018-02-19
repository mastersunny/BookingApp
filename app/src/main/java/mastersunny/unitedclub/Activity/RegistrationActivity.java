package mastersunny.unitedclub.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    public String TAG = "RegistrationActivity";

    private EditText first_name, last_name, email;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ApiInterface apiInterface;

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

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface.signUp(first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), preferences.getString(Constants.PHONE_NUMBER, ""))
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Constants.debugLog(TAG, "" + response.body());
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Constants.debugLog(TAG, "" + t.getMessage());
                            }
                        });
            }
        });


    }
}
