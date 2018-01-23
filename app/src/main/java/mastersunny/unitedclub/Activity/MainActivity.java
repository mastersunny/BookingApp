package mastersunny.unitedclub.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import mastersunny.unitedclub.Fragments.HomeFragment;
import mastersunny.unitedclub.Fragments.MostUsedFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_STORIES = "stories";
    private static final String TAG_SEARCH = "search";
    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_PROFILE = "profile";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void loadHomeFragment() {

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            return;
        }
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
               /* // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;*/
            case 2:
                /*// movies fragment
                MoviesFragment moviesFragment = new MoviesFragment();
                return moviesFragment;*/
            case 3:
               /* // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;*/

            case 4:
                /*// settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;*/
            default:
                return new HomeFragment();
        }
    }

    private void setUpNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bottom_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_bottom_stores:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_STORIES;
                        break;
                    case R.id.nav_bottom_search:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SEARCH;
                        break;
                    case R.id.nav_bottom_categories:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CATEGORIES;
                        break;
                    case R.id.nav_bottom_profile:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    default:
                        navItemIndex = 0;
                }

                loadHomeFragment();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*// Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }*/

        return super.onOptionsItemSelected(item);
    }
}
