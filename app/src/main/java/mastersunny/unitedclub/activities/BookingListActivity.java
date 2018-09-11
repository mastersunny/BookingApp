package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.adapters.BookingAdapter;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingListActivity extends AppCompatActivity {

    private static String TAG = "BookingListActivity";
    private ApiInterface apiInterface;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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

    @BindView(R.id.no_data_found)
    TextView no_data_found;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    BookingAdapter bookingAdapter;

    private List<RoomBookingDTO> roomBookingDTOS;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude, longitude;

    public static void start(Context context) {
        Intent intent = new Intent(context, BookingListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        roomBookingDTOS = new ArrayList<>();
        initLayout();
    }

    private void initLayout() {
        toolbar_title.setText("Booking List");

        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        bookingAdapter = new BookingAdapter(this, roomBookingDTOS);
        recycler_view.setAdapter(bookingAdapter);
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
                break;
            case R.id.start_date_layout:
                break;
            case R.id.end_date_layout:
                break;
            case R.id.room_person_layout:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void notifyBookingAdapter() {
        if (bookingAdapter != null) {
            bookingAdapter.notifyDataSetChanged();
        }
        checkNoData();
    }

    private void checkNoData() {
        progressBar.setVisibility(View.GONE);
        if (roomBookingDTOS.size() == 0) {
            no_data_found.setVisibility(View.VISIBLE);
        } else {
            no_data_found.setVisibility(View.GONE);
        }
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getBookings(0, 100, "createdAt,desc").enqueue(new Callback<List<RoomBookingDTO>>() {
            @Override
            public void onResponse(Call<List<RoomBookingDTO>> call, Response<List<RoomBookingDTO>> response) {

                Constants.debugLog(TAG, response + "");

                if (response.isSuccessful()) {
                    Constants.debugLog(TAG, response.body() + "");
                    roomBookingDTOS.clear();
                    roomBookingDTOS.addAll(response.body());
                    notifyBookingAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<RoomBookingDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
                checkNoData();
            }
        });
    }
}
