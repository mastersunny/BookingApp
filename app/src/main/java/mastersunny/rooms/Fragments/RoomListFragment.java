package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.PagerAdapter;
import mastersunny.rooms.adapters.RoomAdapter;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.utils.Constants;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class RoomListFragment extends Fragment {

    public String TAG = "RoomListFragment";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".ROOM_LIST_FRAGMENT_TAG";

    private Activity mActivity;
    private View view;

    @BindView(R.id.rv_rooms)
    RecyclerView rv_rooms;

    RoomAdapter roomAdapter;

    private List<RoomDTO> roomDTOS;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (roomDTOS.size() <= 0) {
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.room_list_fragment, container, false);
            unbinder = ButterKnife.bind(this, view);
            initLayout();
        }

        return view;
    }

    private void initLayout() {
        roomDTOS = new ArrayList<>();
        rv_rooms.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        roomAdapter = new RoomAdapter(mActivity, roomDTOS);
        rv_rooms.setAdapter(roomAdapter);
    }

    private void notifyPlaceAdapter() {
        if (roomAdapter != null) {
            roomAdapter.notifyDataSetChanged();
        }
//        checkNoData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadData() {
        roomDTOS.add(new RoomDTO("THE WAY DHAKA", 23.7968, 90.4115, 12484));
        roomDTOS.add(new RoomDTO("Four Points By Sheraton DHaka, Gulshan", 23.7944, 90.4137, 15436));
        roomDTOS.add(new RoomDTO("Century Residence Park", 23.7856724, 90.4186784, 6748));
        roomDTOS.add(new RoomDTO("Asia Hotel & Resorts", 23.7306626, 90.4067831, 5061));

        notifyPlaceAdapter();
    }
}
