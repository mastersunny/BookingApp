package mastersunny.rooms.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mastersunny.rooms.R;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.DivisionResponseDto;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "PopularAdapter";

    private List<DivisionResponseDto> placeDTOS;
    private Activity mActivity;
    private ItemSelectListener clickListener;

    public ImageAdapter(Activity mActivity, List<DivisionResponseDto> placeDTOS) {
        this.mActivity = mActivity;
        this.placeDTOS = placeDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final DivisionResponseDto dto = placeDTOS.get(position);
//        mainHolder.place_name.setText(dto.getName());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        HotelListActivity.start(v.getContext(), dto.getName(), (ArrayList<ExamDTO>) dto.getExams(), SearchType.TYPE_PLACE.getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeDTOS == null ? 0 : placeDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.place_image)
//        ImageView place_image;
//
//        @BindView(R.id.place_name)
//        TextView place_name;

        public MainHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public void setClickListener(ItemSelectListener clickListener) {
        this.clickListener = clickListener;
    }
}
