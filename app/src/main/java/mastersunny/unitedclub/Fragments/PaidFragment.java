package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Adapter.TransactionAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 1/23/2018.
 */

public class PaidFragment extends FragmentBase implements View.OnClickListener {

    private Activity mActivity;
    public String TAG = "PaidFragment";
    private RecyclerView transaction_details_rv;
    private ArrayList<TransactionDTO> transactionDTOS;
    private TransactionAdapter transactionAdapter;
    private ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate() {
        transactionDTOS = new ArrayList<>();
        initLayout();
    }

    @Override
    public void initLayout() {
        progressBar = view.findViewById(R.id.progressBar);

        transaction_details_rv = view.findViewById(R.id.most_used_rv);
        transaction_details_rv.setHasFixedSize(true);
        transaction_details_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new TransactionAdapter(mActivity, transactionDTOS);
        transaction_details_rv.setAdapter(transactionAdapter);
    }

    @Override
    public void updateLayout() {

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
    public void onResume() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sendInitialRequest();
            }
        });
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

    private void loaData() {
        try {
            apiInterface.getPaidTransactions(Constants.getAccessToken(mActivity)).enqueue(new Callback<List<TransactionDTO>>() {
                @Override
                public void onResponse(Call<List<TransactionDTO>> call, Response<List<TransactionDTO>> response) {
                    Constants.debugLog(TAG, response + "");
                    progressBar.setVisibility(View.GONE);
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
}
