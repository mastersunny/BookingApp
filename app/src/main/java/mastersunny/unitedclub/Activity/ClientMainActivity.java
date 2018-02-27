package mastersunny.unitedclub.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Fragments.HomeFragment;
import mastersunny.unitedclub.Fragments.ProfileFragment;
import mastersunny.unitedclub.Fragments.StoresFragment;
import mastersunny.unitedclub.Model.AccessModel;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.NotificationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientMainActivity extends AppCompatActivity {

    public String TAG = ClientMainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences preferences;
    static final String NEW_USER = "new_user";
    private boolean isNewUser;
    private ApiInterface apiInterface;

    public static void start(Context context, boolean isNewUser) {
        Intent intent = new Intent(context, ClientMainActivity.class);
        intent.putExtra(NEW_USER, isNewUser);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cleint_main_activity);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        preferences = getSharedPreferences(Constants.prefs, MODE_PRIVATE);
        Constants.accessToken = preferences.getString(Constants.ACCESS_TOKEN, "");
        setUpTabLayout(savedInstanceState);
        setUpNavigationView();
        initBroadcastReceiver();
        isNewUser = getIntent().getBooleanExtra(NEW_USER, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.debugLog(TAG, "" + isNewUser + " " + Constants.accessToken + " token " + FirebaseInstanceId.getInstance().getToken());
        if (isNewUser) {
            isNewUser = false;
            apiInterface.sendRegistrationToServer(Constants.accessToken, FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<AccessModel>() {
                @Override
                public void onResponse(Call<AccessModel> call, Response<AccessModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Constants.debugLog(TAG, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AccessModel> call, Throwable t) {

                }
            });
        }
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void initBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    Constants.debugLog(TAG, "Push message " + message);

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
                }
            }
        };
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.prefs, 0);
        String regId = pref.getString("regId", null);

        Constants.debugLog(TAG, "Firebase reg id: " + regId);
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
