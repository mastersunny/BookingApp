package mastersunny.unitedclub.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.iid.FirebaseInstanceId;

import mastersunny.unitedclub.Fragments.LoginFragment;
import mastersunny.unitedclub.Fragments.SignUpFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.listeners.LoginListener;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private String TAG = "LoginActivity";
    private ApiInterface apiInterface;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        pref = getSharedPreferences(Constants.prefs, 0);
        initLayout();
    }

    private void initLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (!isFinishing()) {
            LoginFragment fragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void loginCompleted() {
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            apiInterface.sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Constants.debugLog(TAG, response + "");
                    if (response.isSuccessful()) {
                        Constants.debugLog(TAG, response.body());
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                    finish();
                }
            });
        }
    }

    @Override
    public void signUp() {
        if (!isFinishing()) {
            SignUpFragment fragment = new SignUpFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
