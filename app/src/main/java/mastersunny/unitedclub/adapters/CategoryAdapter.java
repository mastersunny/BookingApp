package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.activities.CategoryDetailsActivity;
import mastersunny.unitedclub.models.CategoryDTO;
import mastersunny.unitedclub.R;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MainHolder> {

    private ArrayList<CategoryDTO> offerCategories;
    private Activity mActivity;

    public CategoryAdapter(Activity mActivity, ArrayList<CategoryDTO> offerCategories) {
        this.mActivity = mActivity;
        this.offerCategories = offerCategories;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_verical, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (offerCategories != null) {
            final CategoryDTO categoryDTO = offerCategories.get(position);
            holder.store_name.setText(categoryDTO.getCategoryName());
            holder.total_offer.setText(categoryDTO.getTotalOffer() + " Offers");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CategoryDetailsActivity.start(view.getContext(), categoryDTO);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return offerCategories == null ? 0 : offerCategories.size();
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
