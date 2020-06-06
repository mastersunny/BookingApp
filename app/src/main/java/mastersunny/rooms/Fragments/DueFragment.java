package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mastersunny.rooms.adapters.TransactionAdapter;
import mastersunny.rooms.models.TransactionDTO;
import mastersunny.rooms.R;

/**
 * Created by ASUS on 1/23/2018.
 */

public class DueFragment extends FragmentBase implements View.OnClickListener {

    private Activity mActivity;
    public String TAG = "DueFragment";
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendInitialRequest();
                }
            });
        } else {
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void loaData() {
//        try {
//            apiInterface.getTransactions(Constants.accessToken, Constants.TRANSACTION_DUE).enqueue(new Callback<List<TransactionDTO>>() {
//                @Override
//                public void onResponse(Call<List<TransactionDTO>> call, Response<List<TransactionDTO>> response) {
//                    progressBar.setVisibility(View.GONE);
//                    Constants.debugLog(TAG, response + "");
//                    if (response != null && response.isSuccessful() && response.body() != null) {
//                        transactionDTOS.clear();
//                        transactionDTOS.addAll(response.body());
//                        if (transactionAdapter != null) {
//                            transactionAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<TransactionDTO>> call, Throwable t) {
//                    Constants.debugLog(TAG, "Error in load data " + t.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            Constants.debugLog(TAG, "Error in load data " + e.getMessage());
//        }
    }
}
