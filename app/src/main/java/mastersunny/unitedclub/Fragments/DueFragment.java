package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.UserTransactionAdapter;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ASUS on 1/23/2018.
 */

public class DueFragment extends Fragment implements View.OnClickListener, Callback<MoviesResponse> {

    private Activity mActivity;
    private View view;
    public String TAG = "PaidFragment";
    private Toolbar toolbar;
    private RecyclerView transaction_details_rv;
    private ArrayList<TransactionDTO> transactionDTOS;
    private UserTransactionAdapter transactionAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.transaction_fragment_layout, container, false);
            transactionDTOS = new ArrayList<>();
            initLayout();
        }

        return view;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint("Search");
            searchView.setIconifiedByDefault(false);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {

    }
}
