package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Activity.EditProfileActivity;
import mastersunny.unitedclub.Activity.TransactionActivity;
import mastersunny.unitedclub.Adapter.TransactionAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.Model.UserDTO;
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

public class ProfileFragment extends Fragment implements View.OnClickListener {

    public String TAG = "ProfileFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView transaction_rv;
    private TransactionAdapter transactionAdapter;
    private ArrayList<TransactionDTO> transactionDTOS;
    ApiInterface apiService;
    private ProgressBar progressBar;
    protected SwipeRefreshLayout swipeRefresh;
    protected boolean firstRequest = false;
    protected Handler handler = new Handler();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    protected void refreshHandler() {
        handler.postDelayed(runnable, Constants.REQUEST_TIMEOUT);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Constants.debugLog("TAG", "" + swipeRefresh);
            if (swipeRefresh != null) {
                swipeRefresh.setRefreshing(false);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
            apiService = ApiClient.getClient().create(ApiInterface.class);
            transactionDTOS = new ArrayList<>();
            initLayout();

        }

        return view;
    }

    private void initLayout() {
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                firstRequest = false;
                sendInitialRequest();
            }
        });

        transaction_rv = view.findViewById(R.id.transaction_rv);
        transaction_rv.setHasFixedSize(true);
        transaction_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new TransactionAdapter(mActivity, transactionDTOS);
        transaction_rv.setAdapter(transactionAdapter);

        view.findViewById(R.id.view_all_transaction).setOnClickListener(this);
        view.findViewById(R.id.edit_profile).setOnClickListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            sendInitialRequest();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                startActivity(new Intent(mActivity, EditProfileActivity.class));
                break;
            case R.id.view_all_transaction:
                TransactionActivity.start(v.getContext(), new UserDTO());
                break;
        }
    }

    private void loaData() {
        try {
            apiService.getTransactions(Constants.STATUS_RECENT, Constants.getAccessToken(mActivity)).enqueue(new Callback<List<TransactionDTO>>() {
                @Override
                public void onResponse(Call<List<TransactionDTO>> call, Response<List<TransactionDTO>> response) {
                    Constants.debugLog(TAG, response + "");
                    if (response != null && response.isSuccessful() && response.body() != null) {
                        transactionDTOS.clear();
                        transactionDTOS.addAll(response.body());
                        if (transactionAdapter != null) {
                            transactionAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<TransactionDTO>> call, Throwable t) {
                    Constants.debugLog(TAG, "Error in load data " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, "Error in load data " + e.getMessage());
        }
    }

    public void sendInitialRequest() {
        if (!firstRequest) {
            firstRequest = true;
            swipeRefresh.setRefreshing(true);
            refreshHandler();
            loaData();
        }
    }
}
