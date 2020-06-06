package mastersunny.rooms.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import mastersunny.rooms.adapters.PagerAdapter;
import mastersunny.rooms.Fragments.BookingFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.utils.Constants;

public class StoresActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private TextView total_popular_item, total_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.stores_activity_layout);

        initLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpTabLayout(savedInstanceState);
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new BookingFragment(), "All");
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


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    private void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        total_popular_item = findViewById(R.id.total_popular_item);
        total_popular_item.setTypeface(Constants.getRegularFace(this));
        total_item = findViewById(R.id.total_item);
        total_item.setTypeface(Constants.getRegularFace(this));


        findViewById(R.id.back_button).setOnClickListener(this);
        findViewById(R.id.search_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                StoresActivity.this.finish();
                break;
            case R.id.search_button:
                startActivity(new Intent(StoresActivity.this, HotelListActivity.class));
                break;
        }
    }
}
