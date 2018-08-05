package mastersunny.unitedclub.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.models.RoomDTO;

public class RecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity mActivity;
    private List<RoomDTO> roomDTOList = Collections.emptyList();

    public RecommendedAdapter(Activity mActivity, List<RoomDTO> roomDTOList) {
        this.mActivity = mActivity;
        this.roomDTOList = roomDTOList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RoomDTO roomDTO = roomDTOList.get(position);

    }

    @Override
    public int getItemCount() {
        return roomDTOList == null ? 0 : roomDTOList.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
