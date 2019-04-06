package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.listeners.ClickListener;
import mastersunny.rooms.R;
import mastersunny.rooms.models.PlaceDTO;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "PlaceAdapter";

    private List<PlaceDTO> placeDTOS;
    private Activity mActivity;
    public static final int HEADER_ITEM = 1;
    public static final int MAIN_ITEM = 2;
    private ClickListener clickListener;

    public PlaceAdapter(Activity mActivity, List<PlaceDTO> placeDTOS) {
        this.mActivity = mActivity;
        this.placeDTOS = placeDTOS;
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
                headerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null)
                            clickListener.click();
                    }
                });
                break;
            case MAIN_ITEM:
                MainHolder mainHolder = (MainHolder) holder;
                final PlaceDTO dto = placeDTOS.get(position - 1);
                mainHolder.place_name.setText(dto.getName());
                int res = mActivity.getResources().getIdentifier(mActivity.getPackageName()
                        + ":drawable/" + dto.getImageUrl(), null, null);
                Glide.with(mActivity).load(res).into(mainHolder.place_image);
                mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        RoomListActivity.start(v.getContext(), dto.getName(), (ArrayList<ExamDTO>) dto.getExams(), SearchType.TYPE_PLACE.getStatus());
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
        return placeDTOS == null ? 1 : placeDTOS.size() + 1;
//        return placeDTOS == null ? 0 : placeDTOS.size();
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
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
