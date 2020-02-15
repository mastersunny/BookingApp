package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomListActivity;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.adapters.LocalityAdapter;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.ApiResponse;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.DistrictResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    public DivisionResponseDto placeDTO;
    private List<DistrictResponseDto> districts = new ArrayList<>();
    private Unbinder unbinder;
    private LocalityAdapter localityAdapter;
    private RoomSearchListener roomSearchListener;
    private ApiInterface apiInterface;

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
        tv_city_name.setText("All of " + placeDTO.getName());
        super.onResume();
        if (districts.size() <= 0) {
            loadDistricts();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            tv_city_name.setText("All of " + placeDTO.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.room_search_fragment_2, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            initLayout();
        }

        return view;
    }

    private void initLayout() {
        rv_places.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        localityAdapter = new LocalityAdapter(mActivity, districts);
        rv_places.setAdapter(localityAdapter);
        localityAdapter.setItemSelectListener(new RoomSearchListener() {
            @Override
            public void onRecentSearch(RoomDTO roomDTO) {

            }

            @Override
            public void onPlaceSearch(DivisionResponseDto placeDTO) {

            }

            @Override
            public void onLocalitySearch(DistrictResponseDto localityDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onLocalitySearch(localityDTO);
                }
            }
        });
    }

    private void notifyPlaceAdapter() {
        if (localityAdapter != null) {
            localityAdapter.notifyDataSetChanged();
        }
    }


    private void loadDistricts() {
        apiInterface.getDistricts(placeDTO.getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful() && response.body() != null) {
                    Constants.debugLog(TAG, response.body().getDistricts().toString());
                    updateDistricts(response.body().getDistricts());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });

        notifyPlaceAdapter();
    }

    private void updateDistricts(List<DistrictResponseDto> districts) {
        this.districts.clear();
        this.districts.addAll(districts);
        localityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    @OnClick({R.id.all_locality_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_locality_layout:
                startRoomListActivity();
                break;
        }
    }

    private void startRoomListActivity() {
        Intent intent = new Intent(mActivity, RoomListActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
    }
}
