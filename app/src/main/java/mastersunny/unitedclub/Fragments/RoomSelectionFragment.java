package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.activities.DateRoomSelectActivity;
import mastersunny.unitedclub.listeners.DateSelectionListener;
import mastersunny.unitedclub.rest.ApiInterface;

public class RoomSelectionFragment extends Fragment {

    public String TAG = "CheckinFragment";
    private Activity mActivity;
    private View view;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ApiInterface apiInterface;
    private Unbinder unbinder;
    private DateSelectionListener dateSelectionListener;

    private int count = 1;

    @BindView(R.id.guest_count1)
    Button guest_count1;

    @BindView(R.id.guest_count2)
    Button guest_count2;

    @BindView(R.id.guest_count3)
    Button guest_count3;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

        if (!(context instanceof DateRoomSelectActivity)) {
            return;
        }

        dateSelectionListener = (DateRoomSelectActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.room_selection_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.guest_count1, R.id.guest_count2, R.id.guest_count3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guest_count1:
                guest_count1.setSelected(true);
                count = 1;
                break;
            case R.id.guest_count2:
                count = 2;
                break;
            case R.id.guest_count3:
                count = 3;
                break;

        }
        dateSelectionListener.totalGuest(count);
    }
}
