package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.LoginActivity;
import mastersunny.rooms.listeners.LoginListener;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;

public class SignUpFragment extends Fragment {

    private String TAG = "SignUpFragment";
    private Activity mActivity;
    private View view;
    private LoginListener loginListener;
    Unbinder unbinder;
    ApiInterface apiInterface;

    @BindView(R.id.user_name)
    EditText user_name;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.n_id)
    EditText n_id;

    @BindView(R.id.ssc_reg_no)
    EditText ssc_reg_no;

    @BindView(R.id.hsc_reg_no)
    EditText hsc_reg_no;

    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;

    String message = "Cannot be empty";

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
            view = inflater.inflate(R.layout.signup_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);
            pref = mActivity.getSharedPreferences(Constants.prefs, 0);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_sign_up})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                signUp();
                break;
        }
    }

    private void signUp() {
        String userName = user_name.getText().toString().trim();
        String emailAddress = email.getText().toString().trim();
        String nid = n_id.getText().toString().trim();
        String sscRegNo = ssc_reg_no.getText().toString().trim();
        String hscRegNo = hsc_reg_no.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            user_name.setError(message);
        } else if (TextUtils.isEmpty(nid)) {
            n_id.setError(message);
        } else if (TextUtils.isEmpty(sscRegNo)) {
            ssc_reg_no.setError(message);
        } else if (TextUtils.isEmpty(hscRegNo)) {
            hsc_reg_no.setError(message);
        } else {
//            CustomerResponseDto userDTO = new CustomerResponseDto(userName, emailAddress, nid, sscRegNo, hscRegNo);
//            Constants.debugLog(TAG, userDTO.toString());
//            apiInterface.signup(AccountKit.getCurrentAccessToken().getToken(), userDTO).enqueue(new Callback<CustomerResponseDto>() {
//                @Override
//                public void onResponse(Call<CustomerResponseDto> call, Response<CustomerResponseDto> response) {
//                    Constants.debugLog(TAG, response + "");
//                    if (response.isSuccessful()) {
//                        CustomerResponseDto userDTO = response.body();
//                        Constants.debugLog(TAG, "response " + userDTO);
//
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString(Constants.USER_NAME, userDTO.getName());
////                        editor.putString(Constants.PHONE_NUMBER, userDTO.getMobileNo());
////                        editor.putString(Constants.EMAIL, userDTO.getEmail());
////                        editor.putString(Constants.NID, userDTO.getNid());
////                        editor.putString(Constants.SSC_REG_NO, userDTO.getSscRegNo());
////                        editor.putString(Constants.HSC_REG_NO, userDTO.getHscRegNo());
////                        editor.putString(Constants.PROFILE_IMAGE, userDTO.getProfileImage());
//                        editor.commit();
//
//                        loginListener.loginCompleted();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CustomerResponseDto> call, Throwable t) {
//                    Constants.debugLog(TAG, t.getMessage());
//                }
//            });
        }

    }
}
