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

import mastersunny.unitedclub.Activity.CategoryDetailsActivity;
import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Model.CategoryDTO;
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
    private int categoryId;

    public StoreOfferAdapter(Activity mActivity, ArrayList<StoreOfferDTO> storeOfferDTOS, int categoryId) {
        this.mActivity = mActivity;
        this.storeOfferDTOS = storeOfferDTOS;
        face = Constants.getMediumFace(mActivity);
        this.categoryId = categoryId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_offer_bottom_layout, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_offer_layout, parent, false);
            return new MainHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_ITEM:
                HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CategoryDTO categoryDTO = new CategoryDTO();
                        categoryDTO.setCategoryId(categoryId);
                        CategoryDetailsActivity.start(v.getContext(), categoryDTO);
                    }
                });
                break;
            case MAIN_ITEM:
                if (storeOfferDTOS != null) {
                    MainHolder mainHolder = (MainHolder) holder;
                    final StoreOfferDTO dto = storeOfferDTOS.get(position);
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

    @Override
    public int getItemViewType(int position) {
        if (position == storeOfferDTOS.size()) {
            return HEADER_ITEM;
        } else {
            return MAIN_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return storeOfferDTOS == null ? 1 : storeOfferDTOS.size() + 1;
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
