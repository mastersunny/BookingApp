package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.activities.TransactionDetailsActivity;
import mastersunny.unitedclub.models.TransactionDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;

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
            holder.client_name.setText(dto.getUserDTO().getName());
            holder.transaction_date.setText(dto.getTransactionDate());
            holder.total_amount.setText(dto.getAmount() + "");
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
                total_amount;


        public MainHolder(View itemView) {
            super(itemView);
            client_name = itemView.findViewById(R.id.client_name);
            client_name.setTypeface(face);
            transaction_date = itemView.findViewById(R.id.transaction_date);
            transaction_date.setTypeface(face);
            total_amount = itemView.findViewById(R.id.total_amount);
            total_amount.setTypeface(face);
        }
    }
}
