package mastersunny.rooms.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.rooms.R;
import mastersunny.rooms.adapters.PlaceAdapter;
import mastersunny.rooms.adapters.PopularAdapter;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.utils.Constants;

public class HomeActivity extends AppCompatActivity {

    public String TAG = "HomeActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_cities)
    RecyclerView rv_cities;

    @BindView(R.id.rv_popular)
    RecyclerView rv_popular;

    @BindView(R.id.search_layout)
    RelativeLayout search_layout;

    PlaceAdapter placeAdapter;
    private List<PlaceDTO> placeDTOS = new ArrayList<>();

    PopularAdapter popularAdapter;
    private List<PlaceDTO> popularPlaces = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        ButterKnife.bind(this);

        initLayout();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        return super.onCreateOptionsMenu(menu);
//    }


    private void initLayout() {
        rv_cities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_cities.setHasFixedSize(true);
        rv_cities.setNestedScrollingEnabled(false);
        placeAdapter = new PlaceAdapter(this, placeDTOS);
        rv_cities.setAdapter(placeAdapter);

        rv_popular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_popular.setHasFixedSize(true);
        rv_popular.setNestedScrollingEnabled(false);
        popularAdapter = new PopularAdapter(this, popularPlaces);
        rv_popular.setAdapter(popularAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (placeDTOS.size() <= 0) {
            loadData();
        }

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

    private void loadData() {
        placeDTOS.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        placeDTOS.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        placeDTOS.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        placeDTOS.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        placeDTOS.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        placeDTOS.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyPlaceAdapter();

        popularPlaces.add(new PlaceDTO("Dhaka", "ঢাকা", "dhaka"));
        popularPlaces.add(new PlaceDTO("Sylhet", "সিলেট", "dhaka"));
        popularPlaces.add(new PlaceDTO("Rajshahi", "রাজশাহী", "dhaka"));
        popularPlaces.add(new PlaceDTO("Bogura", "বগুড়া", "dhaka"));
        popularPlaces.add(new PlaceDTO("Khulna", "খুলনা", "dhaka"));
        popularPlaces.add(new PlaceDTO("Chottogram", "চট্টগ্রাম", "dhaka"));
        popularPlaces.add(new PlaceDTO("Barishal", "বরিশাল", "dhaka"));

        notifyPopularAdapter();

    }

    private void notifyPlaceAdapter() {
        if (placeAdapter != null) {
            placeAdapter.notifyDataSetChanged();
        }
    }

    private void notifyPopularAdapter() {
        if (popularAdapter != null) {
            popularAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.search_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout:
                Intent intent = new Intent(HomeActivity.this, RoomSearchActivity.class);
                startActivity(intent);
        }
    }

}
