package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomDTO;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomDTO> roomDTOS;
    private Activity mActivity;

    public RoomAdapter(Activity mActivity, List<RoomDTO> roomDTOS) {
        this.mActivity = mActivity;
        this.roomDTOS = roomDTOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RoomDTO roomDTO = roomDTOS.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        mainHolder.address.setText(roomDTO.getAddress());
        mainHolder.room_cost.setText(roomDTO.getRoomCost() + "");
    }

    @Override
    public int getItemCount() {
        return roomDTOS == null ? 0 : roomDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_image)
        ImageView room_image;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.room_cost)
        TextView room_cost;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
