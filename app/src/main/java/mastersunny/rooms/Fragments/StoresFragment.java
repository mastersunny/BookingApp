package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mastersunny.rooms.adapters.PagerAdapter;
import mastersunny.rooms.R;
import mastersunny.rooms.utils.Constants;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class StoresFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TextView toolbar_title, total_popular_item, total_item;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.stores_fragment_layout, container, false);
            initLayout();
            setUpTabLayout(savedInstanceState);
        }

        return view;
    }

    private void setUpTabLayout(Bundle savedInstanceState) {
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        if (savedInstanceState == null) {
            pagerAdapter.addFragment(new BookingFragment(), "All");
        } else {
            Integer count = savedInstanceState.getInt("tabsCount");
            String[] titles = savedInstanceState.getStringArray("titles");
            for (int i = 0; i < count; i++) {
                pagerAdapter.addFragment(getFragment(i, savedInstanceState), titles[i]);
            }
        }

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
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
    }

    private Fragment getFragment(int position, Bundle savedInstanceState) {
        return savedInstanceState == null ? pagerAdapter.getItem(position) : getChildFragmentManager().findFragmentByTag(getFragmentTag(position));
    }

    private String getFragmentTag(int position) {
        String tag = "android:switcher:" + R.id.viewPager + ":" + position;
        return tag;
    }


    private void initLayout() {
        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Stores");
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        total_popular_item = view.findViewById(R.id.total_popular_item);
        total_popular_item.setTypeface(Constants.getRegularFace(mActivity));
        total_item = view.findViewById(R.id.total_item);
        total_item.setTypeface(Constants.getRegularFace(mActivity));

        view.findViewById(R.id.search_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
//                HotelListActivity.start(v.getContext(), Constants.store_search);
                break;
        }
    }
}
