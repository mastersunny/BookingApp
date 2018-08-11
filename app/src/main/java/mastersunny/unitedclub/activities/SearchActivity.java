package mastersunny.unitedclub.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.Fragments.SelectDateFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.adapters.ExamAdapter;
import mastersunny.unitedclub.listeners.DateSelectionListener;
import mastersunny.unitedclub.listeners.ExamSelectionListener;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.models.PlaceDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private static String TAG = "SearchActivity";
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

    @BindView(R.id.exam_rv)
    RecyclerView exam_rv;

    ExamAdapter examAdapter;

    private String placeName;

    private ArrayList<ExamDTO> examDTOS;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;

    public static void start(Context context, String placeName, ArrayList<ExamDTO> examDTOS, int searchType) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.PLACE_NAME, placeName);
        intent.putExtra(Constants.EXAM_DTO_LIST, examDTOS);
        intent.putExtra(Constants.SEARCH_TYPE, searchType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
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
        searchType = getIntent().getIntExtra(Constants.SEARCH_TYPE, 0);
        placeName = getIntent().getStringExtra(Constants.PLACE_NAME);
        examDTOS = (ArrayList<ExamDTO>) getIntent().getSerializableExtra(Constants.EXAM_DTO_LIST);
    }

    private void initLayout() {
        exam_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        examAdapter = new ExamAdapter(this, examDTOS);
        exam_rv.setAdapter(examAdapter);
        examAdapter.setListener(new ExamSelectionListener() {
            @Override
            public void selectedExam(ExamDTO examDTO) {
                String[] dates = examDTO.getExamDate().split("-");
                start_date.setText(Constants.populateSetDate(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]), Integer.valueOf(dates[2])));
                end_date.setText(Constants.populateSetDate(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]), Integer.valueOf(dates[2] + 1)));
            }
        });

        toolbar_title.setText("Where in " + placeName + "?");

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        start_date.setText(Constants.populateSetDate(year, month, day));
        end_date.setText(Constants.populateSetDate(year, month, day + 1));
        room_count.setText("1 Room");
        person_count.setText("1 Adult");
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
                if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    startPlaceAutoComplete();
                } else {
//                    requestPermission(mActivity);
                }
                break;
            case R.id.start_date_layout:
                DateRoomSelectActivity.start(v.getContext(), 0);
                break;
            case R.id.end_date_layout:
                DateRoomSelectActivity.start(v.getContext(), 1);
                break;
            case R.id.room_person_layout:
                DateRoomSelectActivity.start(v.getContext(), 2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                Place place = PlaceAutocomplete.getPlace(SearchActivity.this, data);

                toolbar_title.setText(place.getName());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

                Constants.debugLog(TAG, "lat " + latitude + " lon " + longitude);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(SearchActivity.this, data);
                Constants.debugLog(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Constants.debugLog(TAG, "RESULT_CANCELED");
            }
        }
    }

}
