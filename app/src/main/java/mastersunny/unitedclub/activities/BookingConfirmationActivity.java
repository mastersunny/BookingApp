package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;

public class BookingConfirmationActivity extends AppCompatActivity {


    private ApiInterface apiInterface;
    RoomBookingDTO roomBookingDTO;

    @BindView(R.id.btn_make_call)
    Button btn_make_call;

    @BindView(R.id.cancel_booking)
    TextView cancel_booking;


    public static void start(Context context, RoomBookingDTO roomBookingDTO) {
        Intent intent = new Intent(context, BookingConfirmationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_BOOKING_DTO, roomBookingDTO);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
    }

    private void getIntentData() {
        roomBookingDTO = (RoomBookingDTO) getIntent().getSerializableExtra(Constants.ROOM_BOOKING_DTO);
    }

    @OnClick({R.id.btn_make_call, R.id.cancel_booking})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_call:
                makeCall(roomBookingDTO.getPk().getRoom().getUser().getPhoneNumber());
                break;
            case R.id.cancel_booking:
                break;
        }
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);

    }
}
