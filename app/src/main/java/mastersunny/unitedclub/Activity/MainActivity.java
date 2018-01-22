package mastersunny.unitedclub.Activity;

import android.app.SearchManager;
import android.content.Context;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.AutoPagerAdapter;
import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Adapter.PopularAdapter;
import mastersunny.unitedclub.Fragments.AutoScrollFragment;
import mastersunny.unitedclub.Fragments.AutoScrollFragment2;
import mastersunny.unitedclub.Fragments.MostUsedFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuth.AuthStateListener {

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
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        list = new ArrayList<>();
        initLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        auth.addAuthStateListener(this);
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
                        if (user != null) {
                            auth.signOut();
                        } else {
                            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                        }
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        if (user == null)
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signin));
        else
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signout));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_searchview, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
////            searchView.setIconifiedByDefault(false);
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    return false;
//                }
//            });
//        }
        return false;
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.most_used));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.recharge));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.travel));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.fashion));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.food));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.electronics));
            pagerAdapter.addFragment(new MostUsedFragment(), getResources().getString(R.string.groceries));
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
        autoPagerAdapter.addFragment(new AutoScrollFragment2(), "");
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


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signout));
        else
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signin));
    }
}
