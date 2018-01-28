package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Activity.SignUpActivity;
import mastersunny.unitedclub.Activity.StoresActivity;
import mastersunny.unitedclub.Activity.SearchActivity;
import mastersunny.unitedclub.Adapter.AutoPagerAdapter;
import mastersunny.unitedclub.Adapter.PagerAdapter;
import mastersunny.unitedclub.Adapter.PopularAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.AutoScrollViewPager;
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
    private AutoPagerAdapter autoPagerAdapter;
    private AutoScrollViewPager autoScrollViewPager;
    private PopularAdapter popularAdapter;
    private RecyclerView popular_rv;
    private ArrayList<StoreDTO> storeDTOS;
    private TextView view_all_popular;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_fragment_layout, container, false);
            storeDTOS = new ArrayList<>();
            initLayout();
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//            setUpNavigationView();
            setUpTabLayout(savedInstanceState);

            loadData();

        }

        return view;
    }

    private void loadData() {
        /*ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StoreDTO>> call = apiService.getPopularStores();
        call.enqueue(new Callback<List<StoreDTO>>() {
            @Override
            public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                if (response.body() != null) {
                    for (StoreDTO storeDTO : response.body()) {
                        storeDTOS.add(storeDTO);
                    }
                    if (popularAdapter != null) {
                        popularAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                Log.d(TAG, " " + t.getMessage());

            }
        });*/
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
            pagerAdapter.addFragment(new RechargeFragment(), getResources().getString(R.string.recharge));
            pagerAdapter.addFragment(new TravelFragment(), getResources().getString(R.string.travel));
            pagerAdapter.addFragment(new FashionFragment(), getResources().getString(R.string.fashion));
            pagerAdapter.addFragment(new FoodFragment(), getResources().getString(R.string.food));
            pagerAdapter.addFragment(new ElectronicsFragment(), getResources().getString(R.string.electronics));
            pagerAdapter.addFragment(new GroceriesFragment(), getResources().getString(R.string.groceries));
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

        autoPagerAdapter = new AutoPagerAdapter(getChildFragmentManager());
        autoPagerAdapter.addFragment(new AutoScrollFragment(), "");
        autoPagerAdapter.addFragment(new AutoScrollFragment2(), "");
        autoPagerAdapter.addFragment(new AutoScrollFragment(), "");
        autoScrollViewPager.setAdapter(autoPagerAdapter);
        autoScrollViewPager.setOffscreenPageLimit(3);
        autoScrollViewPager.startAutoScroll();
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getChildFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }


    private void initLayout() {
        toolbar = view.findViewById(R.id.toolbar);
//        drawerLayout = view.findViewById(R.id.drawer_layout);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
//        navigationView = view.findViewById(R.id.nav_view);
        autoScrollViewPager = view.findViewById(R.id.autoViewPager);
        view_all_popular = view.findViewById(R.id.view_all_popular);
        view_all_popular.setOnClickListener(this);
        view.findViewById(R.id.search_layout).setOnClickListener(this);

        for (int i = 0; i < 10; i++) {
            storeDTOS.add(new StoreDTO());
        }

        popular_rv = view.findViewById(R.id.popular_rv);
        popular_rv.setNestedScrollingEnabled(false);
        popular_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        popularAdapter = new PopularAdapter(mActivity, storeDTOS);
        popular_rv.setAdapter(popularAdapter);

        view.findViewById(R.id.search_layout).setOnClickListener(this);

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
        }

    }
}
