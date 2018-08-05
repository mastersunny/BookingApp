package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.unitedclub.activities.StoresActivity;
import mastersunny.unitedclub.activities.SearchActivity;
import mastersunny.unitedclub.activities.StoresDetailsActivity;
import mastersunny.unitedclub.adapters.AutoScrollAdapter;
import mastersunny.unitedclub.adapters.NearbyPlaceAdapter;
import mastersunny.unitedclub.adapters.OfferAdapter;
import mastersunny.unitedclub.adapters.RecommendedAdapter;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.models.SliderDTO;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.models.OfferDTO;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.barcode.BarcodeCaptureActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    public String TAG = "HomeFragment";
    private Activity mActivity;
    private View view;
    //    private ViewPager viewPager;
    private Toolbar toolbar;
    private NearbyPlaceAdapter nearbyPlaceAdapter;
    private RecyclerView nearby_rv;
    private ArrayList<StoreDTO> storeDTOS;
    //    private TextView search_text;
    private AppBarLayout appBarLayout;
    private AutoScrollAdapter autoScrollAdapter;
    private ArrayList<SliderDTO> autoScrollList;
    private ApiInterface apiService;


    private Unbinder unbinder;

    @BindView(R.id.recommended_rv)
    RecyclerView recommended_rv;
    private List<RoomDTO> roomDTOList;
    private RecommendedAdapter recommendedAdapter;

    @BindView(R.id.offer_rv)
    RecyclerView offer_rv;
    private List<OfferDTO> offerDTOS;
    private OfferAdapter offerAdapter;

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

            roomDTOList = new ArrayList<>();
            offerDTOS = new ArrayList<>();

            apiService = ApiClient.getClient().create(ApiInterface.class);
            storeDTOS = new ArrayList<>();
            autoScrollList = new ArrayList<>();
            initLayout();
//            loadData();


        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loadData() {
        try {
            apiService.getPopularStores(Constants.accessToken).enqueue(new Callback<List<StoreDTO>>() {
                @Override
                public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                    Constants.debugLog(TAG, "" + response.body());
                    if (response.isSuccessful() & response.body() != null) {
                        storeDTOS.addAll(response.body());
                        nearbyPlaceAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<StoreDTO>> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Constants.debugLog(TAG, "Error in load data " + e.getMessage());
        }
    }

    private void initLayout() {
        Typeface face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_regular.otf");

        toolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });

        for (int i = 0; i < 10; i++) {
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setStoreName("adpaokfp");
            storeDTO.setStoreId(i);
            storeDTOS.add(storeDTO);
        }

        nearby_rv = view.findViewById(R.id.nearby_rv);
        nearby_rv.setNestedScrollingEnabled(false);
        nearby_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        nearbyPlaceAdapter = new NearbyPlaceAdapter(mActivity, storeDTOS);
        nearby_rv.setAdapter(nearbyPlaceAdapter);

        for (int i = 0; i < 10; i++) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(i);
            roomDTO.setName("name");
            roomDTOList.add(roomDTO);
        }
        recommended_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayout.HORIZONTAL, false));
        recommended_rv.setNestedScrollingEnabled(false);
        recommendedAdapter = new RecommendedAdapter(mActivity, roomDTOList);
        recommended_rv.setAdapter(recommendedAdapter);

        for (int i = 0; i < 4; i++) {
            OfferDTO roomDTO = new OfferDTO();
            roomDTO.setId(i);
            offerDTOS.add(roomDTO);
        }
        offer_rv.setLayoutManager(new GridLayoutManager(mActivity,2));
        offer_rv.setNestedScrollingEnabled(false);
        offerAdapter = new OfferAdapter(mActivity, offerDTOS);
        offer_rv.setAdapter(offerAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_all_popular:
                startActivity(new Intent(v.getContext(), StoresActivity.class));
                break;
            case R.id.search_layout:
                startActivity(new Intent(v.getContext(), SearchActivity.class));
                break;
        }

    }

    public static final int BARCODE_READER_REQUEST_CODE = 101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barCode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d(TAG, "" + barCode.displayValue);
                    getStoreByCode(barCode.displayValue);
                } else {
                    Toast.makeText(mActivity, R.string.no_barcode_captured, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getStoreByCode(String QRCode) {
        try {
            apiService.getStoreByCode(QRCode, Constants.accessToken).enqueue(new Callback<StoreDTO>() {
                @Override
                public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                    if (response != null && response.body() != null) {
                        Constants.debugLog(TAG, response.body().toString());
                        StoresDetailsActivity.start(mActivity, response.body());
                    }
                }

                @Override
                public void onFailure(Call<StoreDTO> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
