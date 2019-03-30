package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.RoomListFragment;
import mastersunny.rooms.Fragments.RoomMapFragment;
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

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

public class RoomListActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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

    RoomAdapter roomAdapter;

    private List<RoomDTO> roomDTOS;

    private Map<Marker, RoomDTO> roomDTOMap = new HashMap<>();

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;

    IconGenerator iconFactory;

    @BindView(R.id.mapView)
    MapView mMapView;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
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

    }

    private void initLayout() {
        roomDTOS = new ArrayList<>();
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

        mMapView.getMapAsync(this);
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
        roomDTOS.add(new RoomDTO("THE WAY DHAKA", 23.7968, 90.4115, 12484));
        roomDTOS.add(new RoomDTO("Four Points By Sheraton DHaka, Gulshan", 23.7944, 90.4137, 15436));
        roomDTOS.add(new RoomDTO("Century Residence Park", 23.7856724, 90.4186784, 6748));
        roomDTOS.add(new RoomDTO("Asia Hotel & Resorts", 23.7306626, 90.4067831, 5061));

        notifyPlaceAdapter();
    }

    @OnClick({R.id.img_map})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_map:
                room_list_layout.setVisibility(View.GONE);
                mMapView.setVisibility(View.VISIBLE);
                break;
        }
    }

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
        RoomDTO roomDTO = new RoomDTO();
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

        return false;
    }
}
