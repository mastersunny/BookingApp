package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.HotelResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

public class RoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomDTO> roomDTOS;
    private Activity mActivity;
    private String TAG = "RoomAdapter";
    NumberFormat formatter = new DecimalFormat("BDT #0");
    private ItemSelectListener itemSelectListener;

    public RoomAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item_layout,
                parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RoomDTO roomDTO = roomDTOS.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        mainHolder.title.setText(roomDTO.getTitle());
        mainHolder.price.setText(formatter.format(roomDTO.getRoomCost()));
        if (roomDTO.getWifiAvailable() == 1
                || roomDTO.getTvAvailable() == 1
                || roomDTO.getAcAvailable() == 1) {
            mainHolder.advantage_layout.setVisibility(View.VISIBLE);
            if (roomDTO.getWifiAvailable() == 1) {
                mainHolder.wifi_layout.setVisibility(View.VISIBLE);
            } else {
                mainHolder.wifi_layout.setVisibility(View.GONE);
            }

            if (roomDTO.getTvAvailable() == 1) {
                mainHolder.tv_layout.setVisibility(View.VISIBLE);
            } else {
                mainHolder.tv_layout.setVisibility(View.GONE);
            }

            if (roomDTO.getAcAvailable() == 1) {
                mainHolder.ac_layout.setVisibility(View.VISIBLE);
            } else {
                mainHolder.ac_layout.setVisibility(View.GONE);
            }
        }
        if (roomDTO.getLunchAvailable() == 1) {
            mainHolder.lunch_layout.setVisibility(View.VISIBLE);
        } else {
            mainHolder.lunch_layout.setVisibility(View.GONE);
        }

        if (roomDTO.getTransportAvailable() == 1) {
            mainHolder.transport_layout.setVisibility(View.VISIBLE);
        } else {
            mainHolder.transport_layout.setVisibility(View.GONE);
        }
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemSelectListener != null) {
                    itemSelectListener.onItemSelect(roomDTO, Constants.ACTION_DETAILS);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomDTOS == null ? 0 : roomDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.wifi_layout)
        LinearLayout wifi_layout;

        @BindView(R.id.tv_layout)
        LinearLayout tv_layout;

        @BindView(R.id.ac_layout)
        LinearLayout ac_layout;

        @BindView(R.id.advantage_layout)
        LinearLayout advantage_layout;

        @BindView(R.id.lunch_layout)
        LinearLayout lunch_layout;

        @BindView(R.id.transport_layout)
        LinearLayout transport_layout;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setData(List<RoomDTO> roomDTOS) {
        this.roomDTOS = roomDTOS;
        notifyDataSetChanged();
    }

    public void setItemSelectListener(ItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }

}
