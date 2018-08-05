package mastersunny.unitedclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;

import java.util.ArrayList;

import mastersunny.unitedclub.activities.StoresDetailsActivity;
import mastersunny.unitedclub.models.SliderDTO;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.utils.Constants;

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
        return LayoutInflater.from(context).inflate(R.layout.auto_scroll_layout, null);
    }

    @Override
    protected void bindView(View holder, int listPosition, int viewType) {
        if (autoScrollList != null) {
            final SliderDTO sliderDTO = autoScrollList.get(listPosition);
            ImageView imageView = holder.findViewById(R.id.store_image);
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
