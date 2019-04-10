package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.ItemType;
import mastersunny.rooms.models.PlaceDTO;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.utils.Constants;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "HomeAdapter";
    PlaceAdapter placeAdapter;
    private List<PlaceDTO> placeDTOS;

    PopularAdapter popularAdapter;
    private List<PlaceDTO> popularPlaces;

    DealAdapter dealAdapter;
    private List<PlaceDTO> deals;

    private Activity mActivity;

    RecyclerView.RecycledViewPool viewPool;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CITY = 2;
    public static final int TYPE_POPULAR = 3;
    public static final int TYPE_DEAL = 4;

    public void setCities(List<PlaceDTO> placeDTOS) {
        this.placeDTOS = placeDTOS;
    }

    public void setPopularPlaces(List<PlaceDTO> popularPlaces) {
        this.popularPlaces = popularPlaces;

    }

    public void setDeals(List<PlaceDTO> deals) {
        this.deals = deals;
    }


    public HomeAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_item_layout, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_CITY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_city_item_layout, parent, false);
            return new CityViewHolder(view);
        } else if (viewType == TYPE_POPULAR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_item_layout, parent, false);
            return new PopularViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_deal_item_layout, parent, false);
            return new DealViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Constants.debugLog(TAG, "position " + position);
        if (holder instanceof CityViewHolder) {
            CityViewHolder cityViewHolder = (CityViewHolder) holder;
            cityViewHolder.rv_cities.setRecycledViewPool(viewPool);
            placeAdapter.notifyDataSetChanged();
        } else if (holder instanceof PopularViewHolder) {
            PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
            popularViewHolder.rv_popular.setRecycledViewPool(viewPool);
            popularAdapter.notifyDataSetChanged();
        } else if (holder instanceof DealViewHolder) {
            DealViewHolder dealViewHolder = (DealViewHolder) holder;
            dealViewHolder.rv_deals.setRecycledViewPool(viewPool);
            dealAdapter.notifyDataSetChanged();
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
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_CITY;
        } else if (position == 2) {
            if (popularPlaces != null && popularPlaces.size() > 0) {
                return TYPE_POPULAR;
            } else {
                return TYPE_DEAL;
            }
        } else if (position == 3) {
            return TYPE_DEAL;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int itemSize = 1;

        if (placeDTOS != null && placeDTOS.size() > 0)
            itemSize++;
        if (popularPlaces != null && popularPlaces.size() > 0)
            itemSize++;
        if (deals != null && deals.size() > 0) {
            itemSize++;
        }
        return itemSize;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_cities)
        RecyclerView rv_cities;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rv_cities.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
            placeAdapter = new PlaceAdapter(mActivity, popularPlaces);
            rv_cities.setAdapter(placeAdapter);
        }
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_popular)
        RecyclerView rv_popular;

        public PopularViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rv_popular.setLayoutManager(new GridLayoutManager(mActivity, 2));
            int spacingInPixels = mActivity.getResources().getDimensionPixelSize(R.dimen.spacing);
            rv_popular.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            popularAdapter = new PopularAdapter(mActivity, popularPlaces);
            rv_popular.setAdapter(popularAdapter);
        }
    }

    public class DealViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_deals)
        RecyclerView rv_deals;

        public DealViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rv_deals.setLayoutManager(new GridLayoutManager(mActivity, 2));
            int spacingInPixels = mActivity.getResources().getDimensionPixelSize(R.dimen.spacing);
            rv_deals.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            rv_deals.setNestedScrollingEnabled(false);
            dealAdapter = new DealAdapter(mActivity, deals);
            rv_deals.setAdapter(dealAdapter);
        }
    }


//    public void setItemSelectListener(RoomSearchListener roomSearchListener) {
//        this.roomSearchListener = roomSearchListener;
//    }
}
