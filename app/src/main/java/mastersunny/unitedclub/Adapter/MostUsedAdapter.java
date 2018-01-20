package mastersunny.unitedclub.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mastersunny.unitedclub.Activity.ItemDetailsActivity;
import mastersunny.unitedclub.Model.Movie;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class MostUsedAdapter extends RecyclerView.Adapter<MostUsedAdapter.MainHolder> {

    private ArrayList<Movie> movies;
    private Activity mActivity;

    public MostUsedAdapter(Activity mActivity, ArrayList<Movie> movies) {
        this.mActivity = mActivity;
        this.movies = movies;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_used_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        if (movies != null) {
            final Movie movie = movies.get(position);
            Constants.loadImage(mActivity, ApiClient.BASE_URL + movie.getPosterPath(), holder.item_image);
            holder.item_offer.setText(movie.getOverview());
            holder.offer_end_date.setText(movie.getReleaseDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra(Constants.ITEM_DTO, movie.getId());
                    mActivity.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        private ImageView item_image;
        private TextView item_offer, offer_end_date;

        public MainHolder(View itemView) {
            super(itemView);
            item_offer = itemView.findViewById(R.id.item_offer);
            offer_end_date = itemView.findViewById(R.id.offer_end_date);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }
}
