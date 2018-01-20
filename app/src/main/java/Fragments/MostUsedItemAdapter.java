package Fragments;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.R;

/**
 * Created by ASUS on 1/17/2018.
 */

public class MostUsedItemAdapter extends RecyclerView.Adapter<MostUsedItemAdapter.MainHolder> {

    public String TAG = "MostUsedItemAdapter";

    private Activity mActivity;
    private ArrayList<String> list;

    public MostUsedItemAdapter(Activity mActivity, ArrayList<String> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_used_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (list != null) {
            holder.item_message.setText(list.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private TextView item_message;

        public MainHolder(View itemView) {
            super(itemView);
            item_message = itemView.findViewById(R.id.item_message);
        }
    }
}
