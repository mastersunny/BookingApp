package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.Fragments.HomeFragment;
import mastersunny.rooms.Fragments.ProfileFragment;
import mastersunny.rooms.Fragments.SavedFragment;
import mastersunny.rooms.R;
import mastersunny.rooms.utils.Constants;

public class HomeActivity extends AppCompatActivity {

    public String TAG = "HomeActivity";

    @BindView(R.id.nav_bottom_home)
    LinearLayout nav_bottom_home;

    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ButterKnife.bind(this);
        initLayout();
    }


    private void initLayout() {
        loadFragment(new HomeFragment(), HomeFragment.FRAGMENT_TAG);
    }

    private void loadFragment(Fragment fragment, String fragmentTAG) {
        Log.d(TAG, fragmentTAG);
        if (fragmentManager.findFragmentByTag(fragmentTAG) != null) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(fragmentTAG)).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.content, fragment, fragmentTAG).commit();
        }

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content, fragment);
//        transaction.addToBackStack(TAG);
//        transaction.commit();
    }

    private void hideAllFragments() {
        if (fragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_TAG)).commit();
        }
        if (fragmentManager.findFragmentByTag(SavedFragment.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(SavedFragment.FRAGMENT_TAG)).commit();
        }
        if (fragmentManager.findFragmentByTag(ProfileFragment.FRAGMENT_TAG) != null) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(ProfileFragment.FRAGMENT_TAG)).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    SearchActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
        } else {
            requestPermission(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.permission_location))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    Constants.PERMISSION_LOCATION);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSION_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_LOCATION) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                SearchActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
            }
        }
    }

    @OnClick({R.id.nav_bottom_home, R.id.nav_bottom_profile, R.id.nav_bottom_saved})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_bottom_home:
                hideAllFragments();
                loadFragment(new HomeFragment(), HomeFragment.FRAGMENT_TAG);
                break;
            case R.id.nav_bottom_profile:
                hideAllFragments();
                loadFragment(new ProfileFragment(), ProfileFragment.FRAGMENT_TAG);
                break;
            case R.id.nav_bottom_saved:
                hideAllFragments();
                loadFragment(new SavedFragment(), SavedFragment.FRAGMENT_TAG);
                break;
        }
    }

}
