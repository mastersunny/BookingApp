package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MainHolder> {
    private String TAG = "TransactionAdapter";

    private ArrayList<TransactionDTO> transactionDTOS;
    private Activity mActivity;

    public TransactionAdapter(Activity mActivity, ArrayList<TransactionDTO> transactionDTOS) {
        this.mActivity = mActivity;
        this.transactionDTOS = transactionDTOS;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (transactionDTOS != null) {
//            TransactionDTO transactionDTO = transactionDTOS.get(position);
//            holder.store_name.setText(transactionDTO.getStoreOfferDTO().getStoreDTO().getStoreName());
//            holder.paid_amount.setText("Paid: " + transactionDTO.getPaidAmount() + "");
//            holder.due_amount.setText("Due: " + transactionDTO.getDueAmount() + "");
//            final StoreOfferDTO storeOfferDTO = storeOfferDTOS.get(position);
//
//            holder.store_offer.setText(storeOfferDTO.getOffer());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return transactionDTOS == null ? 0 : transactionDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private TextView store_name;
        private TextView paid_amount;
        private TextView due_amount;
        private TextView payment_date;

        public MainHolder(View itemView) {
            super(itemView);
//            store_name = itemView.findViewById(R.id.store_name);
//            paid_amount = itemView.findViewById(R.id.paid_amount);
//            due_amount = itemView.findViewById(R.id.due_amount);
//            payment_date = itemView.findViewById(R.id.payment_date);
        }
    }
}
