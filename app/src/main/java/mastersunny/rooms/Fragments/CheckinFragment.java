package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.R;
import mastersunny.rooms.activities.DateRoomSelectActivity;
import mastersunny.rooms.listeners.DateSelectionListener;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;

public class CheckinFragment extends Fragment {

    public String TAG = "CheckinFragment";
    private Activity mActivity;
    private View view;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ApiInterface apiInterface;
    private Unbinder unbinder;
    private DateSelectionListener dateSelectionListener;

    @BindView(R.id.calender_view)
    CalendarView calender_view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();

        if (!(context instanceof DateRoomSelectActivity)) {
            return;
        }

        dateSelectionListener = (DateRoomSelectActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.checkin_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);

            initLayout();
        }

        return view;
    }

    private void initLayout() {
        calender_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                dateSelectionListener.startDate(Constants.calculateDate(year, month, dayOfMonth));
//                Constants.startDate = year + "-" + String.format("%02d", month, Locale.ENGLISH) + "-" + String.format("%02d", dayOfMonth, Locale.ENGLISH);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
