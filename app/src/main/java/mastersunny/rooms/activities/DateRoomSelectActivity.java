package mastersunny.rooms.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.CheckinFragment;
import mastersunny.rooms.Fragments.CheckoutFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.PagerAdapter;
import mastersunny.rooms.listeners.DateSelectionListener;
import mastersunny.rooms.utils.Constants;

public class DateRoomSelectActivity extends AppCompatActivity implements DateSelectionListener {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.back_button)
    ImageView back_button;

    PagerAdapter pagerAdapter;

    private int selectedPosition;

    @BindView(R.id.btn_change_room_date)
    Button btn_change_room_date;

    String[] toolbarTitles = {
            "Select Check-In Date",
            "Select Check-Out Date",
            "Select Rooms & Guests"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_room_select);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getIntentData();
        setUpTabLayout(savedInstanceState);
    }

    private void getIntentData() {
//        selectedPosition = getIntent().getIntExtra(Constants.SELECTED_POSITION, 0);
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new CheckinFragment(), "Start Date");
            pagerAdapter.addFragment(new CheckoutFragment(), "End Date");
//            pagerAdapter.addFragment(new GuestSelectFragment(), "Room");
        } else {
            Integer count = savedInstanceState.getInt("tabsCount");
            String[] titles = savedInstanceState.getStringArray("titles");
            for (int i = 0; i < count; i++) {
                pagerAdapter.addFragment(getFragment(i, savedInstanceState), titles[i]);
            }
        }

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbar_title.setText(toolbarTitles[position]);
                selectedPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(2);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(selectedPosition);
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    @OnClick({R.id.back_button, R.id.btn_change_room_date})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
            case R.id.btn_change_room_date:
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
                break;

        }
    }

    @Override
    public void startDate(String date) {
        tabLayout.getTabAt(selectedPosition).setText("Start Date\n" + date);
    }

    @Override
    public void endDate(String date) {
        tabLayout.getTabAt(selectedPosition).setText("End Date\n" + date);
    }

    @Override
    public void totalGuest(int guestCount) {
        Constants.totalGuest = guestCount;
    }

}
