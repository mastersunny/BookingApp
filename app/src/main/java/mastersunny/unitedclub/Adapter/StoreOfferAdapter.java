package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class StoreOfferAdapter extends RecyclerView.Adapter<StoreOfferAdapter.MainHolder> {
    private String TAG = "StoreOfferAdapter";

    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private Activity mActivity;
    Typeface face;

    public StoreOfferAdapter(Activity mActivity, ArrayList<StoreOfferDTO> storeOfferDTOS) {
        this.mActivity = mActivity;
        this.storeOfferDTOS = storeOfferDTOS;
        face = Typeface.createFromAsset(mActivity.getAssets(), "avenirltstd_medium.otf");
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_offer_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (storeOfferDTOS != null) {
//            final StoreOfferDTO storeOfferDTO = storeOfferDTOS.get(position);
//
//            holder.store_offer.setText(storeOfferDTO.getOffer());
            holder.store_offer.setTypeface(face);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ItemDetailsActivity.start(mActivity, null);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storeOfferDTOS == null ? 0 : storeOfferDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private ImageView store_image;
        private TextView store_offer;
        private TextView offer_end_date;

        public MainHolder(View itemView) {
            super(itemView);
            store_image = itemView.findViewById(R.id.store_image);
            store_offer = itemView.findViewById(R.id.store_offer);
            offer_end_date = itemView.findViewById(R.id.offer_end_date);

        }
    }
}
