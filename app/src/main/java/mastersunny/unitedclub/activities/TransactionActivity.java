package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import mastersunny.unitedclub.adapters.PagerAdapter;
import mastersunny.unitedclub.Fragments.DueFragment;
import mastersunny.unitedclub.Fragments.PaidFragment;
import mastersunny.unitedclub.models.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private TextView total_popular_item, total_item;

    public static void start(Context context, UserDTO userDTO) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra(Constants.USER_DTO, userDTO);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.transaction_activity_layout);

        initLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpTabLayout(savedInstanceState);

    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new PaidFragment(), "Paid");
            pagerAdapter.addFragment(new DueFragment(), "Due");
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
                TransactionActivity.this.finish();
                break;
            case R.id.search_button:
                startActivity(new Intent(TransactionActivity.this, SearchActivity.class));
                break;
        }
    }
}
