package mastersunny.rooms.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import mastersunny.rooms.R;
import mastersunny.rooms.models.OfferDTO;

public class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;
    private List<OfferDTO> offerDTOList = Collections.emptyList();

    public OfferAdapter(Activity mActivity, List<OfferDTO> roomDTOList) {
        this.mActivity = mActivity;
        this.offerDTOList = roomDTOList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OfferDTO roomDTO = offerDTOList.get(position);

    }

    @Override
    public int getItemCount() {
        return offerDTOList == null ? 0 : offerDTOList.size();
    }

    static class MainHolder extends RecyclerView.ViewHolder {

        public MainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
