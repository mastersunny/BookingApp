package mastersunny.unitedclub.activities;

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
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.adapters.RoomAdapter;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomListActivity";
    private SearchView searchView;
    private int searchType;
    private ApiInterface apiInterface;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.back_button)
    LinearLayout back_button;

    @BindView(R.id.start_date_layout)
    LinearLayout start_date_layout;

    @BindView(R.id.end_date_layout)
    LinearLayout end_date_layout;

    @BindView(R.id.room_person_layout)
    LinearLayout room_person_layout;

    @BindView(R.id.startDate)
    TextView start_date;

    @BindView(R.id.endDate)
    TextView end_date;

    @BindView(R.id.room_count)
    TextView room_count;

    @BindView(R.id.person_count)
    TextView person_count;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    RoomAdapter roomAdapter;

    private ExamDTO examDTO;

    private List<RoomDTO> roomDTOS;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;

    public static void start(Context context, ExamDTO examDTO) {
        Intent intent = new Intent(context, RoomListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXAM_DTO, examDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
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

    private void getCityName(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String cityName = addresses.get(0).getLocality();
            toolbar_title.setText("Where in " + cityName + "?");
            Constants.debugLog(TAG, cityName);

        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    private void getIntentData() {
        examDTO = (ExamDTO) getIntent().getSerializableExtra(Constants.EXAM_DTO);
    }

    private void initLayout() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roomAdapter = new RoomAdapter(this, roomDTOS);
        recycler_view.setAdapter(roomAdapter);
//        examAdapter.setListener(new ExamSelectionListener() {
//            @Override
//            public void selectedExam(ExamDTO examDTO) {
//                try {
//                    Date currentDate = Constants.sdf2.parse(examDTO.getDate());
//                    Pair<String, String> pair = Constants.getStartEndDate(currentDate);
//                    start_date.setText(pair.first);
//                    end_date.setText(pair.second);
//                } catch (Exception e) {
//                    Constants.debugLog(TAG, e.getMessage());
//                }
//
//
//            }
//        });

        toolbar_title.setText("Rooms in " + examDTO.getUniversity().getPlace().getName());
        room_count.setText("1 Room");
        person_count.setText("1 Adult");
        try {
            Date currentDate = Constants.sdf2.parse(examDTO.getDate());
            Pair<String, String> pair = Constants.getStartEndDate(currentDate);
            start_date.setText(pair.first);
            end_date.setText(pair.second);
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    @OnClick({R.id.back_button, R.id.search_icon, R.id.toolbar_title, R.id.start_date_layout,
            R.id.end_date_layout, R.id.room_person_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.search_icon:
            case R.id.toolbar_title:
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(RoomListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    startPlaceAutoComplete();
                } else {
//                    requestPermission(mActivity);
                }
                break;
            case R.id.start_date_layout:
                startDateRoomSelectActivity(0);
                break;
            case R.id.end_date_layout:
                startDateRoomSelectActivity(1);
                break;
            case R.id.room_person_layout:
                startDateRoomSelectActivity(2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void startDateRoomSelectActivity(int position) {
        Intent intent = new Intent(this, DateRoomSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.SELECTED_POSITION, position);
        startActivityForResult(intent, Constants.ROOM_DATE_REQUEST_CODE);
    }

    private void startPlaceAutoComplete() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(RoomListActivity.this, data);

                toolbar_title.setText(place.getName());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

                Constants.debugLog(TAG, "lat " + latitude + " lon " + longitude);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(RoomListActivity.this, data);
                Constants.debugLog(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Constants.debugLog(TAG, "RESULT_CANCELED");
            }
        }
        if (requestCode == Constants.ROOM_DATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                start_date.setText(Constants.calculateDate(Constants.startDate));
                end_date.setText(Constants.calculateDate(Constants.endDate));
            }
        }
    }

    private void notifyPlaceAdapter() {
        if (roomAdapter != null) {
            roomAdapter.notifyDataSetChanged();
        }
    }

    private void loadData() {
        apiInterface.getRooms(Constants.startDate, Constants.endDate, examDTO.getUniversity().getPlace().getLatitude(),
                examDTO.getUniversity().getPlace().getLongitude(), 2).enqueue(new Callback<List<RoomDTO>>() {
            @Override
            public void onResponse(Call<List<RoomDTO>> call, Response<List<RoomDTO>> response) {

                Constants.debugLog(TAG, response + "");

                if (response.isSuccessful()) {
                    Constants.debugLog(TAG, response.body() + "");
                    roomDTOS.clear();
                    roomDTOS.addAll(response.body());
                    notifyPlaceAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<RoomDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });
    }

}
