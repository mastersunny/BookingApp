package mastersunny.rooms.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.DistrictResponseDto;
import mastersunny.rooms.rest.ApiClient;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "PopularAdapter";

    private List<DistrictResponseDto> placeDTOS;
    private Activity mActivity;
    private ItemSelectListener clickListener;

    public PopularAdapter(Activity mActivity, List<DistrictResponseDto> placeDTOS) {
        this.mActivity = mActivity;
        this.placeDTOS = placeDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poplar_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final DistrictResponseDto dto = placeDTOS.get(position);
        mainHolder.place_name.setText(dto.getName());
        if (!TextUtils.isEmpty(dto.getImageUrl())) {
            Glide.with(mActivity).load(ApiClient.BASE_URL + dto.getImageUrl())
                    .placeholder(R.drawable.ic_image)
                    .into(mainHolder.place_image);
        }
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return placeDTOS == null ? 0 : placeDTOS.size() > 4 ? 4 : placeDTOS.size();
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
}
