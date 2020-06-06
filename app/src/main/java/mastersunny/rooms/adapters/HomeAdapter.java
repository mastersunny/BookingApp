package mastersunny.rooms.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.models.BannerResponseDto;
import mastersunny.rooms.models.DistrictResponseDto;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.utils.Constants;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "HomeAdapter";

    private List<BannerResponseDto> banners;

    PlaceAdapter placeAdapter;
    private List<DivisionResponseDto> divisions;

    PopularAdapter popularAdapter;
    private List<DistrictResponseDto> popularPlaces;

    DealAdapter dealAdapter;
    private List<DivisionResponseDto> deals;

    private Activity mActivity;

    RecyclerView.RecycledViewPool viewPool;

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CITY = 2;
    public static final int TYPE_POPULAR = 3;
    public static final int TYPE_DEAL = 4;

    public void setDivisions(List<DivisionResponseDto> divisions) {
        this.divisions = divisions;
        notifyDataSetChanged();
    }

    public void setBanners(List<BannerResponseDto> banners) {
        this.banners = banners;
        notifyDataSetChanged();
    }

    public void setPopularPlaces(List<DistrictResponseDto> popularPlaces) {
        this.popularPlaces = popularPlaces;
        notifyDataSetChanged();
    }

    public void setDeals(List<DivisionResponseDto> deals) {
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
            CityViewHolder holder = new CityViewHolder(view);
//            holder.rv_cities.setRecycledViewPool(viewPool);
            return holder;
        } else if (viewType == TYPE_POPULAR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_item_layout, parent, false);
            PopularViewHolder holder = new PopularViewHolder(view);
            holder.rv_popular.setRecycledViewPool(viewPool);
            return holder;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_deal_item_layout, parent, false);
            DealViewHolder holder = new DealViewHolder(view);
            holder.rv_deals.setRecycledViewPool(viewPool);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Constants.debugLog(TAG, "position " + position);
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.search_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, RoomSearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("DIVISION_DTOS", (Serializable) divisions);
                    mActivity.startActivity(intent);
                }
            });
            headerViewHolder.sliderAdapter.renewItems(banners);
            headerViewHolder.sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            headerViewHolder.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            headerViewHolder.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
            headerViewHolder.sliderView.setIndicatorSelectedColor(Color.WHITE);
            headerViewHolder.sliderView.setIndicatorUnselectedColor(Color.GRAY);
            headerViewHolder.sliderView.setScrollTimeInSec(4);
            headerViewHolder.sliderView.startAutoCycle();

        } else if (holder instanceof CityViewHolder) {
            placeAdapter.setDivisions(divisions);
        } else if (holder instanceof PopularViewHolder) {
            PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
            popularAdapter.notifyDataSetChanged();
        } else if (holder instanceof DealViewHolder) {
            DealViewHolder dealViewHolder = (DealViewHolder) holder;
            dealAdapter.notifyDataSetChanged();
        }
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

        if (divisions != null && divisions.size() > 0)
            itemSize++;
        if (popularPlaces != null && popularPlaces.size() > 0)
            itemSize++;
        if (deals != null && deals.size() > 0) {
            itemSize++;
        }
        return itemSize;
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_layout)
        RelativeLayout search_layout;

        @BindView(R.id.slider_view)
        SliderView sliderView;

        SliderAdapter sliderAdapter;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            sliderAdapter = new SliderAdapter(mActivity);
            sliderView.setSliderAdapter(sliderAdapter);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_cities)
        RecyclerView rv_cities;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            layoutManager.setInitialPrefetchItemCount(4);
            rv_cities.setLayoutManager(layoutManager);
            placeAdapter = new PlaceAdapter(mActivity);
            rv_cities.setAdapter(placeAdapter);
        }
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_popular)
        RecyclerView rv_popular;

        public PopularViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
            layoutManager.setInitialPrefetchItemCount(4);
            rv_popular.setLayoutManager(layoutManager);
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

            GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
            layoutManager.setInitialPrefetchItemCount(4);
            rv_deals.setLayoutManager(layoutManager);
            int spacingInPixels = mActivity.getResources().getDimensionPixelSize(R.dimen.spacing);
            rv_deals.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
            dealAdapter = new DealAdapter(mActivity, deals);
            rv_deals.setAdapter(dealAdapter);
        }
    }


//    public void setItemSelectListener(RoomSearchListener roomSearchListener) {
//        this.roomSearchListener = roomSearchListener;
//    }
}
