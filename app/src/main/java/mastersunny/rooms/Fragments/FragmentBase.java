package mastersunny.rooms.Fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mastersunny.rooms.listeners.FragmentInterface;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;

/**
 * Created by ASUS on 2/24/2018.
 */

public abstract class FragmentBase extends Fragment implements FragmentInterface {

    protected View view;
    protected SwipeRefreshLayout swipeRefresh;
    protected Handler handler = new Handler();
    protected boolean firstRequest = false;
    protected ApiInterface apiInterface;

    protected void refreshHandler() {
        handler.postDelayed(runnable, Constants.REQUEST_TIMEOUT);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Constants.debugLog("TAG", "" + swipeRefresh);
            if (swipeRefresh != null) {
                swipeRefresh.setRefreshing(false);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            swipeRefresh = view.findViewById(R.id.swipeRefresh);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    firstRequest = false;
                    sendInitialRequest();
                }
            });
            onCreate();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
