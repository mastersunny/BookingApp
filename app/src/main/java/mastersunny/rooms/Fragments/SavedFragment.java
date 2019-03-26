package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mastersunny.rooms.adapters.PopularVerticalAdapter;
import mastersunny.rooms.models.StoreDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class SavedFragment extends FragmentBase implements View.OnClickListener {

    public String TAG = SavedFragment.class.getName();
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
            apiInterface.getPopularStores(Constants.accessToken).enqueue(new Callback<List<StoreDTO>>() {
                @Override
                public void onResponse(Call<List<StoreDTO>> call, Response<List<StoreDTO>> response) {
                    Constants.debugLog(TAG, "" + response);
                    if (response != null && response.isSuccessful()) {
                        storeDTOS.clear();
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
//            firstRequest = true;
//            swipeRefresh.setRefreshing(true);
//            refreshHandler();
//            loaData();
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
