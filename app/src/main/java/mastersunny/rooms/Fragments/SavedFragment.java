package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.AboutUsActivity;
import mastersunny.rooms.adapters.RoomAdapter;
import mastersunny.rooms.adapters.SavedRoomAdapter;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.models.RoomImageDTO;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class SavedFragment extends Fragment {

    public String TAG = "SavedFragment";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".savedfragment";

    private Activity mActivity;
    private View view;

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_saved_rooms)
    RecyclerView rv_saved_rooms;

    private List<RoomDTO> roomDTOS = new ArrayList<>();

    SavedRoomAdapter savedRoomAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.saved_fragment_layout, container, false);
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
        rv_saved_rooms.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        rv_saved_rooms.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));
        savedRoomAdapter = new SavedRoomAdapter(mActivity);
        rv_saved_rooms.setAdapter(savedRoomAdapter);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("প্রোফাইল");
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

//        int res = getResources().getIdentifier(getPackageName() + ":drawable/" + memberDTO.getImgUrl(), null, null);
//        img_profile.setImageResource(res);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (roomDTOS.size() <= 0) {
            loadData();
        }
    }

    private void loadData() {

        RoomDTO roomDTO1 = new RoomDTO("THE WAY DHAKA", 23.7968, 90.4115, 12484);
        RoomDTO roomDTO2 = new RoomDTO("Four Points By Sheraton DHaka, Gulshan", 23.7944, 90.4137, 15436);
        RoomDTO roomDTO3 = new RoomDTO("Century Residence Park", 23.7856724, 90.4186784, 6748);
        RoomDTO roomDTO4 = new RoomDTO("Asia Hotel & Resorts", 23.7306626, 90.4067831, 5061);

        roomDTO1.getImages().add(new RoomImageDTO("room1"));
        roomDTO2.getImages().add(new RoomImageDTO("room2"));
        roomDTO3.getImages().add(new RoomImageDTO("room3"));
        roomDTO4.getImages().add(new RoomImageDTO("room4"));

        roomDTOS.add(roomDTO1);
        roomDTOS.add(roomDTO2);
        roomDTOS.add(roomDTO3);
        roomDTOS.add(roomDTO4);

        for (int i = 0; i < roomDTOS.size(); i++) {
            savedRoomAdapter.add(roomDTOS.get(i), i);
        }
    }

}
