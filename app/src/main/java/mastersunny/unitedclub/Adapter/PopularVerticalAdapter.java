package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.StoresDetailsActivity;
import mastersunny.unitedclub.Model.PopularDTO;
import mastersunny.unitedclub.R;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PopularVerticalAdapter extends RecyclerView.Adapter<PopularVerticalAdapter.MainHolder> {

    private ArrayList<PopularDTO> popularDTOS;
    private Activity mActivity;

    public PopularVerticalAdapter(Activity mActivity, ArrayList<PopularDTO> popularDTOS) {
        this.mActivity = mActivity;
        this.popularDTOS = popularDTOS;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item_verical, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (popularDTOS != null) {
            PopularDTO popularDTO = popularDTOS.get(position);
            holder.company_name.setText(popularDTO.getCompanyName());
            holder.total_offer.setText(popularDTO.getTotalOffer() + " Offers");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.startActivity(new Intent(view.getContext(), StoresDetailsActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return popularDTOS == null ? 0 : popularDTOS.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {


        private TextView company_name;
        private TextView total_offer;

        public MainHolder(View itemView) {
            super(itemView);
            company_name = itemView.findViewById(R.id.company_name);
            total_offer = itemView.findViewById(R.id.total_offer);
        }
    }
}
