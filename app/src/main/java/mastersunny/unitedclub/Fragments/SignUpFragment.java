package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.accountkit.AccountKit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.activities.LoginActivity;
import mastersunny.unitedclub.listeners.LoginListener;
import mastersunny.unitedclub.models.UserDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            UserDTO userDTO = new UserDTO(userName, emailAddress, nid, sscRegNo, hscRegNo);
            Constants.debugLog(TAG, userDTO.toString());
            apiInterface.signup(AccountKit.getCurrentAccessToken().getToken(), userDTO).enqueue(new Callback<UserDTO>() {
                @Override
                public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                    Constants.debugLog(TAG, response + "");
                    if (response.isSuccessful()) {
                        Constants.debugLog(TAG, response.body().toString());
                        loginListener.loginCompleted();
                    }
                }

                @Override
                public void onFailure(Call<UserDTO> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                }
            });
        }

    }
}
