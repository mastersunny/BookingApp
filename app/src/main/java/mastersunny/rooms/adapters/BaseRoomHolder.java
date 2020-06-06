package mastersunny.rooms.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseRoomHolder extends RecyclerView.ViewHolder {

    public BaseRoomHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
