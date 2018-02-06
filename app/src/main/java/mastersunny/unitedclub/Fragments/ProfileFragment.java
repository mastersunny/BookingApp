package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.ProfileActivity;
import mastersunny.unitedclub.Activity.TransactionActivity;
import mastersunny.unitedclub.Adapter.TransactionAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.Model.UserDTO;
import mastersunny.unitedclub.R;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    public String TAG = "MostUsedFragment";
    private Activity mActivity;
    private View view;
    private RecyclerView transaction_rv;
    private TransactionAdapter transactionAdapter;
    private ArrayList<TransactionDTO> transactionDTOS;

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
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initLayout() {
        view.findViewById(R.id.edit_profile).setOnClickListener(this);

        transaction_rv = view.findViewById(R.id.transaction_rv);
        transaction_rv.setHasFixedSize(true);
        transaction_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        transactionAdapter = new TransactionAdapter(mActivity, transactionDTOS);
        transaction_rv.setAdapter(transactionAdapter);

        view.findViewById(R.id.view_all_transaction).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                startActivity(new Intent(mActivity, ProfileActivity.class));
                break;
            case R.id.view_all_transaction:
                TransactionActivity.start(v.getContext(), new UserDTO());
                break;
        }
    }
}
