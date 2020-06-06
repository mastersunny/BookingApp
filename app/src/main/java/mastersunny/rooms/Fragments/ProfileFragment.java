package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.activities.AboutUsActivity;
import mastersunny.rooms.R;


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
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("প্রোফাইল");
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

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
