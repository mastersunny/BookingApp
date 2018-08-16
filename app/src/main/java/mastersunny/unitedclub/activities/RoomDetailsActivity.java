package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.utils.Constants;

public class RoomDetailsActivity extends AppCompatActivity {

    private String TAG = "RoomDetailsActivity";

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

        total_cost.setText(roomDTO.getRoomCost() + "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
