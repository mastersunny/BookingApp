package mastersunny.unitedclub.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mastersunny.unitedclub.Listener.FragmentInterface;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;

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
