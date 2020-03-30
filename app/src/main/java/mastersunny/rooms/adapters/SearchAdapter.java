package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.GpsListener;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.ItemType;
import mastersunny.rooms.models.RoomDTO;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "SearchAdapter";
    private List<RoomDTO> roomDTOS;
    private List<DivisionResponseDto> placeDTOS;
    private Activity mActivity;

    private RoomSearchListener roomSearchListener;
    private GpsListener gpsListener;

    public SearchAdapter(Activity mActivity, List<RoomDTO> roomDTOS, List<DivisionResponseDto> placeDTOS) {
        this.mActivity = mActivity;
        this.roomDTOS = roomDTOS;
        this.placeDTOS = placeDTOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ItemType.ITEM_TYPE_LOCATION.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
            return new LocationViewHolder(view);
        } else if (viewType == ItemType.ITEM_TYPE_RECENT_HEADER.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_header, parent, false);
            return new RecentHeaderViewHolder(view);
        } else if (viewType == ItemType.ITEM_TYPE_RECENT_SEARCH.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_item_layout, parent, false);
            return new RecentViewHolder(view);
        } else if (viewType == ItemType.ITEM_TYPE_ALL_CITY_HEADER.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_city_header, parent, false);
            return new CityHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_layout, parent, false);
            return new CityViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CityViewHolder) {
            if (roomDTOS == null) position = position - 2;
            else {
                if (roomDTOS.size() == 0) position = position - 2;
                else position = position - (roomDTOS.size() + 3);
            }

            CityViewHolder cityViewHolder = (CityViewHolder) holder;
            final DivisionResponseDto placeDTO = placeDTOS.get(position);
            cityViewHolder.name.setText(placeDTO.getName());
            cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (roomSearchListener != null) {
                        roomSearchListener.onDistrictSearch(placeDTO);
                    }
                }
            });
        } else if (holder instanceof RecentViewHolder) {
            position = position - 2;
            RecentViewHolder recentViewHolder = (RecentViewHolder) holder;
            final RoomDTO roomDTO = roomDTOS.get(position);
            recentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (roomSearchListener != null) {
                        roomSearchListener.onRecentSearch(roomDTO);
                    }
                }
            });
        } else if (holder instanceof LocationViewHolder) {
            LocationViewHolder locationViewHolder = (LocationViewHolder) holder;
            locationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gpsListener != null) {
                        gpsListener.turnOnGps();
                    }
                }
            });
        }

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
        int firstListSize = 0;
        int secondListSize = 0;

        if (roomDTOS != null)
            firstListSize = roomDTOS.size();
        if (placeDTOS != null)
            secondListSize = placeDTOS.size();

        if (position == 0) {
            return ItemType.ITEM_TYPE_LOCATION.getValue();
        }
        if (firstListSize > 0 && secondListSize > 0) {
            if (position == 1) {
                return ItemType.ITEM_TYPE_RECENT_HEADER.getValue();
            } else if (position == 2 + firstListSize) {
                return ItemType.ITEM_TYPE_ALL_CITY_HEADER.getValue();
            } else if (position > 2 + firstListSize) {
                return ItemType.ITEM_TYPE_ALL_CITY.getValue();
            } else {
                return ItemType.ITEM_TYPE_RECENT_SEARCH.getValue();
            }
        } else if (firstListSize == 0 && secondListSize > 0) {
            if (position == 1) {
                return ItemType.ITEM_TYPE_ALL_CITY_HEADER.getValue();
            } else {
                return ItemType.ITEM_TYPE_ALL_CITY.getValue();
            }
        } else if (secondListSize == 0 && firstListSize > 0) {
            if (position == 1) {
                return ItemType.ITEM_TYPE_RECENT_HEADER.getValue();
            } else {
                return ItemType.ITEM_TYPE_RECENT_SEARCH.getValue();
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int firstListSize = 0;
        int secondListSize = 0;

        if (roomDTOS != null)
            firstListSize = roomDTOS.size();
        if (placeDTOS != null)
            secondListSize = placeDTOS.size();

        if (firstListSize == 0 && secondListSize == 0) {
            return 1;
        }
        if (secondListSize > 0 && firstListSize > 0)
            return 3 + firstListSize + secondListSize;
        else if (secondListSize > 0 && firstListSize == 0)
            return 2 + secondListSize;
        else if (secondListSize == 0 && firstListSize > 0)
            return 2 + firstListSize;
        else return 0;
    }

    public class CityHeaderViewHolder extends RecyclerView.ViewHolder {
        public CityHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class RecentHeaderViewHolder extends RecyclerView.ViewHolder {
        public RecentHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder {


        public RecentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        public LocationViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setItemSelectListener(RoomSearchListener roomSearchListener) {
        this.roomSearchListener = roomSearchListener;
    }

    public void setGpsListener(GpsListener gpsListener) {
        this.gpsListener = gpsListener;
    }

}
