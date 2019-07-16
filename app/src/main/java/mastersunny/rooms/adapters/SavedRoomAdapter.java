package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomDetailsActivity;
import mastersunny.rooms.models.RoomDTO;

public class SavedRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomDTO> roomDTOS = new ArrayList<>();
    private Activity mActivity;
    private String TAG = "RoomAdapter";

    public SavedRoomAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_room_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RoomDTO roomDTO = roomDTOS.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        mainHolder.name.setText(roomDTO.getAddress());
//        mainHolder.title.setText(roomDTO.getNoOfAccommodation() > 1 ? roomDTO.getNoOfAccommodation()
//                + " Seats Room" : roomDTO.getNoOfAccommodation() + " Seat Room");
//        mainHolder.address.setText(roomDTO.getAddress());
//        mainHolder.room_cost.setText(roomDTO.getRoomCost() + "");
//
        if (roomDTO.getImages() != null && roomDTO.getImages().size() > 0) {
            int res = mActivity.getResources().getIdentifier(mActivity.getPackageName()
                    + ":drawable/" + roomDTO.getImages().get(0).getImageUrl(), null, null);
            Glide.with(mActivity).load(res).into(mainHolder.room_image);
//            mainHolder.room_image.setImageResource(res);
        }
//
//        if (roomDTO.isFemaleFriendly()) {
//            mainHolder.female_friendly_layout.setVisibility(View.VISIBLE);
//        } else {
//            mainHolder.female_friendly_layout.setVisibility(View.GONE);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomDetailsActivity.start(v.getContext(), roomDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomDTOS == null ? 0 : roomDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_image)
        ImageView room_image;

        @BindView(R.id.name)
        TextView name;
//
//        @BindView(R.id.address)
//        TextView address;
//
//        @BindView(R.id.room_cost)
//        TextView room_cost;
//
//        @BindView(R.id.female_friendly_layout)
//        TextView female_friendly_layout;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(RoomDTO roomDTO, int position) {
        roomDTOS.add(roomDTO);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        roomDTOS.remove(position);
        notifyItemRemoved(position);
    }
}
