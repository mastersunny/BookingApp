package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Activity.StoresDetailsActivity;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MainHolder> {
    private String TAG = "PopularAdapter";

    private ArrayList<StoreDTO> storeDTOS;
    private Activity mActivity;

    public PopularAdapter(Activity mActivity, ArrayList<StoreDTO> storeDTOS) {
        this.mActivity = mActivity;
        this.storeDTOS = storeDTOS;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (storeDTOS != null) {
            final StoreDTO storeDTO = storeDTOS.get(position);
            String imgUrl = ApiClient.BASE_URL + "" + storeDTO.getBannerImg();
            Log.d(TAG, " " + imgUrl + " " + storeDTO.getStoreId() + " " + storeDTO.getStoreName());
            Constants.loadImage(mActivity, imgUrl, holder.store_image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoresDetailsActivity.start(view.getContext(), storeDTO);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storeDTOS == null ? 0 : storeDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private ImageView store_image;

        public MainHolder(View itemView) {
            super(itemView);
            store_image = itemView.findViewById(R.id.store_image);

        }
    }
}
