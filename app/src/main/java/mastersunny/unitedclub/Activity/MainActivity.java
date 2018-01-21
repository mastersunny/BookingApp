package mastersunny.unitedclub.Activity;

import android.content.Intent;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.AutoPagerAdapter;
import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Adapter.PopularAdapter;
import mastersunny.unitedclub.Fragments.AutoScrollFragment;
import mastersunny.unitedclub.Fragments.MostUsedFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private AutoPagerAdapter autoPagerAdapter;
    private AutoScrollViewPager autoScrollViewPager;
    private PopularAdapter popularAdapter;
    private RecyclerView popular_rv;
    private ArrayList<String> list;
    private TextView view_all_popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
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
        view_all_popular = findViewById(R.id.view_all_popular);
        view_all_popular.setOnClickListener(this);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("ddfhduifhuids " + i);
        }

        popular_rv = findViewById(R.id.popular_rv);
        popular_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new PopularAdapter(this, list);
        popular_rv.setAdapter(popularAdapter);
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
                    case R.id.nav_sign:
                        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_all_popular:
                startActivity(new Intent(MainActivity.this, PopularActivity.class));
                break;
        }
    }
}
