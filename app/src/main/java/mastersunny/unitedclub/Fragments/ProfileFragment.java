package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.StoreOfferAdapter;
import mastersunny.unitedclub.Adapter.UserTransactionAdapter;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, Callback<MoviesResponse> {

    public String TAG = "MostUsedFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView transaction_details_rv;
    private ArrayList<TransactionDTO> transactionDTOS;
    private UserTransactionAdapter transactionAdapter;
    private Toolbar toolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
            transactionDTOS = new ArrayList<>();
            initLayout();
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initLayout() {
        toolbar = view.findViewById(R.id.toolbar);
        for (int i = 5; i < 30; i++) {
            TransactionDTO transactionDTO = new TransactionDTO();
            StoreDTO storeDTO = new StoreDTO();
            if (i % 2 == 0) {
                storeDTO.setStoreName("Paytm");
            } else {
                storeDTO.setStoreName("Amazon");
            }
            transactionDTO.getStoreOfferDTO().setStoreDTO(storeDTO);
            transactionDTO.setPaidAmount(10000);
            transactionDTO.setDueAmount(i);
            transactionDTOS.add(transactionDTO);
        }
        transaction_details_rv = view.findViewById(R.id.transaction_details_rv);
        transaction_details_rv.setHasFixedSize(true);
        transaction_details_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new UserTransactionAdapter(mActivity, transactionDTOS);
        transaction_details_rv.setAdapter(transactionAdapter);
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
