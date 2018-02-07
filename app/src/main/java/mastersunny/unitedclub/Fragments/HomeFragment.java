package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Activity.StoresActivity;
import mastersunny.unitedclub.Activity.SearchActivity;
import mastersunny.unitedclub.Adapter.AutoScrollAdapter;
import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Adapter.PopularAdapter;
import mastersunny.unitedclub.Model.SliderDTO;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.barcode.BarcodeCaptureActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    public String TAG = "HomeFragment";
    private Activity mActivity;
    private View view;
    //    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private PopularAdapter popularAdapter;
    private RecyclerView popular_rv;
    private ArrayList<StoreDTO> storeDTOS;
    private TextView view_all_popular, popular_stores,
            search_text, coupon_finder_text;
    private AppBarLayout appBarLayout;
    private LoopingViewPager loopingViewPager;
    private LoopingPagerAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<SliderDTO> autoScrollList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_layout, container, false);
            storeDTOS = new ArrayList<>();
            autoScrollList = new ArrayList<>();
            initLayout();
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            setUpTabLayout(savedInstanceState);

            loadData();
        }

        return view;
    }

    private void loadData() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SliderDTO>> call = apiService.getSliders();
        call.enqueue(new Callback<List<SliderDTO>>() {
            @Override
            public void onResponse(Call<List<SliderDTO>> call, Response<List<SliderDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    autoScrollList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SliderDTO>> call, Throwable t) {

            }
        });
    }

    /*private void setUpNavigationView() {

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
                        startActivity(new Intent(mActivity, SignUpActivity.class));

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        if (user == null)
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signin));
        else
            navigationView.getMenu().getItem(10).setTitle(getResources().getString(R.string.nav_signout));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(mActivity, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


    }*/

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
            pagerAdapter.addFragment(new GroceriesFragment(), getResources().getString(R.string.groceries));
            pagerAdapter.addFragment(new ElectronicsFragment(), getResources().getString(R.string.electronics));
            pagerAdapter.addFragment(new FashionFragment(), getResources().getString(R.string.fashion));
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
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        loopingViewPager = view.findViewById(R.id.autoViewPager);
        adapter = new AutoScrollAdapter(mActivity, autoScrollList, true);
        loopingViewPager.setAdapter(adapter);
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getChildFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }

    private void initLayout() {
        Typeface face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_regular.otf");

        toolbar = view.findViewById(R.id.toolbar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        popular_stores = view.findViewById(R.id.popular_stores);
        view_all_popular = view.findViewById(R.id.view_all_popular);
        search_text = view.findViewById(R.id.search_text);

        progressBar = view.findViewById(R.id.progressBar);

        search_text.setTypeface(face);
        popular_stores.setTypeface(face);
        view_all_popular.setTypeface(face);

        view_all_popular.setOnClickListener(this);
        view.findViewById(R.id.search_layout).setOnClickListener(this);

        appBarLayout = view.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    loopingViewPager.resumeAutoScroll();
                }
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                    loopingViewPager.pauseAutoScroll();
                } else {
                    toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.transparent_100));
                }
            }
        });

        popular_rv = view.findViewById(R.id.popular_rv);
        popular_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new PopularAdapter(mActivity, storeDTOS);
        popular_rv.setAdapter(popularAdapter);

        view.findViewById(R.id.search_layout).setOnClickListener(this);
        view.findViewById(R.id.coupon_finder_layout).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_all_popular:
                startActivity(new Intent(v.getContext(), StoresActivity.class));
                break;
            case R.id.search_layout:
                startActivity(new Intent(v.getContext(), SearchActivity.class));
                break;
            case R.id.coupon_finder_layout:
                Intent intent = new Intent(mActivity, BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
                break;
        }

    }

    public static final int BARCODE_READER_REQUEST_CODE = 101;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barCode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d(TAG, "" + barCode.displayValue);
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setStoreName(barCode.displayValue);
                    ItemDetailsActivity.start(mActivity, storeDTO);
                } else {
                    Toast.makeText(mActivity, R.string.no_barcode_captured, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    storeDTOS.add(new StoreDTO());
                }


                adapter.notifyDataSetChanged();

                adapter.notifyDataSetChanged();
                loopingViewPager.resumeAutoScroll();
            }
        });
    }

    @Override
    public void onPause() {
        loopingViewPager.pauseAutoScroll();
        super.onPause();
    }
}
