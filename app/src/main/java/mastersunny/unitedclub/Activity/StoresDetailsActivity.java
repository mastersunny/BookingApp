package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Adapter.StoreOfferAdapter;
import mastersunny.unitedclub.Fragments.MostUsedFragment;
import mastersunny.unitedclub.Fragments.PopularStoreFragment;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresDetailsActivity extends AppCompatActivity implements Callback<List<StoreOfferDTO>> {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private StoreDTO storeDTO;
    private TextView total_offer;
    private ImageView store_image;
    private TextView store_name;
    private StoreOfferAdapter storeOfferAdapter;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private RecyclerView offer_rv;

    public static void start(Context context, long storeId) {
        Intent intent = new Intent(context, ItemDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.STORE_DTO, storeId);
        context.startActivity(intent);
    }

    private void getIntentData() {
        storeDTO = (StoreDTO) getIntent().getSerializableExtra(Constants.STORE_DTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_details);

        initLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setUpTabLayout(savedInstanceState);

        if (storeDTO != null && storeDTO.getStoreId() > 0) {
            loadData();
        }
    }


    private void initLayout() {
        toolbar = findViewById(R.id.toolbar);
//        tabLayout = findViewById(R.id.tabLayout);
//        viewPager = findViewById(R.id.viewPager);

        total_offer = findViewById(R.id.total_offer);
        store_image = findViewById(R.id.store_image);
        store_name = findViewById(R.id.store_name);

        offer_rv = findViewById(R.id.most_used_rv);
        offer_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapter(this, storeOfferDTOS);
        offer_rv.setAdapter(storeOfferAdapter);
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new MostUsedFragment(), "All");
            pagerAdapter.addFragment(new MostUsedFragment(), "Coupons");
            pagerAdapter.addFragment(new MostUsedFragment(), "Offers");
        } else {
            Integer count = savedInstanceState.getInt("tabsCount");
            String[] titles = savedInstanceState.getStringArray("titles");
            for (int i = 0; i < count; i++) {
                pagerAdapter.addFragment(getFragment(i, savedInstanceState), titles[i]);
            }
        }

        viewPager.setAdapter(pagerAdapter);
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
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getSupportFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        apiService.getStoreOffers(storeDTO.getStoreId(), ApiClient.API_KEY).enqueue(this);
    }

    @Override
    public void onResponse(Call<List<StoreOfferDTO>> call, Response<List<StoreOfferDTO>> response) {


    }

    @Override
    public void onFailure(Call<List<StoreOfferDTO>> call, Throwable t) {

    }
}
