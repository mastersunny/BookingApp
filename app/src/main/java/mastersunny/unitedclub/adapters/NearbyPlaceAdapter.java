package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.unitedclub.Listener.ClickListener;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.activities.StoresDetailsActivity;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class NearbyPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = "NearbyPlaceAdapter";

    private ArrayList<StoreDTO> storeDTOS;
    private Activity mActivity;
    public static final int HEADER_ITEM = 1;
    public static final int MAIN_ITEM = 2;
    private ClickListener clickListener;

    public NearbyPlaceAdapter(Activity mActivity, ArrayList<StoreDTO> storeDTOS) {
        this.mActivity = mActivity;
        this.storeDTOS = storeDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item_layout, parent, false);
            return new MainHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_ITEM:
                if (clickListener != null)
                    clickListener.click();
                break;
            case MAIN_ITEM:
                MainHolder mainHolder = (MainHolder) holder;
                final StoreDTO storeDTO = storeDTOS.get(position - 1);
                String imgUrl = ApiClient.BASE_URL + "" + storeDTO.getImageUrl();
                Constants.loadImage(mActivity, imgUrl, mainHolder.place_image);
                mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StoresDetailsActivity.start(view.getContext(), storeDTO);
                    }
                });

                break;
        }
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
        return storeDTOS == null ? 1 : storeDTOS.size() + 1;
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place_image)
        ImageView place_image;

        @BindView(R.id.place_name)
        TextView place_name;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView view_all;

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
