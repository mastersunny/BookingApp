package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Adapter.PopularVerticalAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class PopularStoreFragment extends FragmentBase implements View.OnClickListener {

    public String TAG = PopularStoreFragment.class.getName();
    private Activity mActivity;
    private ArrayList<StoreDTO> storeDTOS;
    private RecyclerView popular_rv;
    private PopularVerticalAdapter popularVerticalAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    private void loaData() {
        try {
            apiInterface.getPopularStores(Constants.getAccessToken(mActivity)).enqueue(new Callback<List<StoreDTO>>() {
                @Override
                public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                    Constants.debugLog(TAG, "" + response);
                    if (response != null && response.isSuccessful()) {
                        storeDTOS.addAll(response.body());
                        if (popularVerticalAdapter != null) {
                            popularVerticalAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<StoreDTO>> call, Throwable t) {
                    Constants.debugLog(TAG, "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, "Error in load data" + e.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate() {
        storeDTOS = new ArrayList<>();
        initLayout();
    }

    @Override
    public void initLayout() {
        popular_rv = view.findViewById(R.id.most_used_rv);
        popular_rv.setHasFixedSize(true);
        popular_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        popularVerticalAdapter = new PopularVerticalAdapter(mActivity, storeDTOS);
        popular_rv.setAdapter(popularVerticalAdapter);
    }

    @Override
    public void updateLayout() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendInitialRequest();
            }
        });
    }

    @Override
    public void sendInitialRequest() {
        if (!firstRequest) {
            firstRequest = true;
            swipeRefresh.setRefreshing(true);
            refreshHandler();
            loaData();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
