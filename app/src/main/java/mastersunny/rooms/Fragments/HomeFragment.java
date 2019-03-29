package mastersunny.rooms.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import mastersunny.rooms.R;
import mastersunny.rooms.models.ExamDTO;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class HomeFragment extends Fragment {

    public String TAG = "HomeFragment";
    private Activity mActivity;
    private View view;
    private ApiInterface apiInterface;
    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
            view = inflater.inflate(R.layout.home_fragment_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            apiInterface = ApiClient.createService(getActivity(), ApiInterface.class);

            examDTOS = new ArrayList<>();
            initLayout();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initLayout() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Admission Test");


        nearby_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

    }

    private void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(context)
                    .setMessage(context.getResources().getString(R.string.permission_location))
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    Constants.PERMISSION_LOCATION);
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSION_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_LOCATION) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                RoomListActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
            }
        }
    }

    private void loadData() {
        apiInterface.getExams(0, 10, "exam_date,asc").enqueue(new Callback<List<ExamDTO>>() {
            @Override
            public void onResponse(Call<List<ExamDTO>> call, Response<List<ExamDTO>> response) {

                Constants.debugLog(TAG, response + "");

                if (response.isSuccessful()) {
                    Constants.debugLog(TAG, response.body() + "");
                    examDTOS.clear();
                    examDTOS.addAll(response.body());
                    notifyPlaceAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<ExamDTO>> call, Throwable t) {
                Constants.debugLog(TAG, t.getMessage());
            }
        });
    }

    private void notifyPlaceAdapter() {

    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    RoomListActivity.start(mActivity, SearchType.TYPE_NEARBY.getStatus());
        } else {
            requestPermission(mActivity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
