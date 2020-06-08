package mastersunny.rooms.adapters;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.listeners.ItemSelectListener;
import mastersunny.rooms.models.HotelResponseDto;
import mastersunny.rooms.models.RoomDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.utils.Constants;

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotelResponseDto> hotelResponseDtos;
    private Activity mActivity;
    private String TAG = "HotelAdapter";
    NumberFormat formatter = new DecimalFormat("BDT #0");
    private ItemSelectListener itemSelectListener;
    Random random = new Random();

    public HotelAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item_layout,
                parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HotelResponseDto hotelResponseDto = hotelResponseDtos.get(position);
        MainHolder mainHolder = (MainHolder) holder;
        mainHolder.title.setText(hotelResponseDto.getTitle());
        mainHolder.address.setText(hotelResponseDto.getAddress());
        mainHolder.price.setText(formatter.format(hotelResponseDto.getRoomDTOS().get(0).getRoomCost()));

        if (hotelResponseDto.getImages()!=null && hotelResponseDto.getImages().size()>0) {
            String url = ApiClient.BASE_URL + "file/get?filePath="+ hotelResponseDto.getImages()
                    .get(random.nextInt(hotelResponseDto.getImages().size()-1)).getImageUrl();
            Glide.with(mActivity).load(url)
                    .placeholder(R.drawable.ic_image)
                    .into(mainHolder.hotel_image);
        }
        mainHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.debugLog(TAG, "hotel " + hotelResponseDto.toString());
                if (itemSelectListener != null) {
                    itemSelectListener.onItemSelect(hotelResponseDto, Constants.ACTION_DETAILS);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelResponseDtos == null ? 0 : hotelResponseDtos.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.hotel_image)
        ImageView hotel_image;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.price)
        TextView price;

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void add(RoomDTO roomDTO, int position) {
        notifyItemInserted(position);
    }

    public void setData(List<HotelResponseDto> hotelResponseDtos) {
        this.hotelResponseDtos = hotelResponseDtos;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }

    public void setItemSelectListener(ItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }
}
