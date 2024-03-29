package mastersunny.rooms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;

import java.util.ArrayList;

import mastersunny.rooms.activities.StoresDetailsActivity;
import mastersunny.rooms.models.SliderDTO;
import mastersunny.rooms.models.StoreDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

/**
 * Created by ASUS on 1/30/2018.
 */

public class AutoScrollAdapter extends LoopingPagerAdapter<SliderDTO> {

    private ArrayList<SliderDTO> autoScrollList;

    public AutoScrollAdapter(Context context, ArrayList<SliderDTO> autoScrollList, boolean isInfinite) {
        super(context, autoScrollList, isInfinite);
        this.autoScrollList = autoScrollList;
    }

    @Override
    protected View inflateView(int viewType, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.home_banner_layout, null);
    }

    @Override
    protected void bindView(View holder, int listPosition, int viewType) {
        if (autoScrollList != null) {
            final SliderDTO sliderDTO = autoScrollList.get(listPosition);
            ImageView imageView = holder.findViewById(R.id.image);
            String imgUrl = ApiClient.BASE_URL + "" + sliderDTO.getImageUrl();
            Constants.loadImage(context, imgUrl, imageView);

            holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setStoreId(sliderDTO.getStoreId());
                    StoresDetailsActivity.start(view.getContext(), storeDTO);
                }
            });
        }
    }
}
