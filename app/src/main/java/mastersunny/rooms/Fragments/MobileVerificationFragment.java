package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.activities.HomeActivity;
import mastersunny.rooms.activities.LoginActivity;
import mastersunny.rooms.activities.RegistrationActivity;
import mastersunny.rooms.listeners.LoginListener;
import mastersunny.rooms.models.CustomerResponseDto;
import mastersunny.rooms.models.RestModel;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationFragment extends Fragment {

    public String TAG = "MobileVerificationFragment";

    @BindView(R.id.one_time_password)
    EditText one_time_password;

    @BindView(R.id.phone_number)
    TextView phone_number;

    @BindView(R.id.timer_text)
    TextView timer_text;

    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.btn_resend_code)
    Button btn_resend_code;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private ApiInterface apiInterface;
    private CountDownTimer timer;
    private Handler handler;
    private int time = 60;
    private ProgressDialog pdLoading;
    private boolean alreadyRequest = false;

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    private Activity mActivity;
    private View view;
    private LoginListener loginListener;
    private Unbinder unbinder;

    private static String phoneNumber;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
        if (!(context instanceof LoginListener)) {
            throw new ClassCastException("Activity must implement LoginListener");
        }
        this.loginListener = (LoginActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mobile_verification, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            mAuth = FirebaseAuth.getInstance();
            handler = new Handler();
            getIntentData();
            initLayout();
            updateUI();
            initFirebaseCallBack();
        }
        return view;
    }

    private void getIntentData(){
        Bundle bundle = getArguments();
        if(bundle.getString("phoneNo")!=null) {
            phoneNumber = bundle.getString("phoneNo");
            phoneNumber = "+"+phoneNumber;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        sendCode(phoneNumber);
    }

    private void sendCode(String phoneNumber) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void initFirebaseCallBack() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Constants.debugLog(TAG, "onVerificationCompleted:" + credential);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mActivity, "verification completed", Toast.LENGTH_SHORT).show();

                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Constants.debugLog(TAG, "onVerificationFailed"+e.getMessage());

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                Toast.makeText(mActivity, "verification failed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Constants.debugLog(TAG, "onCodeSent:" + verificationId);

                Toast.makeText(mActivity, "Code send to your mobile no", Toast.LENGTH_SHORT).show();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                progressBar.setVisibility(View.GONE);

                // ...
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(mActivity, "Code verification success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(mActivity, "Code verification failed", Toast.LENGTH_SHORT).show();
                            btn_resend_code.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void updateUI() {
        phone_number.setText(phoneNumber);
    }


    private void initLayout() {
        btn_next.setAlpha(0.4f);
        btn_resend_code.setAlpha(0.4f);
        btn_resend_code.setClickable(false);
        progressBar = view.findViewById(R.id.progressBar);

        pdLoading = new ProgressDialog(mActivity);

        one_time_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btn_next.setAlpha(1);
                    btn_next.setClickable(true);
                } else {
                    btn_next.setAlpha(0.4f);
                    btn_next.setClickable(false);
                }
            }
        });
    }

    @OnClick
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_resend_code:
                resendVerificationCode(phoneNumber, mResendToken);
                break;
            case R.id.btn_next:
                verifyPhoneNumberWithCode(mVerificationId, one_time_password.getText().toString().trim());
                break;
            case R.id.back_button:
                if (!alreadyRequest) {
//                    finish();
                }
        }
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    public void showProgressDialog() {
        pdLoading = new ProgressDialog(mActivity);
        pdLoading.setMessage("Authorizing");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

}
