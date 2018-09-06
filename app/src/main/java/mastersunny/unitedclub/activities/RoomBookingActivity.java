package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomBookingActivity extends AppCompatActivity {


    private String TAG = "RoomBookingActivity";

    private ApiInterface apiInterface;
    RoomBookingDTO roomBookingDTO;

    @BindView(R.id.btn_make_call)
    Button btn_make_call;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.cancel_booking)
    TextView cancel_booking;

    @BindView(R.id.congratulation_message)
    TextView congratulation_message;

    private boolean isPending;


    public static void start(Context context, RoomBookingDTO roomBookingDTO, boolean isPending) {
        Intent intent = new Intent(context, RoomBookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_BOOKING_DTO, roomBookingDTO);
        intent.putExtra(Constants.BOOKING_PENDING, isPending);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
        initLayout();
    }

    private void initLayout() {
        if (isPending) {
            congratulation_message.setText("Please confirm pending booking!");
        } else {
            congratulation_message.setText("Congratulations");
        }
    }

    private void getIntentData() {
        roomBookingDTO = (RoomBookingDTO) getIntent().getSerializableExtra(Constants.ROOM_BOOKING_DTO);
        isPending = getIntent().getBooleanExtra(Constants.BOOKING_PENDING, false);
    }

    @OnClick({R.id.btn_make_call, R.id.back_button, R.id.cancel_booking})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_call:
                makeCall(roomBookingDTO.getRoom().getUser().getPhoneNumber());
                break;
            case R.id.back_button:
                finish();
                break;
            case R.id.cancel_booking:
                deleteBooking();
                break;
        }
    }

    private void deleteBooking() {
        apiInterface.deleteBooking(roomBookingDTO.getId()).enqueue(new Callback<RoomBookingDTO>() {
            @Override
            public void onResponse(Call<RoomBookingDTO> call, Response<RoomBookingDTO> response) {
                Constants.debugLog(TAG, response + "");
                if (response.isSuccessful()) {
                    Toast.makeText(RoomBookingActivity.this, "Booking deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RoomBookingDTO> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
                Toast.makeText(RoomBookingActivity.this, "Cannot be deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
