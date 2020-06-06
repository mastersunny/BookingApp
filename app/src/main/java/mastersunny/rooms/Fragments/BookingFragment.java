package mastersunny.rooms.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.rooms.models.ExamDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class BookingFragment extends Fragment {

    public String TAG = "BookingFragment";
    private Activity mActivity;
    private View view;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private ApiInterface apiInterface;
    private Unbinder unbinder;

    @BindView(R.id.nearby_rv)
    RecyclerView nearby_rv;
    private List<ExamDTO> examDTOS;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.booking_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);

//            roomDTOList = new ArrayList<>();
//            offerDTOS = new ArrayList<>();
            examDTOS = new ArrayList<>();

            initLayout();
        }

        return view;
    }

    private void initLayout() {
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });

        nearby_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
