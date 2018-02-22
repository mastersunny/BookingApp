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

import mastersunny.unitedclub.Adapter.StoreOfferAdapter;
import mastersunny.unitedclub.Model.CategoryDTO;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreOfferDTO;
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

public class CategoryFragment extends Fragment implements View.OnClickListener {

    public String TAG = "CategoryFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView most_used_rv;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private StoreOfferAdapter storeOfferAdapter;
    private ProgressBar progressBar;
    ApiInterface apiService;
    private String accessToken = "";
    private boolean firstRequest = false;

    public static CategoryFragment newInstance(CategoryDTO categoryDTO) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CATEGORY_DTO, categoryDTO);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            apiService = ApiClient.getClient().create(ApiInterface.class);
            storeOfferDTOS = new ArrayList<>();
            accessToken = mActivity.getSharedPreferences(Constants.ACCESS_TOKEN, Context.MODE_PRIVATE)
                    .getString(Constants.ACCESS_TOKEN, "");
            initLayout();
        }

        return view;
    }

    private void loaData() {
        apiService.getCategoryOffers(2, accessToken).enqueue(new Callback<List<StoreOfferDTO>>() {
            @Override
            public void onResponse(Call<List<StoreOfferDTO>> call, Response<List<StoreOfferDTO>> response) {
                progressBar.setVisibility(View.GONE);
                if (response != null && response.isSuccessful()) {
                    storeOfferDTOS.addAll(response.body());
                    if (storeOfferAdapter != null) {
                        storeOfferAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<StoreOfferDTO>> call, Throwable t) {

            }
        });
    }

    private void initLayout() {
        progressBar = view.findViewById(R.id.progressBar);
        most_used_rv = view.findViewById(R.id.most_used_rv);
        most_used_rv.setHasFixedSize(true);
        most_used_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapter(mActivity, storeOfferDTOS);
        most_used_rv.setAdapter(storeOfferAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !firstRequest) {
            firstRequest = true;
            Constants.debugLog(TAG, "Visible");
            loaData();
        } else {
        }
    }
}
