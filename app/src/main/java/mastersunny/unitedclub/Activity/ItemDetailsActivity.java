package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.utils.CommonInerface;
import mastersunny.unitedclub.utils.Constants;

public class ItemDetailsActivity extends AppCompatActivity implements CommonInerface, View.OnClickListener {

    private Toolbar toolbar;
    public String TAG = "ItemDetailsActivity";
    private long storeId;
    private ImageView store_image;
    private TextView store_name, offer_details;
    private RelativeLayout offer_details_layout;
    private EditText total_amount;
    private Button submit;
    private StoreOfferDTO storeOfferDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_item_details);

        getIntentData();
        initLayout();
        loadData();
    }

    public static void start(Context context, StoreDTO storeDTO) {
        Intent intent = new Intent(context, ItemDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.STORE_DTO, storeDTO);
        context.startActivity(intent);
    }


    @Override
    public void getIntentData() {
        storeId = getIntent().getLongExtra(Constants.STORE_DTO, 0);
    }

    @Override
    public void initLayout() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        store_image = findViewById(R.id.store_image);
        store_name = findViewById(R.id.store_name);
        offer_details_layout = findViewById(R.id.offer_details_layout);
        total_amount = findViewById(R.id.total_amount);
        offer_details = findViewById(R.id.offer_details);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    private void updateInfo() {
        Constants.loadImage(this, storeOfferDTO.getStoreDTO().getBannerImg(), store_image);
        store_name.setText(storeOfferDTO.getStoreDTO().getStoreName());
        offer_details.setText(storeOfferDTO.getOffer());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submitPurchase();
                break;
        }
    }

    private void submitPurchase() {
        double amount = 0;
        try {
            amount = Double.valueOf(total_amount.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
