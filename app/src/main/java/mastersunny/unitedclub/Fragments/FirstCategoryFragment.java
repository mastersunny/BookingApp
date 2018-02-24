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

import mastersunny.unitedclub.Adapter.StoreOfferAdapter;
import mastersunny.unitedclub.Model.CategoryDTO;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class FirstCategoryFragment extends Fragment implements View.OnClickListener, Callback<MoviesResponse> {

    public String TAG = "MostUsedFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView most_used_rv;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private StoreOfferAdapter storeOfferAdapter;
    private ProgressBar progressBar;
    private boolean firstRequest = false;

    public static FirstCategoryFragment newInstance(CategoryDTO categoryDTO) {
        FirstCategoryFragment fragment = new FirstCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.CATEGORY_DTO, categoryDTO);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.d(MerchantHomeFragment.TAG, "" + "onAttach");
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            storeOfferDTOS = new ArrayList<>();
            initLayout();
            loaData();
        }

        return view;
    }

    private void loaData() {
//        if (ApiClient.API_KEY.isEmpty()) {
//            Toast.makeText(mActivity, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
//            return;
//        }
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        apiService.getTopRatedMovies(ApiClient.API_KEY).enqueue(this);
    }

    @Override
    public void onResume() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!firstRequest) {
                    firstRequest = true;
                    for (int i = 0; i < 10; i++) {
                        StoreOfferDTO storeOfferDTO = new StoreOfferDTO();
                        storeOfferDTOS.add(storeOfferDTO);
                    }
                    if (storeOfferAdapter != null)
                        storeOfferAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
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
    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {

    }
}