package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.RoomSearchActivity;

public class GuestSelectFragment extends DialogFragment {

    public String TAG = "GuestSelectFragment";
    private Activity mActivity;
    private View view;
    private Unbinder unbinder;

    @BindView(R.id.tv_room_qty)
    TextView tv_room_qty;

    private int roomQty = 1;

    @BindView(R.id.tv_adult_qty)
    TextView tv_adult_qty;

    private int adultQty = 1;

    @BindView(R.id.tv_child_qty)
    TextView tv_child_qty;

    private int childQty = 0;

    public interface GuestSelectListener {
        void selectedGuestAndRoom(int roomQty, int adultQty, int childQty);
    }

    private GuestSelectListener guestSelectListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

        if (!(context instanceof RoomSearchActivity)) {
            return;
        }
        guestSelectListener = (RoomSearchActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.guest_select_fragment, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_room_decrease, R.id.btn_room_increase})
    public void handleRoom(View v) {
        switch (v.getId()) {
            case R.id.btn_room_decrease:
                if (roomQty > 1) {
                    roomQty--;
                }
                break;
            case R.id.btn_room_increase:
                roomQty++;
                break;
        }
        tv_room_qty.setText(roomQty + "");
    }

    @OnClick({R.id.btn_adult_decrease, R.id.btn_adult_increase})
    public void handleAdult(View v) {
        switch (v.getId()) {
            case R.id.btn_adult_decrease:
                if (adultQty > 1) {
                    adultQty--;
                }
                break;
            case R.id.btn_adult_increase:
                adultQty++;
                break;
        }
        tv_adult_qty.setText(adultQty + "");
    }

    @OnClick({R.id.btn_child_decrease, R.id.btn_child_increase})
    public void handleChild(View v) {
        switch (v.getId()) {
            case R.id.btn_child_decrease:
                if (childQty > 0) {
                    childQty--;
                }
                break;
            case R.id.btn_child_increase:
                childQty++;
                break;
        }
        tv_child_qty.setText(childQty + "");
    }

    @OnClick({R.id.btn_cancel, R.id.btn_apply})
    public void handleApplyCancel(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_apply:
                if (guestSelectListener != null) {
                    guestSelectListener.selectedGuestAndRoom(roomQty, adultQty, childQty);
                }
                dismiss();
                break;
        }
    }
}
