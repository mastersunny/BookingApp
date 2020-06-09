package mastersunny.rooms.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import mastersunny.rooms.Fragments.InitLoginFragment;
import mastersunny.rooms.Fragments.MobileLoginFragment;
import mastersunny.rooms.Fragments.MobileVerificationFragment;
import mastersunny.rooms.Fragments.SignUpFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.entities.CustomerEntity;
import mastersunny.rooms.listeners.LoginListener;
import mastersunny.rooms.models.ApiResponse;
import mastersunny.rooms.models.CustomerLoginRequestDto;
import mastersunny.rooms.models.CustomerRequestDto;
import mastersunny.rooms.models.CustomerResponseDto;
import mastersunny.rooms.repositories.CustomerRepository;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import mastersunny.rooms.utils.ObjectSaveHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private String TAG = "LoginActivity";
    private ApiInterface apiInterface;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        initLayout();
    }

    private void initLayout() {
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (!isFinishing()) {
            initLogin();
        }
    }

    @Override
    public void initLogin() {
        InitLoginFragment fragment = new InitLoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void insertPhoneNumber() {
        MobileLoginFragment mobileLoginFragment = new MobileLoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, mobileLoginFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("phoneNo", phoneNumber);
        MobileVerificationFragment fragment = new MobileVerificationFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void customerLogin(String phoneNumber) {
        CustomerLoginRequestDto requestDto = new CustomerLoginRequestDto();
        requestDto.setMobileNo(phoneNumber);
        apiInterface.loginCustomer(requestDto).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Constants.debugLog(TAG, response+"");
                if(response.isSuccessful()&& response.body()!=null){
                    Constants.debugLog(TAG, response.body().getCustomer().toString());
                    if(response.body().getCustomer()!=null){
                        saveCustomer(response.body().getCustomer());
                    }else {
                        signUp();
                    }
                }else {
                    onBackPressed();
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                onBackPressed();
                Constants.debugLog(TAG, t.getMessage());
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCustomer(CustomerResponseDto customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setMobileNo(customer.getMobileNo());
        customerEntity.setName(customer.getName());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setCustomerId(customer.getId());

        ObjectSaveHelper.getInstance().saveObject(CustomerResponseDto.TAG, customer);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(CustomerResponseDto.TAG, customer);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void loginSuccess() {
//        if (FirebaseInstanceId.getInstance().getToken() != null) {
//            progressBar.setVisibility(View.VISIBLE);
//            apiInterface.sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    progressBar.setVisibility(View.GONE);
//                    Constants.debugLog(TAG, response + "");
//                    if (response.isSuccessful()) {
//                        Constants.debugLog(TAG, response.body());
//                    }
//                    finish();
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    progressBar.setVisibility(View.GONE);
//                    Constants.debugLog(TAG, t.getMessage());
//                    finish();
//                }
//            });
//        }
    }

    @Override
    public void loginCanceled() {
        Toast.makeText(this, "Login cancelled", Toast.LENGTH_SHORT).show();
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
