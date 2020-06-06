package mastersunny.rooms.adapters;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.activities.RoomSearchActivity;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.R;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "PlaceAdapter";

    private List<DivisionResponseDto> divisions;
    private Activity mActivity;
    public static final int HEADER_ITEM = 1;
    public static final int MAIN_ITEM = 2;
    private ItemSelectListener clickListener;

    public PlaceAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setDivisions(List<DivisionResponseDto> divisions) {
        this.divisions = divisions;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item_layout, parent, false);
            return new MainHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER_ITEM:
                HeaderHolder headerHolder = (HeaderHolder) holder;
//                headerHolder.pulsator.start();
                headerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null)
                            clickListener.onItemSelect("", Constants.ACTION_GPS);
                    }
                });
                break;
            case MAIN_ITEM:
                MainHolder mainHolder = (MainHolder) holder;
                final DivisionResponseDto dto = divisions.get(position - 1);
                mainHolder.place_name.setText(dto.getName());
                if (dto.getImageUrl() != null) {
                    Glide.with(mActivity).load(ApiClient.BASE_URL + dto.getImageUrl())
                            .into(mainHolder.place_image);
                }
                mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, RoomSearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("PLACE_DTO", dto);
                        mActivity.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_ITEM;
        } else {
            return MAIN_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return divisions == null ? 1 : divisions.size() + 1;
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place_image)
        ImageView place_image;

        @BindView(R.id.place_name)
        TextView place_name;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.pulsator)
//        PulsatorLayout pulsator;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setClickListener(ItemSelectListener clickListener) {
        this.clickListener = clickListener;
    }
}
