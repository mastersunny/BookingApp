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

import mastersunny.unitedclub.Activity.StoresDetailsActivity;
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

public class FirstCategoryFragment extends Fragment implements View.OnClickListener {

    public String TAG = "FirstCategoryFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView most_used_rv;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private StoreOfferAdapter storeOfferAdapter;
    private ProgressBar progressBar;
    private boolean firstRequest = false;
    private ApiInterface apiInterface;
    private CategoryDTO categoryDTO;

    public static FirstCategoryFragment newInstance(CategoryDTO categoryDTO) {
        FirstCategoryFragment fragment = new FirstCategoryFragment();
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
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            storeOfferDTOS = new ArrayList<>();
            getIntentData();
            initLayout();
        }

        return view;
    }

    private void getIntentData() {
        categoryDTO = (CategoryDTO) getArguments().getSerializable(Constants.CATEGORY_DTO);
    }

    private void loaData() {
        try {
            apiInterface.getStoreOffers(categoryDTO.getCategoryId(), Constants.getAccessToken(mActivity))
                    .enqueue(new Callback<List<StoreOfferDTO>>() {
                        @Override
                        public void onResponse(Call<List<StoreOfferDTO>> call, Response<List<StoreOfferDTO>> response) {
                            Constants.debugLog(TAG, "" + response);
                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful() && response.body() != null) {
                                Constants.debugLog(TAG, "" + response.body());
                                storeOfferDTOS.addAll(response.body());
                                if (storeOfferAdapter != null) {
                                    storeOfferAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<StoreOfferDTO>> call, Throwable t) {
                            Constants.debugLog(TAG, "" + t.getMessage());
                        }
                    });
        } catch (Exception e) {
            Constants.debugLog(TAG, "" + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!firstRequest) {
                    progressBar.setVisibility(View.VISIBLE);
                    firstRequest = true;
                    loaData();
                }
            }
        });
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initLayout() {
        progressBar = view.findViewById(R.id.progressBar);
        view.findViewById(R.id.swipeRefresh).setEnabled(false);

        most_used_rv = view.findViewById(R.id.most_used_rv);
        most_used_rv.setHasFixedSize(true);
        most_used_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapter(mActivity, storeOfferDTOS, categoryDTO.getCategoryId());
        most_used_rv.setAdapter(storeOfferAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
