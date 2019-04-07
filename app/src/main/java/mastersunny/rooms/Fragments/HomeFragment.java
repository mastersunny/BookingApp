package mastersunny.rooms.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.HomeActivity;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.adapters.DealAdapter;
import mastersunny.rooms.adapters.PlaceAdapter;
import mastersunny.rooms.adapters.PopularAdapter;
import mastersunny.rooms.adapters.SpacesItemDecoration;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;


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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_cities)
    RecyclerView rv_cities;

    @BindView(R.id.rv_popular)
    RecyclerView rv_popular;

    @BindView(R.id.rv_deals)
    RecyclerView rv_deals;

    @BindView(R.id.search_layout)
    RelativeLayout search_layout;

    PlaceAdapter placeAdapter;
    private List<PlaceDTO> placeDTOS = new ArrayList<>();

    PopularAdapter popularAdapter;
    private List<PlaceDTO> popularPlaces = new ArrayList<>();

    DealAdapter dealAdapter;
    private List<PlaceDTO> deals = new ArrayList<>();


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
        rv_cities.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        rv_cities.setHasFixedSize(true);
        rv_cities.setNestedScrollingEnabled(false);
        placeAdapter = new PlaceAdapter(mActivity, placeDTOS);
        rv_cities.setAdapter(placeAdapter);

        rv_popular.setLayoutManager(new GridLayoutManager(mActivity, 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        rv_popular.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv_popular.setNestedScrollingEnabled(false);
        popularAdapter = new PopularAdapter(mActivity, popularPlaces);
        rv_popular.setAdapter(popularAdapter);

        rv_deals.setLayoutManager(new GridLayoutManager(mActivity, 2));
        rv_deals.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        rv_deals.setNestedScrollingEnabled(false);
        dealAdapter = new DealAdapter(mActivity, deals);
        rv_deals.setAdapter(dealAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (placeDTOS.size() <= 0) {
            loadData();
        }
    }


    private void loadData() {
        placeDTOS.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Sylhet", "সিলেট", "sylhet"));
        placeDTOS.add(new PlaceDTO("Rajshahi", "রাজশাহী", "rajshahi"));
        placeDTOS.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        placeDTOS.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        placeDTOS.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyPlaceAdapter();

        popularPlaces.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        popularPlaces.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        popularPlaces.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        popularPlaces.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        popularPlaces.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        popularPlaces.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        popularPlaces.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyPopularAdapter();

        deals.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        deals.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        deals.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        deals.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        deals.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        deals.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        deals.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyDealAdapter();
    }

    private void notifyPlaceAdapter() {
        if (placeAdapter != null) {
            placeAdapter.notifyDataSetChanged();
        }
    }

    private void notifyPopularAdapter() {
        if (popularAdapter != null) {
            popularAdapter.notifyDataSetChanged();
        }
    }

    private void notifyDealAdapter() {
        if (dealAdapter != null) {
            dealAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.search_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout:
                Intent intent = new Intent(mActivity, RoomSearchActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
