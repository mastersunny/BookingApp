package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailsActivity extends AppCompatActivity {

    private String TAG = "RoomDetailsActivity";

    private ApiInterface apiInterface;

    RoomDTO roomDTO;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.female_friendly_layout)
    TextView female_friendly_layout;

    @BindView(R.id.wifi_layout)
    LinearLayout wifi_layout;

    @BindView(R.id.tv_layout)
    LinearLayout tv_layout;

    @BindView(R.id.ac_layout)
    LinearLayout ac_layout;

    @BindView(R.id.advantage_layout)
    LinearLayout advantage_layout;

    @BindView(R.id.lunch_layout)
    RelativeLayout lunch_layout;

    @BindView(R.id.transport_layout)
    RelativeLayout transport_layout;

    @BindView(R.id.total_cost)
    TextView total_cost;

    @BindView(R.id.room_details)
    TextView room_details;

    @BindView(R.id.lunch_checkbox)
    CheckBox lunch_checkbox;

    @BindView(R.id.transport_checkbox)
    CheckBox transport_checkbox;

    @BindView(R.id.room_image)
    ImageView room_image;

    @BindView(R.id.guest_count1)
    Button guest_count1;

    @BindView(R.id.guest_count2)
    Button guest_count2;

    @BindView(R.id.guest_count3)
    Button guest_count3;

    @BindView(R.id.btn_book_room)
    Button btn_book_room;

    @BindView(R.id.map_layout)
    ImageView map_layout;

    double amount, guestCount = 1;

    private static DecimalFormat df2 = new DecimalFormat(".##");


    public static void start(Context context, RoomDTO roomDTO) {
        Intent intent = new Intent(context, RoomDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_DTO, roomDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        roomDTO = (RoomDTO) getIntent().getSerializableExtra(Constants.ROOM_DTO);
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Room Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (roomDTO.isFemaleFriendly()) {
            female_friendly_layout.setVisibility(View.VISIBLE);
        }

        room_details.setText(roomDTO.getDetails());

        if (roomDTO.isWifiAvailable() || roomDTO.isTvAvailable() || roomDTO.isAcAvailable()) {
            advantage_layout.setVisibility(View.VISIBLE);
            if (roomDTO.isWifiAvailable()) {
                wifi_layout.setVisibility(View.VISIBLE);
            }

            if (roomDTO.isTvAvailable()) {
                tv_layout.setVisibility(View.VISIBLE);
            }

            if (roomDTO.isAcAvailable()) {
                ac_layout.setVisibility(View.VISIBLE);
            }
        }

        if (roomDTO.isLunchAvailable()) {
            lunch_layout.setVisibility(View.VISIBLE);
        }

        if (roomDTO.isTransportAvailable()) {
            transport_layout.setVisibility(View.VISIBLE);
        }

        amount = roomDTO.getRoomCost();
        guest_count1.setSelected(true);
        updateTotalCost();

        lunch_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalCost();
            }
        });

        transport_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotalCost();
            }
        });

        Constants.loadImage(this, ApiClient.BASE_URL + ApiClient.APP_NAME + roomDTO.getImages().get(0).getImageUrl(),
                room_image);
    }

    private void updateTotalCost() {
        amount = 0;
        if (lunch_checkbox.isChecked()) {
            amount += (roomDTO.getLunchCost() * guestCount);
        }
        if (transport_checkbox.isChecked()) {
            amount += (roomDTO.getTransportCost() * guestCount);
        }
        amount += (roomDTO.getRoomCost() * guestCount);

        total_cost.setText(df2.format(amount));
    }

    @OnClick({R.id.guest_count1, R.id.guest_count2, R.id.guest_count3, R.id.btn_book_room, R.id.map_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_layout:
                startGoogleMap();
                break;
            case R.id.guest_count1:
                deselectAll();
                guest_count1.setSelected(true);
                guestCount = 1;
                updateTotalCost();
                break;
            case R.id.guest_count2:
                deselectAll();
                guest_count2.setSelected(true);
                guestCount = 2;
                updateTotalCost();
                break;
            case R.id.guest_count3:
                deselectAll();
                guest_count3.setSelected(true);
                guestCount = 3;
                updateTotalCost();
                break;
            case R.id.btn_book_room:
                bookRoom();
                break;
        }
    }

    private void deselectAll() {
        guest_count1.setSelected(false);
        guest_count2.setSelected(false);
        guest_count3.setSelected(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void startGoogleMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?" + "daddr=" + roomDTO.getLatitude() + "," + roomDTO.getLongitude()));
        startActivity(intent);
    }

    private void bookRoom() {
        apiInterface.bookRoom(Constants.startDate, Constants.endDate, roomDTO.getId(), amount, (int) guestCount).enqueue(new Callback<RoomBookingDTO>() {
            @Override
            public void onResponse(Call<RoomBookingDTO> call, Response<RoomBookingDTO> response) {

                Constants.debugLog(TAG, response + " ");

                if (response.isSuccessful() && response.body() != null) {
                    Constants.debugLog(TAG, response.body().toString());
                    BookingConfirmationActivity.start(RoomDetailsActivity.this, response.body());
                }
            }

            @Override
            public void onFailure(Call<RoomBookingDTO> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
                Toast.makeText(RoomDetailsActivity.this, "Cannot make booking", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
