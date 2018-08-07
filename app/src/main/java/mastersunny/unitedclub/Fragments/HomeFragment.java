package mastersunny.unitedclub.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.unitedclub.adapters.PlaceAdapter;
import mastersunny.unitedclub.listeners.ClickListener;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.PlaceDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.activities.SearchActivity;
import mastersunny.unitedclub.activities.StoresActivity;
import mastersunny.unitedclub.adapters.OfferAdapter;
import mastersunny.unitedclub.adapters.RecommendedAdapter;
import mastersunny.unitedclub.models.OfferDTO;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.SearchType;
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
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ApiInterface apiInterface;
    private Unbinder unbinder;

    @BindView(R.id.recommended_rv)
    RecyclerView recommended_rv;
    private List<RoomDTO> roomDTOList;
    private RecommendedAdapter recommendedAdapter;

    @BindView(R.id.offer_rv)
    RecyclerView offer_rv;
    private List<OfferDTO> offerDTOS;
    private OfferAdapter offerAdapter;

    @BindView(R.id.nearby_rv)
    RecyclerView nearby_rv;
    private List<PlaceDTO> placeDTOS;
    private PlaceAdapter placeAdapter;

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

            roomDTOList = new ArrayList<>();
            offerDTOS = new ArrayList<>();
            placeDTOS = new ArrayList<>();

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
        Typeface face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_regular.otf");

        toolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });

        nearby_rv.setNestedScrollingEnabled(false);
        nearby_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        placeAdapter = new PlaceAdapter(mActivity, placeDTOS);
        nearby_rv.setAdapter(placeAdapter);
        placeAdapter.setClickListener(new ClickListener() {
            @Override
            public void click() {
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    SearchActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
                } else {
                    requestPermission(mActivity);
                }
            }
        });

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
        offer_rv.setLayoutManager(new GridLayoutManager(mActivity, 2));
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

    private void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.permission_location))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    Constants.REQUEST_LOCATION);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.REQUEST_LOCATION) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SearchActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
            }
        }
    }

    private void loadData() {
        apiInterface.getPlaces(0, 10, "id,desc").enqueue(new Callback<List<PlaceDTO>>() {
            @Override
            public void onResponse(Call<List<PlaceDTO>> call, Response<List<PlaceDTO>> response) {

                Constants.debugLog(TAG, response + "");

                if (response.isSuccessful()) {
                    placeDTOS.clear();
                    placeDTOS.addAll(response.body());
                    notifyPlaceAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<PlaceDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });
    }

    private void notifyPlaceAdapter() {
        if (placeAdapter != null) {
            placeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
