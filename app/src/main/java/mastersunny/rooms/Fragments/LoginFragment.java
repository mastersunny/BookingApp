package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import mastersunny.rooms.R;
import mastersunny.rooms.activities.LoginActivity;
import mastersunny.rooms.listeners.LoginListener;
import mastersunny.rooms.models.CustomerResponseDto;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import mastersunny.rooms.utils.PermissionUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private View view;
    private Button btn_sms_login;
    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private String TAG = "LoginFragment";
    private LoginListener loginListener;
    private LinearLayout login_layout;
    MediaPlayer mediaPlayer = null;
    private int audioClip;
    SharedPreferences pref;

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
            view = inflater.inflate(R.layout.login_fragment_layout, container, false);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);
            pref = mActivity.getSharedPreferences(Constants.prefs, 0);
            initLayout();
        }
        return view;
    }

    private void initLayout() {
        btn_sms_login = view.findViewById(R.id.btn_sms_login);
        btn_sms_login.setOnClickListener(this);
        progressBar = view.findViewById(R.id.progressBar);
        login_layout = view.findViewById(R.id.login_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sms_login:
                if (!PermissionUtils.hasPermissions(getContext(), PermissionUtils.PERMISSION_SMS)) {
                    requestPermissions(PermissionUtils.PERMISSION_SMS, Constants.PERMISSION_READ_SMS);
                } else {
                    phoneLogin();
                }
                break;
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(mActivity, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, Constants.REQUEST_FACEBOOK_LOGIN_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_FACEBOOK_LOGIN_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(
                        mActivity,
                        toastMessage,
                        Toast.LENGTH_LONG)
                        .show();
            } else if (loginResult.wasCancelled()) {
                Toast.makeText(
                        mActivity,
                        "Login canceled",
                        Toast.LENGTH_LONG)
                        .show();
            } else {
                login_layout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                try {
                    apiInterface.login(AccountKit.getCurrentAccessToken().getToken()).enqueue(new Callback<CustomerResponseDto>() {
                        @Override
                        public void onResponse(Call<CustomerResponseDto> call, Response<CustomerResponseDto> response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                CustomerResponseDto userDTO = response.body();
                                Constants.debugLog(TAG, "response " + userDTO);

                                if (userDTO.getId() != null && userDTO.getMobileNo() != null) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString(Constants.USER_NAME, userDTO.getName());
//                                    editor.putString(Constants.PHONE_NUMBER, userDTO.getMobileNo());
//                                    editor.putString(Constants.EMAIL, userDTO.getEmail());
//                                    editor.putString(Constants.NID, userDTO.getNid());
//                                    editor.putString(Constants.SSC_REG_NO, userDTO.getSscRegNo());
//                                    editor.putString(Constants.HSC_REG_NO, userDTO.getHscRegNo());
//                                    editor.putString(Constants.PROFILE_IMAGE, userDTO.getProfileImage());
                                    editor.commit();

                                    loginListener.loginCompleted();
                                } else {
                                    loginListener.signUp();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CustomerResponseDto> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Constants.debugLog(TAG, t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    login_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Constants.debugLog(TAG, e.getMessage());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_READ_SMS) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phoneLogin();
            }
        }
    }

    private void startPlaying(String fileName) {
        try {
            audioClip = getResources().getIdentifier(fileName, "raw", mActivity.getPackageName());
            mediaPlayer = MediaPlayer.create(mActivity, audioClip);
            mediaPlayer.start();
        } catch (Exception e) {
            Constants.debugLog(TAG, "mediaPlayer " + e.getMessage());
        }
    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        startPlaying("audio_clip2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlaying();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlaying();
    }
}

