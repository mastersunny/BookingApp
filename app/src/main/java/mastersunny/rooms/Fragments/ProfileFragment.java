package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.activities.AboutUsActivity;
import mastersunny.rooms.activities.EditProfileActivity;
import mastersunny.rooms.activities.TransactionActivity;
import mastersunny.rooms.adapters.PendingTransactionAdapter;
import mastersunny.rooms.models.RestModel;
import mastersunny.rooms.models.TransactionDTO;
import mastersunny.rooms.models.UserDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.CircleImageView;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class ProfileFragment extends Fragment {

    public String TAG = "ProfileFragment";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".profilefragment";

    private Activity mActivity;
    private View view;

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            initLayout();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initLayout() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("প্রোফাইল");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

//        int res = getResources().getIdentifier(getPackageName() + ":drawable/" + memberDTO.getImgUrl(), null, null);
//        img_profile.setImageResource(res);
    }

    @OnClick({R.id.profile_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_layout:
                Intent intent = new Intent(mActivity, AboutUsActivity.class);
                mActivity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
