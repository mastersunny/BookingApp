package mastersunny.rooms.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.rooms.models.TransactionDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MainHolder> {
    private String TAG = "TransactionAdapter";

    private ArrayList<TransactionDTO> transactionDTOS;
    private Activity mActivity;
    static Typeface face;
    private int colorGreen;
    private int colorRed;

    public TransactionAdapter(Activity mActivity, ArrayList<TransactionDTO> transactionDTOS) {
        this.mActivity = mActivity;
        this.transactionDTOS = transactionDTOS;
        face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_medium.otf");
        colorGreen = mActivity.getResources().getColor(R.color.green);
        colorRed = mActivity.getResources().getColor(R.color.red);
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (transactionDTOS != null) {
            TransactionDTO dto = transactionDTOS.get(position);
            String imgUrl = dto.getOfferDTO().getStoreDTO().getImageUrl();
            if (!TextUtils.isEmpty(imgUrl)) {
                Constants.loadImage(mActivity, (ApiClient.BASE_URL + imgUrl), holder.store_image);
            }


            holder.offer_details.setText(dto.getOfferDTO().getOffer());
            holder.offer_date.setText(dto.getTransactionDate());
            holder.total_amount.setText(dto.getAmount() + "");
//            if (dto.getPaidStatus() == Constants.STATUS_PAID) {
//                holder.paid_status.setText("Paid");
//                holder.paid_status.setTextColor(colorGreen);
//            } else {
//                holder.paid_status.setText("Due");
//                holder.paid_status.setTextColor(colorRed);
//            }
            holder.paid_status.setTypeface(face);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return transactionDTOS == null ? 0 : transactionDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private TextView offer_details, offer_date, total_amount, paid_status;
        private ImageView store_image;


        public MainHolder(View itemView) {
            super(itemView);
            store_image = itemView.findViewById(R.id.image);
            offer_details = itemView.findViewById(R.id.offer_details);
            offer_details.setTypeface(face);
            offer_date = itemView.findViewById(R.id.offer_date);
            offer_date.setTypeface(face);
            total_amount = itemView.findViewById(R.id.total_amount);
            total_amount.setTypeface(face);
            paid_status = itemView.findViewById(R.id.paid_status);
            paid_status.setTypeface(face);
        }
    }
}
