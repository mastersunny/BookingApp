package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.activities.RoomDetailsActivity;
import mastersunny.unitedclub.models.RoomBookingDTO;
import mastersunny.unitedclub.models.RoomDTO;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.utils.Constants;

public class BookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RoomBookingDTO> roomBookingDTOS;
    private Activity mActivity;
    private String TAG = "BookingAdapter";

    public BookingAdapter(Activity mActivity, List<RoomBookingDTO> roomBookingDTOS) {
        this.mActivity = mActivity;
        this.roomBookingDTOS = roomBookingDTOS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RoomBookingDTO roomBookingDTO = roomBookingDTOS.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        mainHolder.title.setText(roomBookingDTO.getNoOfAccommodation() > 1 ? roomBookingDTO.getNoOfAccommodation()
                + " Seats Room" : roomBookingDTO.getNoOfAccommodation() + " Seat Room");
        mainHolder.address.setText(roomBookingDTO.getRoom().getAddress());
        mainHolder.room_cost.setText(roomBookingDTO.getRoomCost() + "");

        Constants.debugLog(TAG, roomBookingDTO.getRoom().getImages() + "");
        if (roomBookingDTO.getRoom().getImages() != null && roomBookingDTO.getRoom().getImages().size() > 0) {
            Constants.loadImage(mActivity, ApiClient.BASE_URL + ApiClient.APP_NAME + roomBookingDTO.getRoom().getImages().get(0).getImageUrl(),
                    mainHolder.room_image);
        }

        if (roomBookingDTO.getRoom().isFemaleFriendly()) {
            mainHolder.female_friendly_layout.setVisibility(View.VISIBLE);
        } else {
            mainHolder.female_friendly_layout.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RoomDetailsActivity.start(v.getContext(), roomDTO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomBookingDTOS == null ? 0 : roomBookingDTOS.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_image)
        ImageView room_image;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.room_cost)
        TextView room_cost;

        @BindView(R.id.booking_date)
        TextView booking_date;

        @BindView(R.id.female_friendly_layout)
        TextView female_friendly_layout;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
