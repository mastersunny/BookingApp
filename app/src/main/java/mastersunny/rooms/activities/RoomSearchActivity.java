package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.GuestSelectFragment;
import mastersunny.rooms.Fragments.RoomSearchFragment1;
import mastersunny.rooms.Fragments.RoomSearchFragment2;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.RecentSearchAdapter;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.utils.Constants;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class RoomSearchActivity extends AppCompatActivity implements GuestSelectFragment.GuestSelectListener, RoomSearchListener {


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

    private double latitude, longitude;

    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_search);

        ButterKnife.bind(this);

        switchFragmentA();

        initLayout();
    }

    private void switchFragmentA() {
        if (fragmentManager.findFragmentByTag(RoomSearchFragment1.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(RoomSearchFragment1.FRAGMENT_TAG)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.content, new RoomSearchFragment1(), RoomSearchFragment1.FRAGMENT_TAG).commit();
        }
    }

    private void switchFragmentB() {
        if (fragmentManager.findFragmentByTag(RoomSearchFragment2.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(RoomSearchFragment2.FRAGMENT_TAG)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.content, new RoomSearchFragment2(), RoomSearchFragment2.FRAGMENT_TAG).commit();
        }
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search for Hotel, City, Or Location");
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

    @Override
    public void onRecentSearch(RoomDTO roomDTO) {

    }

    @Override
    public void onPlaceSearch(PlaceDTO placeDTO) {
        Log.d(TAG, placeDTO.toString());
        switchFragmentB();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        switchFragmentA();
    }
}
