package mastersunny.unitedclub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;

import java.util.ArrayList;

import mastersunny.unitedclub.R;

/**
 * Created by ASUS on 1/30/2018.
 */

public class AutoScrollAdapter extends LoopingPagerAdapter<Integer> {

    public AutoScrollAdapter(Context context, ArrayList<Integer> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    @Override
    protected View inflateView(int viewType, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.auto_scroll_layout, null);
    }

    @Override
    protected void bindView(View holder, int listPosition, int viewType) {
        switch (listPosition) {
            case 0:
                int id1 = context.getResources().getIdentifier("grocery_bg", "drawable", context.getPackageName());
                ImageView imageView = holder.findViewById(R.id.store_image);
                imageView.setImageResource(id1);
                break;
            case 1:
                int id2 = context.getResources().getIdentifier("winzone_bg", "drawable", context.getPackageName());
                ImageView img2 = holder.findViewById(R.id.store_image);
                img2.setImageResource(id2);
                break;
        }
    }
}
