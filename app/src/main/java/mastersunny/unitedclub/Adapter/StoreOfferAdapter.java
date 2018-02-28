package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class StoreOfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "StoreOfferAdapter";

    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private Activity mActivity;
    private Typeface face;
    public static final int HEADER_ITEM = 1;
    public static final int MAIN_ITEM = 2;

    public StoreOfferAdapter(Activity mActivity, ArrayList<StoreOfferDTO> storeOfferDTOS) {
        this.mActivity = mActivity;
        this.storeOfferDTOS = storeOfferDTOS;
        face = Constants.getMediumFace(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_details_layout_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_offer_layout, parent, false);
            return new MainHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (storeOfferDTOS != null) {
            final StoreOfferDTO dto = storeOfferDTOS.get(position);
            if (dto.getStoreDTO().getImageUrl() != null) {
                String imgUrl = ApiClient.BASE_URL + "" + dto.getStoreDTO().getImageUrl();
                Constants.loadImage(mActivity, imgUrl, holder.store_image);
            }
            holder.store_offer.setText(dto.getOffer());
            holder.offer_end_date.setText(dto.getEndDate());
            holder.store_offer.setTypeface(face);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemDetailsActivity.start(mActivity, dto);
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

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView view_all;

        public HeaderHolder(View itemView) {
            super(itemView);
            view_all = itemView.findViewById(R.id.view_all);
        }
    }
}
