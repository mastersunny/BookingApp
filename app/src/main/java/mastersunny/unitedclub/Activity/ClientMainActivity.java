package mastersunny.unitedclub.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import mastersunny.unitedclub.Fragments.CategoriesFragment;
import mastersunny.unitedclub.Fragments.HomeFragment;
import mastersunny.unitedclub.Fragments.PaidFragment;
import mastersunny.unitedclub.Fragments.ProfileFragment;
import mastersunny.unitedclub.Fragments.StoresFragment;
import mastersunny.unitedclub.R;

public class ClientMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_STORES = "stores";
    private static final String TAG_PROFILE = "profile";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    public String TAG = "ClientMainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cleint_main_activity);

        mHandler = new Handler();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setUpNavigationView();

        Display mDisplay = getWindowManager().getDefaultDisplay();
        final int width = mDisplay.getWidth();
        final int height = mDisplay.getHeight();

        Log.d(TAG, "" + width);
        Log.d(TAG, "" + height);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private Runnable mPendingRunnable = new Runnable() {
        @Override
        public void run() {
            Fragment fragment = getHomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();
        }
    };

    private void loadHomeFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            return;
        }
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                StoresFragment storiesFragment = new StoresFragment();
                return storiesFragment;
            case 2:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
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
                        CURRENT_TAG = TAG_STORES;
                        break;
                    case R.id.nav_bottom_profile:
                        navItemIndex = 2;
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
}
