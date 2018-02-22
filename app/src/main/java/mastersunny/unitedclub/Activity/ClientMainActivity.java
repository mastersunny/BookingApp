package mastersunny.unitedclub.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Fragments.HomeFragment;
import mastersunny.unitedclub.Fragments.ProfileFragment;
import mastersunny.unitedclub.Fragments.StoresFragment;
import mastersunny.unitedclub.R;

public class ClientMainActivity extends AppCompatActivity {

    public String TAG = "ClientMainActivity";
    private BottomNavigationView bottomNavigationView;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cleint_main_activity);

        setUpTabLayout(savedInstanceState);
        setUpNavigationView();

       /* Display mDisplay = getWindowManager().getDefaultDisplay();
        final int width = mDisplay.getWidth();
        final int height = mDisplay.getHeight();

        Log.d(TAG, "" + width);
        Log.d(TAG, "" + height);*/
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new HomeFragment(), getResources().getString(R.string.nav_home));
            pagerAdapter.addFragment(new StoresFragment(), getResources().getString(R.string.stores));
            pagerAdapter.addFragment(new ProfileFragment(), getResources().getString(R.string.profile));
        } else {
            Integer count = savedInstanceState.getInt("tabsCount");
            String[] titles = savedInstanceState.getStringArray("titles");
            for (int i = 0; i < count; i++) {
                pagerAdapter.addFragment(getFragment(i, savedInstanceState), titles[i]);
            }
        }

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    private void setUpNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bottom_home:
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.nav_bottom_stores:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.nav_bottom_profile:
                        viewPager.setCurrentItem(2, true);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
