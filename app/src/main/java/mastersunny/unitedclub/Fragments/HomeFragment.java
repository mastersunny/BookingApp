package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.activities.StoresActivity;
import mastersunny.unitedclub.activities.SearchActivity;
import mastersunny.unitedclub.activities.StoresDetailsActivity;
import mastersunny.unitedclub.Adapter.AutoScrollAdapter;
import mastersunny.unitedclub.Adapter.CategoryPagerAdapter;
import mastersunny.unitedclub.Adapter.PopularAdapter;
import mastersunny.unitedclub.Model.CategoryDTO;
import mastersunny.unitedclub.Model.SliderDTO;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private CategoryPagerAdapter categoryPagerAdapter;
    private PopularAdapter popularAdapter;
    private RecyclerView popular_rv;
    private ArrayList<StoreDTO> storeDTOS;
    private TextView view_all_popular, popular_stores, search_text;
    private AppBarLayout appBarLayout;
    private LoopingViewPager loopingViewPager;
    private AutoScrollAdapter autoScrollAdapter;
    private ArrayList<SliderDTO> autoScrollList;
    private ApiInterface apiService;
    private ArrayList<CategoryDTO> categoryDTOS;

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
            apiService = ApiClient.getClient().create(ApiInterface.class);
            storeDTOS = new ArrayList<>();
            autoScrollList = new ArrayList<>();
            categoryDTOS = new ArrayList<>();
//            for (int i = 0; i < 4; i++) {
//                CategoryDTO categoryDTO = new CategoryDTO();
//                categoryDTO.setCategoryName("Fashion");
//                categoryDTOS.add(categoryDTO);
//            }
            initLayout();
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//            loadData();

        }

        return view;
    }

    private void loadData() {
        try {
            Constants.debugLog(TAG, Constants.accessToken);
            apiService.getSliders(Constants.accessToken).enqueue(new Callback<List<SliderDTO>>() {
                @Override
                public void onResponse(Call<List<SliderDTO>> call, Response<List<SliderDTO>> response) {
                    Constants.debugLog(TAG, "" + response.body());
                    if (response.isSuccessful() && response.body() != null) {
                        autoScrollList.addAll(response.body());
                        createAutoScrollViewPager();
                    }
                }

                @Override
                public void onFailure(Call<List<SliderDTO>> call, Throwable t) {

                }
            });

            apiService.getPopularStores(Constants.accessToken).enqueue(new Callback<List<StoreDTO>>() {
                @Override
                public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                    Constants.debugLog(TAG, "" + response.body());
                    if (response.isSuccessful() & response.body() != null) {
                        storeDTOS.addAll(response.body());
                        popularAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<StoreDTO>> call, Throwable t) {

                }
            });

            apiService.getCategories(Constants.accessToken).enqueue(new Callback<List<CategoryDTO>>() {
                @Override
                public void onResponse(Call<List<CategoryDTO>> call, Response<List<CategoryDTO>> response) {
                    Constants.debugLog(TAG, "getCategories " + response);
                    if (response != null && response.isSuccessful()) {
                        Constants.debugLog(TAG, "getCategories " + response.body());
                        categoryDTOS.addAll(response.body());
                        setUpTabLayout();
                        if (categoryPagerAdapter != null) {
                            categoryPagerAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryDTO>> call, Throwable t) {
                    Constants.debugLog(TAG, "Error in load data" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, "Error in load data " + e.getMessage());
        }
    }

    private void createAutoScrollViewPager() {
        autoScrollAdapter = new AutoScrollAdapter(mActivity, autoScrollList, true);
        loopingViewPager.setAdapter(autoScrollAdapter);
    }

    private void setUpTabLayout() {
        categoryPagerAdapter = new CategoryPagerAdapter(getChildFragmentManager());
        categoryPagerAdapter.addItems(categoryDTOS);
        viewPager.setAdapter(categoryPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                tabLayout.getTabAt(position).setText(categoryDTOS.get(position).getCategoryName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initLayout() {
        Typeface face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_regular.otf");

        toolbar = view.findViewById(R.id.toolbar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        popular_stores = view.findViewById(R.id.popular_stores);
        view_all_popular = view.findViewById(R.id.view_all_popular);
        search_text = view.findViewById(R.id.search_text);
        loopingViewPager = view.findViewById(R.id.autoViewPager);

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

        for (int i = 0; i < 10; i++) {
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setStoreName("adpaokfp");
            storeDTO.setStoreId(i);
            storeDTOS.add(storeDTO);
        }

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
                    getStoreByCode(barCode.displayValue);
                } else {
                    Toast.makeText(mActivity, R.string.no_barcode_captured, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getStoreByCode(String QRCode) {
        try {
            apiService.getStoreByCode(QRCode, Constants.accessToken).enqueue(new Callback<StoreDTO>() {
                @Override
                public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                    if (response != null && response.body() != null) {
                        Constants.debugLog(TAG, response.body().toString());
                        StoresDetailsActivity.start(mActivity, response.body());
                    }
                }

                @Override
                public void onFailure(Call<StoreDTO> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (autoScrollAdapter != null && autoScrollAdapter.getCount() > 0) {
                    Log.d(TAG, "resumeAutoScroll");
                    loopingViewPager.resumeAutoScroll();
                }
            }
        });
    }

    @Override
    public void onPause() {
        loopingViewPager.pauseAutoScroll();
        super.onPause();
    }
}
