package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.adapters.SearchAdapter;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.LocalityDTO;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class RoomSearchFragment1 extends Fragment {

    public String TAG = "RoomSearchFragment1";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".roomsearchfragment1";

    private Activity mActivity;
    private View view;

    @BindView(R.id.rv_places)
    RecyclerView rv_places;

    private List<RoomDTO> roomDTOS = new ArrayList<>();
    private List<PlaceDTO> placeDTOS = new ArrayList<>();
    private Unbinder unbinder;
    private SearchAdapter searchAdapter;
    private RoomSearchListener roomSearchListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

        if (!(context instanceof RoomSearchActivity)) {
            return;
        }
        roomSearchListener = (RoomSearchActivity) context;
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
            view = inflater.inflate(R.layout.room_search_fragment_1, container, false);
            unbinder = ButterKnife.bind(this, view);
            initLayout();
        }

        return view;
    }

    private void initLayout() {
        rv_places.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(mActivity, roomDTOS, placeDTOS);
        rv_places.setAdapter(searchAdapter);
        searchAdapter.setItemSelectListener(new RoomSearchListener() {
            @Override
            public void onRecentSearch(RoomDTO roomDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onRecentSearch(roomDTO);
                }
            }

            @Override
            public void onPlaceSearch(PlaceDTO placeDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onPlaceSearch(placeDTO);
                }

            }

            @Override
            public void onLocalitySearch(LocalityDTO localityDTO) {

            }
        });
    }

    private void notifyPlaceAdapter() {
        if (searchAdapter != null) {
            searchAdapter.notifyDataSetChanged();
        }
    }


    private void loadData() {
        roomDTOS.add(new RoomDTO());
        roomDTOS.add(new RoomDTO());
        roomDTOS.add(new RoomDTO());

        placeDTOS.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        placeDTOS.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        placeDTOS.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        placeDTOS.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        placeDTOS.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyPlaceAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
