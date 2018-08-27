package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mastersunny.unitedclub.adapters.ExamAdapter;
import mastersunny.unitedclub.adapters.PopularVerticalAdapter;
import mastersunny.unitedclub.models.ExamDTO;
import mastersunny.unitedclub.models.StoreDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.rest.ApiClient;
import mastersunny.unitedclub.rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ExamAdapter examAdapter;


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
        examAdapter = new ExamAdapter(mActivity, examDTOS);
        nearby_rv.setAdapter(examAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
