package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.activities.StoresDetailsActivity;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PopularVerticalAdapter extends RecyclerView.Adapter<PopularVerticalAdapter.MainHolder> {

    private ArrayList<StoreDTO> storeDTOS;
    private Activity mActivity;
    private Typeface face;

    public PopularVerticalAdapter(Activity mActivity, ArrayList<StoreDTO> storeDTOS) {
        this.mActivity = mActivity;
        this.storeDTOS = storeDTOS;
        face = Constants.getMediumFace(mActivity);
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_verical, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (storeDTOS != null) {
            final StoreDTO storeDTO = storeDTOS.get(position);
            holder.store_name.setText(storeDTO.getStoreName());
            holder.store_name.setTypeface(face);
            holder.total_offer.setText(storeDTO.getTotalOffer() + " Offers");
            holder.total_offer.setTypeface(face);
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


        private TextView store_name;
        private TextView total_offer;

        public MainHolder(View itemView) {
            super(itemView);
            store_name = itemView.findViewById(R.id.store_name);
            total_offer = itemView.findViewById(R.id.total_offer);
        }
    }
}
