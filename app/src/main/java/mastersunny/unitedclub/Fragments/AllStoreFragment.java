package mastersunny.unitedclub.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.PopularVerticalAdapter;
import mastersunny.unitedclub.Model.MoviesResponse;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class AllStoreFragment extends Fragment implements View.OnClickListener {

    public String TAG = "PopularStoreFragment";
    private Activity mActivity;
    private View view;
    private ArrayList<StoreDTO> storeDTOS;
    private RecyclerView popular_rv;
    private PopularVerticalAdapter popularVerticalAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            storeDTOS = new ArrayList<>();
            initLayout();
            loaData();
        }

        return view;
    }

    private void loaData() {
//        if (ApiClient.API_KEY.isEmpty()) {
//            Toast.makeText(mActivity, "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
//            return;
//        }
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        apiService.getTopRatedMovies(ApiClient.API_KEY).enqueue(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initLayout() {
        for (int i = 5; i < 15; i++) {
            StoreDTO storeDTO = new StoreDTO();
            if (i % 2 == 1) {
                storeDTO.setStoreName("MakeMyTrip");
            } else {
                storeDTO.setStoreName("Paytm");
            }
            storeDTO.setTotalOffer(i * 10);
            storeDTOS.add(storeDTO);
        }
        popular_rv = view.findViewById(R.id.most_used_rv);
        popular_rv.setHasFixedSize(true);
        popular_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        popularVerticalAdapter = new PopularVerticalAdapter(mActivity, storeDTOS);
        popular_rv.setAdapter(popularVerticalAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }
}
