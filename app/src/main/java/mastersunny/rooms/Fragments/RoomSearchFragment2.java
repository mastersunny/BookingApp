package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.CityAdapter;
import mastersunny.rooms.adapters.RecentSearchAdapter;
import mastersunny.rooms.models.ItemType;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class RoomSearchFragment2 extends Fragment {

    public String TAG = "RoomSearchFragment2";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".roomsearchfragment2";

    private Activity mActivity;
    private View view;

    @BindView(R.id.rv_places)
    RecyclerView rv_places;

    @BindView(R.id.tv_city_name)
    TextView tv_city_name;

    public static PlaceDTO placeDTO;
    private List<PlaceDTO> placeDTOS = new ArrayList<>();
    private Unbinder unbinder;
    private CityAdapter cityAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (placeDTOS.size() <= 0) {
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.room_search_fragment_2, container, false);
            unbinder = ButterKnife.bind(this, view);
            initLayout();
        }

        return view;
    }

    private void initLayout() {
        rv_places.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        cityAdapter = new CityAdapter(mActivity, placeDTOS);
        rv_places.setAdapter(cityAdapter);
    }

    private void notifyPlaceAdapter() {
        if (cityAdapter != null) {
            cityAdapter.notifyDataSetChanged();
        }
    }


    private void loadData() {
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
