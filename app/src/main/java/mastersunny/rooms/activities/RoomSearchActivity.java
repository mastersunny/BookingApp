package mastersunny.rooms.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.GuestSelectFragment;
import mastersunny.rooms.Fragments.RoomSearchFragment1;
import mastersunny.rooms.Fragments.RoomSearchFragment2;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.MapAdapter;
import mastersunny.rooms.gmap.GooglePlaceDTO;
import mastersunny.rooms.gmap.Prediction;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.LocalityDTO;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    @BindView(R.id.place_rv)
    RecyclerView place_rv;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.room_search_header)
    LinearLayout room_search_header;

    private double latitude, longitude;
    private ApiInterface apiInterface;

    FragmentManager fragmentManager = getSupportFragmentManager();

    private MapAdapter mapAdapter;
    private List<Prediction> predictions = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_search);

        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);

        switchFragmentA();

        initLayout();
    }

    private void switchFragmentA() {
        shouldShowA = false;
        hideFragment(RoomSearchFragment2.FRAGMENT_TAG);
        if (fragmentManager.findFragmentByTag(RoomSearchFragment1.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(RoomSearchFragment1.FRAGMENT_TAG)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.content, new RoomSearchFragment1(), RoomSearchFragment1.FRAGMENT_TAG).commit();
        }
    }

    private void hideFragment(String fragmentTag) {
        if (fragmentManager.findFragmentByTag(fragmentTag) != null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(fragmentTag)).commit();
        }
    }

    private void switchFragmentB(PlaceDTO placeDTO) {
        shouldShowA = true;
        hideFragment(RoomSearchFragment1.FRAGMENT_TAG);
        if (fragmentManager.findFragmentByTag(RoomSearchFragment2.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(RoomSearchFragment2.FRAGMENT_TAG)).commit();
        } else {
            RoomSearchFragment2 fragment2 = new RoomSearchFragment2();
            fragmentManager.beginTransaction().add(R.id.content, fragment2, RoomSearchFragment2.FRAGMENT_TAG).commit();
        }
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.ic_cake);

        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.startDate);
        showFormattedDate(cal, Constants.addDays(Constants.startDate, 1));

        place_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mapAdapter = new MapAdapter(this, predictions);
        place_rv.setAdapter(mapAdapter);

        AutoCompleteTextView search_text = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_14));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_text));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return false;
                }
                searchPlace(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                place_rv.setVisibility(View.VISIBLE);
                room_search_header.setVisibility(View.GONE);
                return true;
            }
        });
        ImageView closeButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard(RoomSearchActivity.this);
                searchView.setQuery("", false);
                predictions.clear();
                mapAdapter.notifyDataSetChanged();
                place_rv.setVisibility(View.GONE);
                room_search_header.setVisibility(View.VISIBLE);
            }
        });

    }

    private void searchPlace(String query) {
        apiInterface.getPlaceFromGoogle(query, getResources().getString(R.string.GOOGLE_PLACE_API_KEY)).enqueue(new Callback<GooglePlaceDTO>() {
            @Override
            public void onResponse(Call<GooglePlaceDTO> call, Response<GooglePlaceDTO> response) {
                Constants.debugLog(TAG, response.body().getPredictions() + "");
                if (response.isSuccessful()) {
                    GooglePlaceDTO googlePlaceDTO = response.body();
                    Constants.debugLog(TAG, googlePlaceDTO.toString());
                    if (googlePlaceDTO.getStatus().equalsIgnoreCase("OK")) {
                        predictions.clear();
                        predictions.addAll(googlePlaceDTO.getPredictions());
                        mapAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<GooglePlaceDTO> call, Throwable t) {

            }
        });
    }

//    private void startPlaceAutoComplete() {
//        try {
//            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
//            startActivityForResult(intent, Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE);
//        } catch (GooglePlayServicesRepairableException e) {
//
//        } catch (GooglePlayServicesNotAvailableException e) {
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                Place place = PlaceAutocomplete.getPlace(RoomSearchActivity.this, data);
//
////                toolbar_title.setText(place.getName());
//                latitude = place.getLatLng().latitude;
//                longitude = place.getLatLng().longitude;
//
//                Constants.debugLog(TAG, "lat " + latitude + " lon " + longitude);
//
//                startRoomListActivity();
//
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                Status status = PlaceAutocomplete.getStatus(RoomSearchActivity.this, data);
//                Constants.debugLog(TAG, status.getStatusMessage());
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Constants.debugLog(TAG, "RESULT_CANCELED");
//            }
//        }
    }

    int REQUEST_CODE = 1;

    @OnClick({R.id.start_date_layout,
            R.id.end_date_layout,
            R.id.room_guest_layout,
            R.id.img_back,
    })
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.img_search:
//                searchPlace();
//                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.start_date_layout:
            case R.id.end_date_layout:
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(callback)
                        .setStartDate(Constants.startDate)
                        .setEndDate(Constants.endDate)
                        .show(getSupportFragmentManager(), "tag_slycalendar");
                break;
            case R.id.room_guest_layout:
                GuestSelectFragment guestSelectFragment = new GuestSelectFragment();
                guestSelectFragment.show(getSupportFragmentManager(), "guestSelectFragment");
                break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("MMM");

    SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
            showFormattedDate(firstDate, secondDate);
        }
    };

    private void showFormattedDate(Calendar startDate, Calendar endDate) {
        String firstMonth = sdf.format(startDate.getTime());
        int firstDay = startDate.get(Calendar.DAY_OF_MONTH);
        tv_start_date.setText(firstMonth + " " + firstDay);


        String secondMonth = sdf.format(endDate.getTime());
        int secondDay = endDate.get(Calendar.DAY_OF_MONTH);
        tv_end_date.setText(secondMonth + " " + secondDay);

        Constants.startDate = startDate.getTime();
        Constants.endDate = endDate.getTime();
    }

    private void startRoomListActivity() {
        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
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
        startRoomListActivity();
    }

    @Override
    public void onPlaceSearch(PlaceDTO placeDTO) {
        switchFragmentB(placeDTO);
    }

    @Override
    public void onLocalitySearch(LocalityDTO localityDTO) {
        startRoomListActivity();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean shouldShowA = false;

    @Override
    public void onBackPressed() {
        hideKeyboard(this);
        if (shouldShowA) {
            switchFragmentA();
        } else {
            finish();
        }
    }
}
