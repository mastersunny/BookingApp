package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.RoomSearchListener;
import mastersunny.rooms.models.DivisionResponseDto;

/**
 * Created by sunnychowdhury on 1/19/18.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "CityAdapter";

    private List<DivisionResponseDto> placeDTOS;
    private Activity mActivity;

    private RoomSearchListener roomSearchListener;

    public CityAdapter(Activity mActivity, List<DivisionResponseDto> placeDTOS) {
        this.mActivity = mActivity;
        this.placeDTOS = placeDTOS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        final DivisionResponseDto dto = placeDTOS.get(position);
        mainHolder.name.setText(dto.getName());
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomSearchListener != null) {
                    roomSearchListener.onDistrictSearch(dto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeDTOS == null ? 0 : placeDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemSelectListener(RoomSearchListener roomSearchListener) {
        this.roomSearchListener = roomSearchListener;
    }

}
