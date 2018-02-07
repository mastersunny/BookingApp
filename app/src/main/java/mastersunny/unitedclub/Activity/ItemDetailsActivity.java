package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
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

    public String TAG = "ItemDetailsActivity";
    private long storeId;
    private ImageView store_image;
    private TextView store_name, offer_details, description_text, description_details;
    private EditText total_amount;
    private Button submit;
    private StoreOfferDTO storeOfferDTO;
    private NestedScrollView nestedScrollView;
    private RelativeLayout hidden_toolbar, normal_toolbar;

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
        normal_toolbar = findViewById(R.id.normal_toolbar);
        normal_toolbar.setOnClickListener(this);
        hidden_toolbar = findViewById(R.id.hidden_toolbar);
        hidden_toolbar.setOnClickListener(this);
        store_image = findViewById(R.id.store_image);
        store_name = findViewById(R.id.store_name);
        total_amount = findViewById(R.id.total_amount);
        offer_details = findViewById(R.id.offer_details);
        description_text = findViewById(R.id.description_text);
        description_details = findViewById(R.id.description_details);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        store_name.setTypeface(Constants.getRegularFace(this));
        offer_details.setTypeface(Constants.getRegularFace(this));
        description_text.setTypeface(Constants.getMediumFace(this));
        description_details.setTypeface(Constants.getMediumFace(this));


        nestedScrollView = findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                    hidden_toolbar.setVisibility(View.VISIBLE);
                }
                if (scrollY <= 100) {
                    Log.i(TAG, "Scroll UP");
                    hidden_toolbar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void loadData() {

    }

    private void updateInfo() {
        Constants.loadImage(this, storeOfferDTO.getStoreDTO().getImageUrl(), store_image);
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
            case R.id.normal_toolbar:
            case R.id.hidden_toolbar:
                ItemDetailsActivity.this.finish();
                break;
        }
    }

    private void submitPurchase() {
        Constants.showPaymentDialog(this);
        double amount = 0;
        try {
            amount = Double.valueOf(total_amount.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
