package mastersunny.unitedclub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import Adapter.AutoPagerAdapter;
import Adapter.PagerAdapter;
import Fragments.AutoScrollFragment;
import Fragments.MostUsedFragment;
import utils.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private AutoPagerAdapter autoPagerAdapter;
    private AutoScrollViewPager autoScrollViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        setSupportActionBar(toolbar);
        setUpNavigationView();
        setUpTabLayout(savedInstanceState);
    }

    private void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.nav_view);
        autoScrollViewPager = findViewById(R.id.autoViewPager);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_grab:
                        break;
                    case R.id.nav_share:
                        break;
                    case R.id.nav_settings:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.most_used));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.recharge));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.travel));
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


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        autoPagerAdapter = new AutoPagerAdapter(getSupportFragmentManager());
        autoPagerAdapter.addFragment(new AutoScrollFragment(), "");
        autoPagerAdapter.addFragment(new AutoScrollFragment(), "");
        autoPagerAdapter.addFragment(new AutoScrollFragment(), "");
        autoScrollViewPager.setAdapter(autoPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        autoScrollViewPager.startAutoScroll();
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }


        super.onBackPressed();
    }
}
