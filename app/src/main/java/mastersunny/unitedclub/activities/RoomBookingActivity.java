package mastersunny.unitedclub.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.Fragments.ProgressDialogFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.NotificationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomBookingActivity extends AppCompatActivity {


    private String TAG = "RoomBookingActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

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

    private static ProgressDialogFragment progressDialogFragment;


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


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    if (!isFinishing()) {
                        Constants.showNotificationDialog(RoomBookingActivity.this, message);
                    }
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
        showProgressDialog("Canceling...");
        apiInterface.deleteBooking(roomBookingDTO.getId()).enqueue(new Callback<RoomBookingDTO>() {
            @Override
            public void onResponse(Call<RoomBookingDTO> call, Response<RoomBookingDTO> response) {
                Constants.debugLog(TAG, response + "");
                dismissDialog();

                if (response.isSuccessful()) {
                    Toast.makeText(RoomBookingActivity.this, getResources().getString(R.string.booking_canceled), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RoomBookingDTO> call, Throwable t) {
                dismissDialog();
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
}
