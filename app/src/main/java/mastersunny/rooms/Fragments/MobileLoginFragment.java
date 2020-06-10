package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.ornach.richtext.RichEditText;

import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
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

public class MobileLoginFragment extends Fragment {

    public static final String TAG = "MobileLoginFragment";

    private Activity mActivity;
    private RichEditText phone_number;
    private Button btn_send_code;
    private CountryCodePicker countryCodePicker;
    private View view;
    private LoginListener loginListener;
    private Unbinder unbinder;

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
            view = inflater.inflate(R.layout.fragment_mobile_login, container, false);
            unbinder = ButterKnife.bind(this, view);
            initLayout();

//            initFirebaseCallBack();
        }
        return view;
    }

    private void initLayout() {
        phone_number = view.findViewById(R.id.phone_number);
        btn_send_code = view.findViewById(R.id.btn_send_code);
        btn_send_code.setAlpha(0.4f);
        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btn_send_code.setAlpha(1);
                    btn_send_code.setClickable(true);
                } else {
                    btn_send_code.setAlpha(0.5f);
                    btn_send_code.setClickable(false);
                }
            }
        });

        countryCodePicker = view.findViewById(R.id.ccp);
    }

    @OnClick({R.id.btn_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                if(loginListener!=null){
                    String countryCode = countryCodePicker.getSelectedCountryCode().trim();
                    String phoneNumber = phone_number.getText().toString().trim();
                    if(loginListener!=null){
                        loginListener.verifyPhoneNumber(countryCode+phoneNumber);
                    }
                }
                break;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
