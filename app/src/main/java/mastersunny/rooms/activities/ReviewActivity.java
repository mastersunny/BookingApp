package mastersunny.rooms.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.ReviewAdapter;
import mastersunny.rooms.models.RoomDTO;

public class ReviewActivity extends AppCompatActivity {

    @BindView(R.id.rv_review)
    RecyclerView rv_review;

    private List<RoomDTO> roomDTOS = new ArrayList<>();
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);
        initLayout();
    }

    private void initLayout() {
        roomDTOS = new ArrayList<>();
        rv_review.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_review.setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));
        reviewAdapter = new ReviewAdapter(this, roomDTOS);
        rv_review.setAdapter(reviewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (roomDTOS.size() <= 0) {
            loadData();
        }
    }

    private void loadData() {
        RoomDTO roomDTO1 = new RoomDTO("THE WAY DHAKA", 23.7968, 90.4115, 12484);
        RoomDTO roomDTO2 = new RoomDTO("Four Points By Sheraton DHaka, Gulshan", 23.7944, 90.4137, 15436);
        RoomDTO roomDTO3 = new RoomDTO("Century Residence Park", 23.7856724, 90.4186784, 6748);
        RoomDTO roomDTO4 = new RoomDTO("Asia Hotel & Resorts", 23.7306626, 90.4067831, 5061);

        roomDTOS.add(roomDTO1);
        roomDTOS.add(roomDTO2);
        roomDTOS.add(roomDTO3);
        roomDTOS.add(roomDTO4);
    }

    @OnClick({R.id.img_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
