package mastersunny.rooms.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.ReviewBottomSheetFragment;
import mastersunny.rooms.Fragments.ProgressDialogFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.ImageAdapter;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;

public class RoomDetailsActivity extends AppCompatActivity {

    private String TAG = "RoomDetailsActivity";

    private ApiInterface apiInterface;

    private static ProgressDialogFragment progressDialogFragment;

    RoomDTO roomDTO;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.room_images)
    RecyclerView room_images;

    ImageAdapter imageAdapter;

    @BindView(R.id.bottom_sheet)
    NestedScrollView bottom_sheet;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.btn_book_room)
    Button btn_book_room;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.address)
    TextView address;

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

    @BindView(R.id.guest_count1)
    Button guest_count1;

    @BindView(R.id.guest_count2)
    Button guest_count2;

    @BindView(R.id.guest_count3)
    Button guest_count3;

    @BindView(R.id.map_layout)
    ImageView map_layout;

    @BindView(R.id.tv_check_in_date)
    TextView tv_check_in_date;

    @BindView(R.id.tv_check_in_day)
    TextView tv_check_in_day;

    @BindView(R.id.tv_check_in_year)
    TextView tv_check_in_year;

    @BindView(R.id.tv_check_out_date)
    TextView tv_check_out_date;

    @BindView(R.id.tv_check_out_day)
    TextView tv_check_out_day;

    @BindView(R.id.tv_check_out_year)
    TextView tv_check_out_year;

    double amount, guestCount = 1;

    NumberFormat formatter = new DecimalFormat("BDT #0");


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
        updateLayout();
        updateTotalCost();
    }

    private void updateLayout() {
        title.setText(roomDTO.getTitle());
        room_details.setText(roomDTO.getDescription());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Constants.startDate);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

        tv_check_in_date.setText(dayOfMonth + "");
        tv_check_in_day.setText(dayOfWeek);
        tv_check_in_year.setText(month + " " + year);


        calendar.setTime(Constants.endDate);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);

        dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);

        tv_check_out_date.setText(dayOfMonth + "");
        tv_check_out_day.setText(dayOfWeek);
        tv_check_out_year.setText(month + " " + year);
    }

    private void getIntentData() {
        roomDTO = (RoomDTO) getIntent().getSerializableExtra(Constants.ROOM_DTO);
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        room_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        imageAdapter = new ImageAdapter(this, new ArrayList<DivisionResponseDto>());
        room_images.setAdapter(imageAdapter);

        if (roomDTO.getWifiAvailable() == 1
                || roomDTO.getTvAvailable() == 1
                || roomDTO.getAcAvailable() == 1) {
            advantage_layout.setVisibility(View.VISIBLE);
            if (roomDTO.getWifiAvailable() == 1) {
                wifi_layout.setVisibility(View.VISIBLE);
            } else {
                wifi_layout.setVisibility(View.GONE);
            }

            if (roomDTO.getTvAvailable() == 1) {
                tv_layout.setVisibility(View.VISIBLE);
            } else {
                tv_layout.setVisibility(View.GONE);
            }

            if (roomDTO.getAcAvailable() == 1) {
                ac_layout.setVisibility(View.VISIBLE);
            } else {
                ac_layout.setVisibility(View.GONE);
            }
        }

        if (roomDTO.getLunchAvailable() == 1) {
            lunch_layout.setVisibility(View.VISIBLE);
        } else {
            lunch_layout.setVisibility(View.GONE);
        }

        if (roomDTO.getTransportAvailable() == 1) {
            transport_layout.setVisibility(View.VISIBLE);
        } else {
            transport_layout.setVisibility(View.GONE);
        }

        amount = roomDTO.getRoomCost();
        guest_count1.setSelected(true);
        guestCount = 1;

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

//        switch (roomDTO.getNoOfAccommodation()) {
//            case 1:
//                guest_count2.setVisibility(View.GONE);
//                guest_count3.setVisibility(View.GONE);
//                break;
//            case 2:
//                guest_count3.setVisibility(View.GONE);
//                break;
//        }

        bottom_sheet.setSmoothScrollingEnabled(true);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Constants.debugLog(TAG, "newState " + newState);
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    fab.setVisibility(View.VISIBLE);
                    btn_book_room.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);
                    btn_book_room.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
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

        total_cost.setText(formatter.format(amount));
    }

//    @OnClick({R.id.guest_count1, R.id.guest_count2, R.id.guest_count3, R.id.btn_book_room, R.id.map_layout})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.map_layout:
//                startGoogleMap();
//                break;
//            case R.id.guest_count1:
//                deselectAll();
//                guest_count1.setSelected(true);
//                guestCount = 1;
//                updateTotalCost();
//                break;
//            case R.id.guest_count2:
//                deselectAll();
//                guest_count2.setSelected(true);
//                guestCount = 2;
//                updateTotalCost();
//                break;
//            case R.id.guest_count3:
//                deselectAll();
//                guest_count3.setSelected(true);
//                guestCount = 3;
//                updateTotalCost();
//                break;
//            case R.id.btn_book_room:
//                bookRoom();
//                break;
//        }
//    }

    @OnClick({R.id.rating_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rating_layout:
                ReviewBottomSheetFragment reviewBottomSheetFragment = new ReviewBottomSheetFragment();
                reviewBottomSheetFragment.show(getSupportFragmentManager(), reviewBottomSheetFragment.getTag());
                break;
        }
    }

    private void deselectAll() {
//        guest_count1.setSelected(false);
//        guest_count2.setSelected(false);
//        guest_count3.setSelected(false);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bookRoom() {
        showProgressDialog("Booking...");
//        apiInterface.createBooking(Constants.startDate, Constants.endDate, roomDTO.getId(), amount, (int) guestCount).enqueue(new Callback<RoomBookingDTO>() {
//            @Override
//            public void onResponse(Call<RoomBookingDTO> call, Response<RoomBookingDTO> response) {
//
//                Constants.debugLog(TAG, response + " ");
//                dismissDialog();
//
//                if (response.isSuccessful() && response.body() != null) {
//                    Constants.debugLog(TAG, response.body().toString());
//                    if (response.headers().get("status").equals("exists")) {
//                        RoomBookingActivity.start(RoomDetailsActivity.this, response.body(), true);
//                        RoomDetailsActivity.this.finish();
//                    } else {
//                        RoomBookingActivity.start(RoomDetailsActivity.this, response.body(), false);
//                        RoomDetailsActivity.this.finish();
//                    }
//                } else {
//                    if (!isFinishing()) {
//                        Constants.showNotificationDialog(RoomDetailsActivity.this, "Booking", response.message());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RoomBookingDTO> call, Throwable t) {
//                dismissDialog();
//                Constants.debugLog(TAG, t.getMessage());
//                Toast.makeText(RoomDetailsActivity.this, "Cannot make booking", Toast.LENGTH_SHORT).show();
//            }
//        });

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

//    @OnClick({R.id.img_back})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
//        }
//    }
}
