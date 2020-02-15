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
import mastersunny.rooms.adapters.HomeAdapter;
import mastersunny.rooms.models.ApiResponse;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class HomeFragment extends Fragment {

    public String TAG = "HomeFragment";
    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".homefragment";

    private Activity mActivity;
    private View view;
    private ApiInterface apiInterface;
    private Unbinder unbinder;

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    HomeAdapter homeAdapter;

    private List<DivisionResponseDto> divisions = new ArrayList<>();
    private List<DivisionResponseDto> popularPlaces = new ArrayList<>();
    private List<DivisionResponseDto> deals = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);

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
        rv_home.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        homeAdapter = new HomeAdapter(mActivity);
        rv_home.setAdapter(homeAdapter);

//        rv_popular.setLayoutManager(new GridLayoutManager(mActivity, 2));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        rv_popular.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
//        rv_popular.setNestedScrollingEnabled(false);
//        popularAdapter = new PopularAdapter(mActivity, popularPlaces);
//        rv_popular.setAdapter(popularAdapter);
//
//        rv_deals.setLayoutManager(new GridLayoutManager(mActivity, 2));
//        rv_deals.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
//        rv_deals.setNestedScrollingEnabled(false);
//        dealAdapter = new DealAdapter(mActivity, deals);
//        rv_deals.setAdapter(dealAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (divisions.size() <= 0) {
            loadCityData();
        }
        if (popularPlaces.size() <= 0) {
            loadPopularData();
        }
        if (deals.size() <= 0) {
            loadDealData();
        }
        homeAdapter.notifyDataSetChanged();
    }

    private void loadCityData() {
//        placeDTOS.add(new DivisionResponseDto("Dhaka", "ঢাকা", "dhaka"));
//        placeDTOS.add(new DivisionResponseDto("Sylhet", "সিলেট", "sylhet"));
//        placeDTOS.add(new DivisionResponseDto("Rajshahi", "রাজশাহী", "rajshahi"));
//        placeDTOS.add(new DivisionResponseDto("Bogura", "বগুড়া", "dhaka"));
//        placeDTOS.add(new DivisionResponseDto("Khulna", "খুলনা", "dhaka"));
//        placeDTOS.add(new DivisionResponseDto("Chottogram", "চট্টগ্রাম", "dhaka"));
//        placeDTOS.add(new DivisionResponseDto("Barishal", "বরিশাল", "dhaka"));
        apiInterface.getDivisions().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful() && response.body() != null) {
                    Constants.debugLog(TAG, response.body().toString());
                    updateDivisionData(response.body().getDivisions());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });


    }

    private void updateDivisionData(List<DivisionResponseDto> divisions) {
        this.divisions.clear();
        this.divisions.addAll(divisions);
        homeAdapter.setDivisions(this.divisions);
    }

    private void loadPopularData() {
        popularPlaces.add(new DivisionResponseDto("Dhaka", "ঢাকা", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Sylhet", "সিলেট", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Rajshahi", "রাজশাহী", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Bogura", "বগুড়া", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Khulna", "খুলনা", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Chottogram", "চট্টগ্রাম", "dhaka"));
        popularPlaces.add(new DivisionResponseDto("Barishal", "বরিশাল", "dhaka"));

        homeAdapter.setPopularPlaces(popularPlaces);
    }


    private void loadDealData() {
        deals.add(new DivisionResponseDto("Dhaka", "ঢাকা", "dhaka"));
        deals.add(new DivisionResponseDto("Sylhet", "সিলেট", "dhaka"));
        deals.add(new DivisionResponseDto("Rajshahi", "রাজশাহী", "dhaka"));
        deals.add(new DivisionResponseDto("Bogura", "বগুড়া", "dhaka"));
        deals.add(new DivisionResponseDto("Khulna", "খুলনা", "dhaka"));
        deals.add(new DivisionResponseDto("Chottogram", "চট্টগ্রাম", "dhaka"));
        deals.add(new DivisionResponseDto("Barishal", "বরিশাল", "dhaka"));

        homeAdapter.setDeals(deals);
    }

//    @OnClick({R.id.search_layout})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.search_layout:
//                Intent intent = new Intent(mActivity, RoomSearchActivity.class);
//                startActivity(intent);
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
