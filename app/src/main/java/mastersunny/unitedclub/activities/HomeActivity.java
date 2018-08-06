package mastersunny.unitedclub.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.unitedclub.Fragments.AllStoreFragment;
import mastersunny.unitedclub.Fragments.HomeFragment;
import mastersunny.unitedclub.Fragments.PopularStoreFragment;
import mastersunny.unitedclub.Fragments.ProfileFragment;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.adapters.PagerAdapter;
import mastersunny.unitedclub.models.AccessModel;
import mastersunny.unitedclub.utils.Constants;
import mastersunny.unitedclub.utils.NotificationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public String TAG = HomeActivity.class.getSimpleName();
    private PagerAdapter pagerAdapter;
    private MenuItem prevMenuItem;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    static final String NEW_USER = "new_user";
    private ApiInterface apiInterface;
    private Handler mHandler;

    private Unbinder unbinder;

    @BindView(R.id.home_layout)
    LinearLayout home_layout;

    @BindView(R.id.saved_layout)
    LinearLayout saved_layout;

    @BindView(R.id.booking_layout)
    LinearLayout booking_layout;

    @BindView(R.id.profile_layout)
    LinearLayout profile_layout;

    private String HOME_FRAGMENT = "home_fragment";
    private String SAVED_FRAGMENT = "saved_fragment";
    private String BOOKING_FRAGMENT = "booking_fragment";
    private String PROFILE_FRAGMENT = "profile_fragment";
    private String CURRENT_FRAGMENT = HOME_FRAGMENT;

    Fragment fragment;


    public static void start(Context context, boolean isNewUser) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(NEW_USER, isNewUser);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_activity);
        unbinder = ButterKnife.bind(this);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        setUpNavigationView();
        initBroadcastReceiver();

        mHandler = new Handler();

        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            CURRENT_FRAGMENT = HOME_FRAGMENT;
            loadHomeFragment(fragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Constants.debugLog(TAG, "" + Constants.accessToken + " token " + FirebaseInstanceId.getInstance().getToken());
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
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
//                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {

                    String message = intent.getStringExtra(Constants.MESSAGE);
                    String transactionDate = intent.getStringExtra(Constants.TRANSACTION_DATE);
                    final int transactionId = intent.getIntExtra(Constants.TRANSACTION_ID, 0);
                    Constants.showDialog(HomeActivity.this, message);
                }
            }
        };
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.prefs, 0);
        String regId = pref.getString("regId", null);

        Constants.debugLog(TAG, "Firebase reg id: " + regId);
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    private void setUpNavigationView() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.home_layout, R.id.saved_layout, R.id.booking_layout, R.id.profile_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                fragment = new HomeFragment();
                CURRENT_FRAGMENT = HOME_FRAGMENT;
                loadHomeFragment(fragment);
                break;
            case R.id.saved_layout:
                fragment = new PopularStoreFragment();
                CURRENT_FRAGMENT = SAVED_FRAGMENT;
                loadHomeFragment(fragment);
                break;
            case R.id.booking_layout:
                fragment = new AllStoreFragment();
                CURRENT_FRAGMENT = BOOKING_FRAGMENT;
                loadHomeFragment(fragment);
                break;
            case R.id.profile_layout:
                fragment = new ProfileFragment();
                CURRENT_FRAGMENT = PROFILE_FRAGMENT;
                loadHomeFragment(fragment);
                break;
        }

    }

    private void loadHomeFragment(final Fragment fragment) {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT) != null) {
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content, fragment, CURRENT_FRAGMENT);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
}
