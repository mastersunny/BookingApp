package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.RoomAdapter;
import mastersunny.rooms.models.ExamDTO;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomListActivity";

    @BindView(R.id.rv_rooms)
    RecyclerView rv_rooms;

    RoomAdapter roomAdapter;

    private List<RoomDTO> roomDTOS;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        roomDTOS = new ArrayList<>();
        getIntentData();
        initLayout();


//        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//            mFusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                getCityName(location);
//                            }
//                        }
//                    });
//
//        }

    }

    private void getIntentData() {

    }

    private void initLayout() {
        rv_rooms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roomAdapter = new RoomAdapter(this, roomDTOS);
        rv_rooms.setAdapter(roomAdapter);

//        try {
//            Date currentDate = Constants.sdf2.parse(examDTO.getExamDate());
//            Pair<String, String> pair = Constants.getStartEndDate(currentDate);
//            start_date.setText(pair.first);
//            end_date.setText(pair.second);
//        } catch (Exception e) {
//            Constants.debugLog(TAG, e.getMessage());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void notifyPlaceAdapter() {
        if (roomAdapter != null) {
            roomAdapter.notifyDataSetChanged();
        }
        checkNoData();
    }

    private void checkNoData() {
        
    }

    private void loadData() {

    }

}
