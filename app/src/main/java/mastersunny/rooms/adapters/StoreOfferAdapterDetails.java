package mastersunny.rooms.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mastersunny.rooms.activities.ItemDetailsActivity;
import mastersunny.rooms.models.OfferDTO;
import mastersunny.rooms.models.StoreDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class StoreOfferAdapterDetails extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "StoreOfferAdapter";

    private ArrayList<OfferDTO> offerDTOS;
    private Activity mActivity;
    private Typeface face;
    public static final int HEADER_ITEM = 1;
    public static final int MAIN_ITEM = 2;
    private StoreDTO storeDTO;

    public StoreOfferAdapterDetails(Activity mActivity, ArrayList<OfferDTO> offerDTOS, StoreDTO storeDTO) {
        this.mActivity = mActivity;
        this.offerDTOS = offerDTOS;
        this.storeDTO = storeDTO;
        face = Constants.getMediumFace(mActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_details_layout_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item_layout, parent, false);
            return new MainHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_ITEM:
                if (storeDTO != null) {
                    HeaderHolder headerHolder = (HeaderHolder) holder;
                    headerHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            Toast.makeText(mActivity, Float.toString(v), Toast.LENGTH_LONG).show();
                        }
                    });
                    headerHolder.follow_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mActivity, "Followed successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                    String imgUrl = ApiClient.BASE_URL + "" + storeDTO.getImageUrl();
                    Constants.loadImage(mActivity, imgUrl, headerHolder.store_image);
                    headerHolder.total_offer.setText(storeDTO.getTotalOffer() + " Offers");
                }
                break;
            case MAIN_ITEM:
                if (offerDTOS != null) {
                    MainHolder mainHolder = (MainHolder) holder;
                    final OfferDTO dto = offerDTOS.get(position - 1);
                    if (dto.getStoreDTO().getImageUrl() != null) {
                        String imgUrl = ApiClient.BASE_URL + "" + dto.getStoreDTO().getImageUrl();
                        Constants.loadImage(mActivity, imgUrl, mainHolder.store_image);
                    }
                    mainHolder.store_offer.setText(dto.getOffer());
                    mainHolder.offer_end_date.setText(dto.getEndDate());
                    mainHolder.store_offer.setTypeface(face);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ItemDetailsActivity.start(mActivity, dto);
                        }
                    });
                }
                break;
        }

    }

    public void setStoreDTO(StoreDTO storeDTO) {
        this.storeDTO = storeDTO;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_ITEM;
        } else {
            return MAIN_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return offerDTOS == null ? 1 : offerDTOS.size() + 1;
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private ImageView store_image;
        private TextView store_offer;
        private TextView offer_end_date;

        public MainHolder(View itemView) {
            super(itemView);
//            store_image = itemView.findViewById(R.id.store_image);
//            store_offer = itemView.findViewById(R.id.store_offer);
//            offer_end_date = itemView.findViewById(R.id.offer_end_date);

        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        private ImageView store_image;
        private TextView total_offer;
        private RatingBar ratingBar;
        private LinearLayout follow_layout;

        public HeaderHolder(View itemView) {
            super(itemView);
            store_image = itemView.findViewById(R.id.image);
            total_offer = itemView.findViewById(R.id.total_offer);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            follow_layout = itemView.findViewById(R.id.follow_layout);
        }
    }
}
