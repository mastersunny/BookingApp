package mastersunny.rooms.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.BuildConfig;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.adapters.SearchAdapter;
import mastersunny.rooms.listeners.GpsListener;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.DistrictResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.utils.Constants;


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
    public List<DivisionResponseDto> divisions;
    private Unbinder unbinder;
    private SearchAdapter searchAdapter;
    private RoomSearchListener roomSearchListener;

    public static final int REQUEST_LOCATION_ON = 1;

    LocationManager mLocationManager;

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
//        if (roomDTOS.size() <= 0) {
//            loadData();
//        }
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
        searchAdapter = new SearchAdapter(mActivity, roomDTOS, divisions);
        rv_places.setAdapter(searchAdapter);
        searchAdapter.setItemSelectListener(new RoomSearchListener() {
            @Override
            public void onRecentSearch(RoomDTO roomDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onRecentSearch(roomDTO);
                }
            }

            @Override
            public void onDistrictSearch(DivisionResponseDto placeDTO) {
                if (roomSearchListener != null) {
                    roomSearchListener.onDistrictSearch(placeDTO);
                }
            }

            @Override
            public void onSearch(String name, double latitude, double longitude) {

            }
        });
        searchAdapter.setGpsListener(new GpsListener() {
            @Override
            public void turnOnGps() {
                displayLocationSettingsRequest(mActivity);
            }
        });
    }

    private void getLocation() {
        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                    10.0f, mLocationListener);
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Constants.debugLog(TAG, location + "");
            if (location != null) {
                Constants.debugLog(TAG, location.getLatitude() + " "
                        + location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Constants.debugLog(TAG, "All location settings are satisfied.");
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Constants.debugLog(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(mActivity, REQUEST_LOCATION_ON);
                        } catch (IntentSender.SendIntentException e) {
                            Constants.debugLog(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Constants.debugLog(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
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
        notifyPlaceAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
