package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import mastersunny.rooms.adapters.StoreOfferAdapter;
import mastersunny.rooms.models.CategoryDTO;
import mastersunny.rooms.models.OfferDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
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
    private ArrayList<OfferDTO> offerDTOS;
    private StoreOfferAdapter storeOfferAdapter;
    private ProgressBar progressBar;
    private boolean firstRequest = false;
    private ApiInterface apiInterface;
    private CategoryDTO categoryDTO;

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
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            offerDTOS = new ArrayList<>();
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
            apiInterface.getCategoryOffers(categoryDTO.getCategoryId(), Constants.accessToken)
                    .enqueue(new Callback<List<OfferDTO>>() {
                        @Override
                        public void onResponse(Call<List<OfferDTO>> call, Response<List<OfferDTO>> response) {
                            Constants.debugLog(TAG, "" + response);
                            if (response.isSuccessful() && response.body() != null) {
                                Constants.debugLog(TAG, "" + response.body());
                                offerDTOS.addAll(response.body());
                                if (storeOfferAdapter != null) {
                                    storeOfferAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OfferDTO>> call, Throwable t) {
                            Constants.debugLog(TAG, "" + t.getMessage());
                        }
                    });
        } catch (Exception e) {
            Constants.debugLog(TAG, "" + e.getMessage());
        }
    }

    private void initLayout() {
        progressBar = view.findViewById(R.id.progressBar);
        view.findViewById(R.id.swipeRefresh).setEnabled(false);

        most_used_rv = view.findViewById(R.id.most_used_rv);
        most_used_rv.setHasFixedSize(true);
        most_used_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapter(mActivity, offerDTOS, categoryDTO);
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
            loaData();
        }
    }
}
