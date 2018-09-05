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

    @BindView(R.id.cancel_booking_1)
    ImageView cancel_booking_1;

    @BindView(R.id.cancel_booking_2)
    TextView cancel_booking_2;


    public static void start(Context context, RoomBookingDTO roomBookingDTO) {
        Intent intent = new Intent(context, RoomBookingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_BOOKING_DTO, roomBookingDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
    }

    private void getIntentData() {
        roomBookingDTO = (RoomBookingDTO) getIntent().getSerializableExtra(Constants.ROOM_BOOKING_DTO);
    }

    @OnClick({R.id.btn_make_call, R.id.cancel_booking_1, R.id.cancel_booking_2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_call:
                makeCall(roomBookingDTO.getRoom().getUser().getPhoneNumber());
                break;
            case R.id.cancel_booking_1:
            case R.id.cancel_booking_2:
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
                    Toast.makeText(RoomBookingActivity.this, "deleted", Toast.LENGTH_SHORT).show();
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