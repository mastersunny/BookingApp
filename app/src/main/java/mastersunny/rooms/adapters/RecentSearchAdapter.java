package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mastersunny.rooms.R;
import mastersunny.rooms.models.ItemType;
import mastersunny.rooms.models.PlaceRoomItem;
import mastersunny.rooms.models.RoomDTO;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "RecentSearchAdapter";
    private List<PlaceRoomItem> placeRoomItems;
    private Activity mActivity;

    public RecentSearchAdapter(Activity mActivity, List<PlaceRoomItem> placeRoomItems) {
        this.mActivity = mActivity;
        this.placeRoomItems = placeRoomItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ItemType.ITEM_TYPE_LOCATION.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ItemType.ITEM_TYPE_ROOM.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_item_layout, parent, false);
            return new RecentViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_verical, parent, false);
            return new MainHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        final RoomDTO roomDTO = roomDTOS.get(position);
//        MainHolder mainHolder = (MainHolder) holder;
//        mainHolder.title.setText(roomDTO.getNoOfAccommodation() > 1 ? roomDTO.getNoOfAccommodation()
//                + " Seats Room" : roomDTO.getNoOfAccommodation() + " Seat Room");
//        mainHolder.address.setText(roomDTO.getAddress());
//        mainHolder.room_cost.setText(roomDTO.getRoomCost() + "");
//
//        if (roomDTO.getImages() != null && roomDTO.getImages().size() > 0) {
//            Constants.loadImage(mActivity, roomDTO.getImages().get(0).getImageUrl(),
//                    mainHolder.room_image);
//        }
//
//        if (roomDTO.isFemaleFriendly()) {
//            mainHolder.female_friendly_layout.setVisibility(View.VISIBLE);
//        } else {
//            mainHolder.female_friendly_layout.setVisibility(View.GONE);
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RoomDetailsActivity.start(v.getContext(), roomDTO);
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ItemType.ITEM_TYPE_LOCATION.getValue();
        } else {
            return placeRoomItems.get(position - 1).getItemType();
        }
    }

    @Override
    public int getItemCount() {
        return placeRoomItems == null ? 0 : placeRoomItems.size() + 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {
        public RecentViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class MainHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.room_image)
//        ImageView room_image;
//
//        @BindView(R.id.title)
//        TextView title;
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
//            ButterKnife.bind(this, itemView);
        }
    }
}
