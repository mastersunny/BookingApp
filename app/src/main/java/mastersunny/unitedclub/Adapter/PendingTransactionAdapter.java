package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.TransactionDetailsActivity;
import mastersunny.unitedclub.Model.TransactionDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PendingTransactionAdapter extends RecyclerView.Adapter<PendingTransactionAdapter.MainHolder> {
    private String TAG = "PendingTransactionAdapter";

    private ArrayList<TransactionDTO> transactionDTOS;
    private Activity mActivity;
    static Typeface face;
    ApiInterface apiInterface;

    public PendingTransactionAdapter(Activity mActivity, ArrayList<TransactionDTO> transactionDTOS) {
        this.mActivity = mActivity;
        this.transactionDTOS = transactionDTOS;
        face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_medium.otf");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (transactionDTOS != null) {
            final TransactionDTO dto = transactionDTOS.get(position);
            holder.client_name.setText(dto.getUserDTO().getFirstName());
            holder.transaction_date.setText(dto.getTransactionDate());
            holder.total_amount.setText(dto.getAmount() + "");
            if (dto.getPaidStatus() == Constants.STATUS_PAID) {
                holder.accept.setVisibility(View.VISIBLE);
                holder.decline.setVisibility(View.GONE);
            } else {
                holder.accept.setVisibility(View.GONE);
                holder.decline.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransactionDetailsActivity.start(mActivity, dto.getTransactionId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return transactionDTOS == null ? 0 : transactionDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private TextView client_name, transaction_date,
                total_amount, accept, decline;


        public MainHolder(View itemView) {
            super(itemView);
            client_name = itemView.findViewById(R.id.client_name);
            client_name.setTypeface(face);
            transaction_date = itemView.findViewById(R.id.transaction_date);
            transaction_date.setTypeface(face);
            total_amount = itemView.findViewById(R.id.total_amount);
            total_amount.setTypeface(face);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.decline);
        }
    }
}
