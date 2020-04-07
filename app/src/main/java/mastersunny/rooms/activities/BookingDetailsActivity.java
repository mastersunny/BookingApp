package mastersunny.rooms.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.ProgressDialogFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.models.RoomBookingDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity {

    private String TAG = "RoomDetailsActivity";

    private ApiInterface apiInterface;

    private static ProgressDialogFragment progressDialogFragment;

    RoomBookingDTO roomBookingDTO;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.address)
    TextView address;

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


    public static void start(Context context, RoomBookingDTO roomBookingDTO) {
        Intent intent = new Intent(context, BookingDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_BOOKING_DTO, roomBookingDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        roomBookingDTO = (RoomBookingDTO) getIntent().getSerializableExtra(Constants.ROOM_BOOKING_DTO);
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Room Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(roomBookingDTO.getNoOfAccommodation() > 1 ? roomBookingDTO.getNoOfAccommodation()
                + " Guests" : roomBookingDTO.getNoOfAccommodation() + " Guest");
//        address.setText(roomBookingDTO.getRoom().getAddress());

        if (roomBookingDTO.getRoom().getFemaleFriendly() == 1) {
            female_friendly_layout.setVisibility(View.VISIBLE);
        }

        room_details.setText(roomBookingDTO.getRoom().getDescription());

        if (roomBookingDTO.getRoom().getWifiAvailable() == 1
                || roomBookingDTO.getRoom().getTvAvailable() == 1
                || roomBookingDTO.getRoom().getAcAvailable() == 1) {
            advantage_layout.setVisibility(View.VISIBLE);
            if (roomBookingDTO.getRoom().getWifiAvailable() == 1) {
                wifi_layout.setVisibility(View.VISIBLE);
            }

            if (roomBookingDTO.getRoom().getTvAvailable() == 1) {
                tv_layout.setVisibility(View.VISIBLE);
            }

            if (roomBookingDTO.getRoom().getAcAvailable() == 1) {
                ac_layout.setVisibility(View.VISIBLE);
            }
        }

        if (roomBookingDTO.getRoom().getLunchAvailable() == 1) {
            lunch_layout.setVisibility(View.VISIBLE);
        }

        if (roomBookingDTO.getRoom().getTransportAvailable() == 1) {
            transport_layout.setVisibility(View.VISIBLE);
        }

        amount = roomBookingDTO.getRoomCost();
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

        if (roomBookingDTO.getRoom().getImages() != null && roomBookingDTO.getRoom().getImages().size() > 0) {
            Constants.loadImage(this, roomBookingDTO.getRoom().getImages().get(0).getImageUrl(),
                    room_image);
        }

        switch (roomBookingDTO.getNoOfAccommodation()) {
            case 1:
                deselectAll();
                guest_count1.setSelected(true);
                break;
            case 2:
                deselectAll();
                guest_count2.setSelected(true);
                break;
            case 3:
                deselectAll();
                guest_count3.setSelected(true);
                break;
        }
    }

    private void updateTotalCost() {
        amount = 0;
        if (lunch_checkbox.isChecked()) {
            amount += (roomBookingDTO.getRoom().getLunchCost() * guestCount);
        }
        if (transport_checkbox.isChecked()) {
            amount += (roomBookingDTO.getRoom().getTransportCost() * guestCount);
        }
        amount += (roomBookingDTO.getRoom().getRoomCost() * guestCount);

        total_cost.setText("BDT " + df2.format(amount));
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
                deleteBooking();
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
                Uri.parse("http://maps.google.com/maps?" + "daddr=" + roomBookingDTO.getRoom().getLatitude() + "," + roomBookingDTO.getRoom().getLongitude()));
        startActivity(intent);
    }


    protected synchronized void showProgressDialog(String message) {
        if (progressDialogFragment != null && progressDialogFragment.isVisible()) {
            progressDialogFragment.dismissAllowingStateLoss();
        }
        progressDialogFragment = null;
        progressDialogFragment = new ProgressDialogFragment(message);
        progressDialogFragment.show(getSupportFragmentManager(), ProgressDialogFragment.class.toString());
    }

    protected synchronized void dismissDialog() {
        progressDialogFragment.dismissAllowingStateLoss();
    }

    private void deleteBooking() {
        showProgressDialog("Canceling...");
        apiInterface.deleteBooking(roomBookingDTO.getId()).enqueue(new Callback<RoomBookingDTO>() {
            @Override
            public void onResponse(Call<RoomBookingDTO> call, Response<RoomBookingDTO> response) {
                Constants.debugLog(TAG, response + "");
                dismissDialog();

                if (response.isSuccessful()) {
                    Toast.makeText(BookingDetailsActivity.this, getResources().getString(R.string.booking_canceled), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RoomBookingDTO> call, Throwable t) {
                dismissDialog();
                Constants.debugLog(TAG, t.getMessage());
                Toast.makeText(BookingDetailsActivity.this, "Cannot be deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
