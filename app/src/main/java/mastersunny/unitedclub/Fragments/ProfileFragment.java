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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.activities.EditProfileActivity;
import mastersunny.unitedclub.activities.TransactionActivity;
import mastersunny.unitedclub.adapters.PendingTransactionAdapter;
import mastersunny.unitedclub.models.RestModel;
import mastersunny.unitedclub.models.TransactionDTO;
import mastersunny.unitedclub.models.UserDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.CircleImageView;
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
    private PendingTransactionAdapter transactionAdapter;
    private ArrayList<TransactionDTO> transactionDTOS;
    private ProgressBar progressBar;
    protected SwipeRefreshLayout swipeRefresh;
    protected Handler handler = new Handler();
    private ApiInterface apiInterface;
    private TextView pending_transaction_message, user_name;
    private UserDTO userDTO;
    private CircleImageView profile_image;

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
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            transactionDTOS = new ArrayList<>();
            initLayout();
            checkNoData();
        }

        return view;
    }

    private void initLayout() {
        profile_image = view.findViewById(R.id.profile_image);
        user_name = view.findViewById(R.id.user_name);
        pending_transaction_message = view.findViewById(R.id.pending_transaction_message);
        progressBar = view.findViewById(R.id.progressBar);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendInitialRequest();
            }
        });

        transaction_rv = view.findViewById(R.id.transaction_rv);
        transaction_rv.setHasFixedSize(true);
        transaction_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new PendingTransactionAdapter(mActivity, transactionDTOS);
        transaction_rv.setAdapter(transactionAdapter);

        view.findViewById(R.id.edit_profile).setOnClickListener(this);
        view.findViewById(R.id.view_all_transaction).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sendInitialRequest();
    }

    private void updateProfile(UserDTO userDTO) {
        user_name.setText(userDTO.getName() + " " + userDTO.getName());
        if (!TextUtils.isEmpty(userDTO.getProfileImage())) {
            String imgUrl = ApiClient.BASE_URL + userDTO.getCreatedAt();
            Constants.loadImage(mActivity, imgUrl, profile_image);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                EditProfileActivity.start(v.getContext(), userDTO);
                break;
            case R.id.view_all_transaction:
                TransactionActivity.start(v.getContext(), new UserDTO());
                break;
        }
    }

    private void checkNoData() {
        if (transactionDTOS.size() == 0) {
            pending_transaction_message.setVisibility(View.VISIBLE);
        } else {
            pending_transaction_message.setVisibility(View.GONE);
        }
    }

    private void loaData() {
        try {
            apiInterface.getProfileDetails(Constants.accessToken).enqueue(new Callback<RestModel>() {
                @Override
                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
                    if (response != null && response.isSuccessful() && response.body().getMetaData().isData()) {
                        userDTO = response.body().getUserDTO();
                        updateProfile(userDTO);
                        Constants.debugLog(TAG, userDTO.toString());
                    }
                }

                @Override
                public void onFailure(Call<RestModel> call, Throwable t) {
                    Constants.debugLog(TAG, t.getMessage());
                }
            });

            apiInterface.getTransactions(Constants.accessToken, Constants.TRANSACTION_PENDING).enqueue(new Callback<List<TransactionDTO>>() {
                @Override
                public void onResponse(Call<List<TransactionDTO>> call, Response<List<TransactionDTO>> response) {
                    Constants.debugLog(TAG, response + "");
                    swipeRefresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    if (response != null && response.isSuccessful() && response.body() != null) {
                        transactionDTOS.clear();
                        transactionDTOS.addAll(response.body());
                        if (transactionAdapter != null) {
                            transactionAdapter.notifyDataSetChanged();
                        }
                    }
                    checkNoData();
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
        swipeRefresh.setRefreshing(true);
        refreshHandler();
        loaData();
    }
}
