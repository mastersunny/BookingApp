package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.UserTransactionAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;

/**
 * Created by ASUS on 1/23/2018.
 */

public class PaidFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private View view;
    public String TAG = "PaidFragment";
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
    public void onClick(View view) {

    }
}
