package mastersunny.unitedclub.activities;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.adapters.ExamAdapter;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private String TAG = "SearchActivity";
    private SearchView searchView;
    private int searchType;
    private ApiInterface apiInterface;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.back_button)
    ImageView back_button;

    @BindView(R.id.exam_rv)
    RecyclerView exam_rv;

    ExamAdapter examAdapter;

    private ArrayList<ExamDTO> examDTOList;

    private FusedLocationProviderClient mFusedLocationClient;

    public static void start(Context context, ArrayList<ExamDTO> examDTOList, int searchType) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.EXAM_DTO_LIST, examDTOList);
        intent.putExtra(Constants.SEARCH_TYPE, searchType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        apiInterface = ApiClient.createService(this, ApiInterface.class);
        examDTOList = new ArrayList<>();
        getIntentData();
        initLayout();

        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                getCityName(location);
                            }
                        }
                    });

        }

    }

    private void getCityName(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String cityName = addresses.get(0).getLocality();
            toolbar_title.setText("Where in " + cityName + "?");
            Constants.debugLog(TAG, cityName);

        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    private void getIntentData() {
        searchType = getIntent().getIntExtra(Constants.SEARCH_TYPE, 0);
        examDTOList = (ArrayList<ExamDTO>) getIntent().getSerializableExtra(Constants.EXAM_DTO_LIST);
    }

    private void initLayout() {
        exam_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        examAdapter = new ExamAdapter(this, examDTOList);
        exam_rv.setAdapter(examAdapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
        }

        searchView.setQueryHint("where to stay?");
//        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }*/

    @OnClick
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
        }
    }
}
