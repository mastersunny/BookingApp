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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.adapters.LocalityAdapter;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.LocalityDTO;
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
    private List<LocalityDTO> localityDTOS = new ArrayList<>();
    private Unbinder unbinder;
    private LocalityAdapter localityAdapter;
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
        if (localityDTOS.size() <= 0) {
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
        localityAdapter = new LocalityAdapter(mActivity, localityDTOS);
        rv_places.setAdapter(localityAdapter);
        localityAdapter.setItemSelectListener(new RoomSearchListener() {
            @Override
            public void onRecentSearch(RoomDTO roomDTO) {

            }

            @Override
            public void onPlaceSearch(PlaceDTO placeDTO) {

            }

            @Override
            public void onLocalitySearch(LocalityDTO localityDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onLocalitySearch(localityDTO);
                }
            }
        });

        view.findViewById(R.id.all_locality_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (roomSearchListener != null) {
//                    roomSearchListener.onLocalitySearch(localityDTO);
//                }
            }
        });
    }

    private void notifyPlaceAdapter() {
        if (localityAdapter != null) {
            localityAdapter.notifyDataSetChanged();
        }
    }


    private void loadData() {
        localityDTOS.add(new LocalityDTO("Dhamrai", "ঢাকা", "dhaka"));
        localityDTOS.add(new LocalityDTO("Dohar", "সিলেট", "dhaka"));
        localityDTOS.add(new LocalityDTO("Keraniganj ", "রাজশাহী", "dhaka"));
        localityDTOS.add(new LocalityDTO("Nawabganj", "বগুড়া", "dhaka"));
        localityDTOS.add(new LocalityDTO("Savar", "খুলনা", "dhaka"));
        localityDTOS.add(new LocalityDTO("Tejgaon", "চট্টগ্রাম", "dhaka"));

        notifyPlaceAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
