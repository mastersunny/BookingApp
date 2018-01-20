package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.MostUsedAdapter;
import mastersunny.unitedclub.R;


/**
 * Created by sunnychowdhury on 12/16/17.
 */

public class MostUsedFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;
    private View view;
    private RecyclerView most_used_rv;
    private SearchView searchView;
    private TextView no_client_message;
    private ArrayList<String> list;
    private MostUsedAdapter mostUsedAdapter;

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
            initLayout();
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initLayout() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("ddfhduifhuids " + i);
        }

        most_used_rv = view.findViewById(R.id.most_used_rv);
        most_used_rv.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mostUsedAdapter = new MostUsedAdapter(mActivity, list);
        most_used_rv.setAdapter(mostUsedAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
