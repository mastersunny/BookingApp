package mastersunny.rooms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.HotelAdapter;
import mastersunny.rooms.adapters.RoomAdapter;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.HotelResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;

public class RoomListActivity extends AppCompatActivity {

    private static String TAG = "RoomListActivity";

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.rv_rooms)
    RecyclerView rv_rooms;

    private ApiInterface apiInterface;
    private HotelResponseDto hotelResponseDto;
    private List<RoomDTO> roomDTOS;
    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        getIntentData();
        initLayout();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.HOTEL_DTO)) {
            hotelResponseDto = (HotelResponseDto) intent.getSerializableExtra(Constants.HOTEL_DTO);
            roomDTOS = hotelResponseDto.getRoomDTOS();
        }
    }

    private void initLayout() {
        rv_rooms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_rooms.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));
        roomAdapter = new RoomAdapter(this);
        rv_rooms.setAdapter(roomAdapter);
        roomAdapter.setData(roomDTOS);
        roomAdapter.setItemSelectListener(new ItemSelectListener() {
            @Override
            public void onItemSelect(Object selectedItem, int action) {
                switch (action) {
                    case Constants.ACTION_DETAILS:
                        startRoomDetailsActivity((RoomDTO) selectedItem);
                        break;
                }
            }
        });
    }

    private void startRoomDetailsActivity(RoomDTO selectedItem) {
        Intent intent = new Intent(this, RoomDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.ROOM_DTO, selectedItem);
        startActivity(intent);
    }

    @OnClick({R.id.img_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }
}
