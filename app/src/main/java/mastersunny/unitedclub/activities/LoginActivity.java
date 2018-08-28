package mastersunny.unitedclub.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mastersunny.unitedclub.Fragments.LoginFragment;
import mastersunny.unitedclub.Fragments.SignUpFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.listeners.LoginListener;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        finish();
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
