package mastersunny.unitedclub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

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
    protected void bindView(View convertView, int listPosition, int viewType) {

    }
}
