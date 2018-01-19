package Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.R;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class MostUsedAdapter extends RecyclerView.Adapter<MostUsedAdapter.MainHolder> {

    private ArrayList<String> list;
    private Activity mActivity;

    public MostUsedAdapter(Activity mActivity, ArrayList<String> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (list != null) {
            holder.textView.setText(list.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MainHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.TextView);
        }
    }
}
