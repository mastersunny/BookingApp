package mastersunny.rooms.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.DistrictResponseDto;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class LocalityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "LocalityAdapter";

    private List<DistrictResponseDto> localityDTOS;
    private Activity mActivity;

    private RoomSearchListener roomSearchListener;

    public LocalityAdapter(Activity mActivity, List<DistrictResponseDto> localityDTOS) {
        this.mActivity = mActivity;
        this.localityDTOS = localityDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locality_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final DistrictResponseDto localityDTO = localityDTOS.get(position);
        mainHolder.name.setText(localityDTO.getName());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomSearchListener != null) {
                    roomSearchListener.onSearch(localityDTO.getName(), localityDTO.getLatitude(),
                            localityDTO.getLongitude());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return localityDTOS == null ? 0 : localityDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView name;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemSelectListener(RoomSearchListener roomSearchListener) {
        this.roomSearchListener = roomSearchListener;
    }

}
