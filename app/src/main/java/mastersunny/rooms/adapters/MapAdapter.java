package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.gmap.GooglePlaceDTO;
import mastersunny.rooms.gmap.Prediction;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.PlaceDTO;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class MapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CityAdapter";

    private List<Prediction> predictions;
    private Activity mActivity;

    public MapAdapter(Activity mActivity, List<Prediction> predictions) {
        this.mActivity = mActivity;
        this.predictions = predictions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final Prediction dto = predictions.get(position);
        mainHolder.main_text.setText(dto.getStructuredFormatting().getMainText());
        mainHolder.secondary_text.setText(dto.getStructuredFormatting().getSecondaryText());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return predictions == null ? 0 : predictions.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_text)
        TextView main_text;

        @BindView(R.id.secondary_text)
        TextView secondary_text;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
