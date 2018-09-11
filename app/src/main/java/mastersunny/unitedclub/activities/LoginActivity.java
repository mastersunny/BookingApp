package mastersunny.unitedclub.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        pref = getSharedPreferences(Constants.prefs, 0);
        initLayout();
    }

    private void initLayout() {
        progressBar = findViewById(R.id.progressBar);
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
        Constants.loginSucccessful = true;
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface.sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressBar.setVisibility(View.GONE);
                    Constants.debugLog(TAG, response + "");
                    if (response.isSuccessful()) {
                        Constants.debugLog(TAG, response.body());
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
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
