package mastersunny.rooms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import mastersunny.rooms.Fragments.GuestSelectFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.RoomAdapter;
import mastersunny.rooms.gmap.GooglePlaceDetails;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.models.RoomImageDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class RoomListActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GuestSelectFragment.GuestSelectListener {

    private static String TAG = "RoomListActivity";

    @BindView(R.id.rv_rooms)
    RecyclerView rv_rooms;

    @BindView(R.id.room_list_layout)
    FrameLayout room_list_layout;

    @BindView(R.id.room_item_layout)
    RelativeLayout room_item_layout;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.img_back)
    ImageView img_back;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_start_date)
    TextView tv_start_date;

    @BindView(R.id.tv_end_date)
    TextView tv_end_date;

    @BindView(R.id.tv_room_qty)
    TextView tv_room_qty;

    @BindView(R.id.tv_adult_qty)
    TextView tv_adult_qty;

    @BindView(R.id.map_layout)
    LinearLayout map_layout;

    @BindView(R.id.list_layout)
    LinearLayout list_layout;

    @BindView(R.id.room_image)
    ImageView room_image;

    private RoomDTO roomDTO;

    private RoomAdapter roomAdapter;

    private List<RoomDTO> roomDTOS;

    private Map<Marker, RoomDTO> roomDTOMap = new HashMap<>();

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;
    private ApiInterface apiInterface;

    IconGenerator iconFactory;

    @BindView(R.id.mapView)
    MapView mMapView;

    private GoogleMap mMap;
    private String placeId = "";

    SimpleDateFormat sdf = new SimpleDateFormat("MMM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        mMapView.onCreate(savedInstanceState);
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
        Intent intent = getIntent();
        if (intent.hasExtra("PLACE_ID")) {
            placeId = intent.getStringExtra("PLACE_ID");
        }

    }

    private void initLayout() {
        roomDTOS = new ArrayList<>();
        rv_rooms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_rooms.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(5f)));
        roomAdapter = new RoomAdapter(this);
        rv_rooms.setAdapter(roomAdapter);

        mMapView.getMapAsync(this);

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(Constants.startDate);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(Constants.endDate);

        showFormattedDate(startDate, endDate);
    }


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

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        loadMapData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        if (roomDTOS.size() <= 0) {
            loadData();
        }
        if (!TextUtils.isEmpty(placeId)) {
            loadPlaceDetails();
        }
    }

    private void loadPlaceDetails() {
        apiInterface.getPlaceDetailsFromGoogle(placeId, "geometry(location)",
                getResources().getString(R.string.GOOGLE_PLACE_API_KEY)).enqueue(new Callback<GooglePlaceDetails>() {
            @Override
            public void onResponse(Call<GooglePlaceDetails> call, Response<GooglePlaceDetails> response) {
                if (response.isSuccessful()) {
                    GooglePlaceDetails googlePlaceDetails = response.body();
                    Constants.debugLog(TAG, googlePlaceDetails.toString());
                    if (googlePlaceDetails.getStatus().equalsIgnoreCase("OK")) {
                        latitude = googlePlaceDetails.getResult().getGeometry().getLocation().getLat();
                        longitude = googlePlaceDetails.getResult().getGeometry().getLocation().getLng();

                        Constants.debugLog(TAG, "latitude " + latitude + " longitude " + longitude);
                    }

                }
            }

            @Override
            public void onFailure(Call<GooglePlaceDetails> call, Throwable t) {
                Toast.makeText(RoomListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void notifyPlaceAdapter() {
        if (roomAdapter != null) {
            roomAdapter.notifyDataSetChanged();
        }
//        checkNoData();
    }


    private void checkNoData() {

    }

    private void loadData() {

        RoomDTO roomDTO1 = new RoomDTO("THE WAY DHAKA", 23.7968, 90.4115, 12484);
        RoomDTO roomDTO2 = new RoomDTO("Four Points By Sheraton DHaka, Gulshan", 23.7944, 90.4137, 15436);
        RoomDTO roomDTO3 = new RoomDTO("Century Residence Park", 23.7856724, 90.4186784, 6748);
        RoomDTO roomDTO4 = new RoomDTO("Asia Hotel & Resorts", 23.7306626, 90.4067831, 5061);

        roomDTO1.getImages().add(new RoomImageDTO("room1"));
        roomDTO2.getImages().add(new RoomImageDTO("room2"));
        roomDTO3.getImages().add(new RoomImageDTO("room3"));
        roomDTO4.getImages().add(new RoomImageDTO("room4"));

        roomDTOS.add(roomDTO1);
        roomDTOS.add(roomDTO2);
        roomDTOS.add(roomDTO3);
        roomDTOS.add(roomDTO4);

        for (int i = 0; i < roomDTOS.size(); i++) {
            roomAdapter.add(roomDTOS.get(i), i);
        }
    }

    @OnClick({R.id.map_layout, R.id.img_back, R.id.start_date_layout,
            R.id.end_date_layout, R.id.room_guest_layout, R.id.list_layout,
            R.id.room_item_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_layout:
                list_layout.setVisibility(View.VISIBLE);
                room_list_layout.setVisibility(View.GONE);
                mMapView.setVisibility(View.VISIBLE);
                map_layout.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.list_layout:
                onBackPressed();
                break;
            case R.id.start_date_layout:
            case R.id.end_date_layout:
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setCallback(callback)
                        .setStartDate(Constants.startDate)
                        .setEndDate(Constants.addDays(Constants.startDate, 1).getTime())
                        .show(getSupportFragmentManager(), "tag_slycalendar");
                break;
            case R.id.room_guest_layout:
                GuestSelectFragment guestSelectFragment = new GuestSelectFragment();
                guestSelectFragment.show(getSupportFragmentManager(), "guestSelectFragment");
                break;
            case R.id.room_item_layout:
                RoomDetailsActivity.start(v.getContext(), roomDTO);
                break;
        }
    }

    SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
            showFormattedDate(firstDate, secondDate);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void loadMapData() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(roomDTOS.get(0).getLatitude(), roomDTOS.get(0).getLongitude()), 10));
        for (int i = 0; i < roomDTOS.size(); i++) {
            RoomDTO roomDTO = roomDTOS.get(i);
            iconFactory = new IconGenerator(this);
            iconFactory.setTextAppearance(R.style.mapText);
            iconFactory.setContentPadding(0, 0, 0, 0);
            addIcon(iconFactory, "BDT " + roomDTO.getRoomCost(), new LatLng(roomDTO.getLatitude(), roomDTO.getLongitude()), roomDTO);
        }
    }

    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position, RoomDTO roomDTO) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
        mMap.setOnMarkerClickListener(this);
        Marker marker = mMap.addMarker(markerOptions);

        roomDTOMap.put(marker, roomDTO);
    }

    private CharSequence makeCharSequence() {
        String prefix = "Mixing ";
        String suffix = "different fonts";
        String sequence = prefix + suffix;
        SpannableStringBuilder ssb = new SpannableStringBuilder(sequence);
        ssb.setSpan(new StyleSpan(ITALIC), 0, prefix.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(BOLD), prefix.length(), sequence.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, roomDTOMap.get(marker).toString() + "");
        roomDTO = new RoomDTO();
        if (roomDTOMap.containsKey(marker)) {
            roomDTO = roomDTOMap.get(marker);
        }
        iconFactory.setStyle(IconGenerator.STYLE_RED);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("BDT " + roomDTO.getRoomCost())));

        for (Map.Entry<Marker, RoomDTO> entry : roomDTOMap.entrySet()) {
            if (!entry.getKey().equals(marker)) {
                iconFactory = new IconGenerator(this);
                iconFactory.setTextAppearance(R.style.mapText);
                iconFactory.setContentPadding(0, 0, 0, 0);
                entry.getKey().setIcon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("BDT " + roomDTO.getRoomCost())));
            }
        }
        room_item_layout.setVisibility(View.VISIBLE);
        tv_name.setText(roomDTO.getAddress());
        int res = getResources().getIdentifier(getPackageName()
                + ":drawable/" + roomDTO.getImages().get(0).getImageUrl(), null, null);
        Glide.with(this).load(res).into(room_image);

        return false;
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

    @Override
    public void onBackPressed() {
        if (mMapView.getVisibility() == View.VISIBLE) {
            list_layout.setVisibility(View.INVISIBLE);
            mMapView.setVisibility(View.GONE);
            map_layout.setVisibility(View.VISIBLE);
            room_list_layout.setVisibility(View.VISIBLE);
            room_item_layout.setVisibility(View.GONE);
        } else {
            finish();
        }
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
