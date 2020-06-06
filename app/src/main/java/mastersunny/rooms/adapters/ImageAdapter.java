package mastersunny.rooms.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.DivisionResponseDto;
import mastersunny.rooms.models.RoomImageDTO;
import mastersunny.rooms.rest.ApiClient;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "ImageAdapter";

    private List<RoomImageDTO> imageDTOS;
    private Activity mActivity;
    private ItemSelectListener clickListener;

    public ImageAdapter(Activity mActivity, List<RoomImageDTO> imageDTOS) {
        this.mActivity = mActivity;
        this.imageDTOS = imageDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final RoomImageDTO dto = imageDTOS.get(position);
        Glide.with(mActivity).load(ApiClient.BASE_URL + dto.getImageUrl())
                .placeholder(R.drawable.ic_image)
                .into(mainHolder.room_image);
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        HotelListActivity.start(v.getContext(), dto.getName(), (ArrayList<ExamDTO>) dto.getExams(), SearchType.TYPE_PLACE.getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDTOS == null ? 0 : imageDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_image)
        ImageView room_image;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setClickListener(ItemSelectListener clickListener) {
        this.clickListener = clickListener;
    }
}
