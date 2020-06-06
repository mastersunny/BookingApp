package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.LoginActivity;
import mastersunny.rooms.listeners.LoginListener;

public class InitLoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";

    private Activity mActivity;
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
            view = inflater.inflate(R.layout.login_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        return view;
    }

    @OnClick({R.id.btn_sms_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sms_login:
                if(loginListener!=null){
                    loginListener.insertPhoneNumber();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

