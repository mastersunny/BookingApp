package mastersunny.unitedclub.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.CommonInerface;
import mastersunny.unitedclub.utils.Constants;

public class ItemDetailsActivity extends AppCompatActivity implements CommonInerface, View.OnClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        getIntentData();
        initLayout();
        loadData();
    }


    @Override
    public void getIntentData() {
        movieId = getIntent().getIntExtra(Constants.ITEM_DTO, 0);
    }

    @Override
    public void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                break;
        }
    }
}
