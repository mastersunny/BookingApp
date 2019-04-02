package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.GuestSelectFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.CityAdapter;
import mastersunny.rooms.adapters.PlaceAdapter;
import mastersunny.rooms.adapters.RecentSearchAdapter;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.PlaceRoomItem;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.utils.Constants;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class RoomSearchActivity extends AppCompatActivity implements GuestSelectFragment.GuestSelectListener {


    public String TAG = "RoomSearchActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.tv_start_date)
    TextView tv_start_date;

    @BindView(R.id.tv_end_date)
    TextView tv_end_date;

    @BindView(R.id.tv_room_qty)
    TextView tv_room_qty;

    @BindView(R.id.tv_adult_qty)
    TextView tv_adult_qty;

    @BindView(R.id.rv_places)
    RecyclerView rv_places;
    private List<PlaceRoomItem> placeRoomItems = new ArrayList<>();
    RecentSearchAdapter recentSearchAdapter;

    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_search);

        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search for Hotel, City, Or Location");

        rv_places.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recentSearchAdapter = new RecentSearchAdapter(this, placeRoomItems);
        rv_places.setAdapter(recentSearchAdapter);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (placeRoomItems.size() <= 0) {
            loadData();
        }
    }

    private void loadData() {
        placeRoomItems.add(new RoomDTO());
        placeRoomItems.add(new RoomDTO());
        placeRoomItems.add(new RoomDTO());

        placeRoomItems.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        placeRoomItems.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        loadRecentSearch();
    }

    private void loadRecentSearch() {
        if (recentSearchAdapter != null) {
            recentSearchAdapter.notifyDataSetChanged();
        }
    }

    private void searchPlace() {
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            startPlaceAutoComplete();
        } else {
        }
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
                Place place = PlaceAutocomplete.getPlace(RoomSearchActivity.this, data);

//                toolbar_title.setText(place.getName());
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;

                Constants.debugLog(TAG, "lat " + latitude + " lon " + longitude);

                startRoomListActivity();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(RoomSearchActivity.this, data);
                Constants.debugLog(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Constants.debugLog(TAG, "RESULT_CANCELED");
            }
        }
    }

    int REQUEST_CODE = 1;

    @OnClick({R.id.img_search, R.id.img_back, R.id.start_date_layout,
            R.id.end_date_layout, R.id.room_guest_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                searchPlace();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.start_date_layout:
            case R.id.end_date_layout:
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(callback)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
//                AirCalendarIntent intent = new AirCalendarIntent(this);
//                intent.isBooking(false);
//                intent.isSelect(false);
////                intent.setBookingDateArray('Array Dates(format: yyyy-MM-dd');
//                intent.setStartDate(2019 , 03 , 29); // int
//                intent.setEndDate(2019 , 03 , 30); // int
//                intent.isMonthLabels(false);
//                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.room_guest_layout:
                GuestSelectFragment guestSelectFragment = new GuestSelectFragment();
                guestSelectFragment.show(getSupportFragmentManager(), "guestSelectFragment");
                break;
        }
    }

    SimpleDateFormat f = new SimpleDateFormat("MMM");

    SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
            String firstMonth = f.format(firstDate.getTime());
            int firstDay = firstDate.get(Calendar.DAY_OF_MONTH);

            tv_start_date.setText(firstMonth + " " + firstDay);


            String secondMonth = f.format(secondDate.getTime());
            int secondDay = secondDate.get(Calendar.DAY_OF_MONTH);

            tv_end_date.setText(secondMonth + " " + secondDay);


        }
    };

    private void startRoomListActivity() {
        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent);
    }

    @Override
    public void selectedGuestAndRoom(int roomQty, int adultQty, int childQty) {
        tv_room_qty.setText(roomQty + " Rooms");
        if (childQty > 0) {
            tv_adult_qty.setText(adultQty + " Adults, " + (childQty > 1 ? (childQty + " Children") : (childQty + " Child")));
        } else {
            tv_adult_qty.setText(adultQty + " Adults");
        }

    }
}
