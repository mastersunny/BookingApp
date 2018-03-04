package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mastersunny.unitedclub.Model.RestModel;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "ItemDetailsActivity";
    private ImageView store_image;
    private TextView store_name, offer_details, description_text, description_details;
    private EditText total_amount;
    private Button submit;
    private StoreOfferDTO storeOfferDTO;
    private NestedScrollView nestedScrollView;
    private RelativeLayout hidden_toolbar, normal_toolbar;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_item_details);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getIntentData();
        initLayout();
        updateInfo();
    }

    public static void start(Context context, StoreOfferDTO storeOfferDTO) {
        Intent intent = new Intent(context, ItemDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.STORE_OFFER_DTO, storeOfferDTO);
        context.startActivity(intent);
    }

    public void getIntentData() {
        storeOfferDTO = (StoreOfferDTO) getIntent().getSerializableExtra(Constants.STORE_OFFER_DTO);
    }

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

    private void updateInfo() {
        if (!TextUtils.isEmpty(storeOfferDTO.getStoreDTO().getImageUrl())) {
            String imgUrl = ApiClient.BASE_URL + "" + storeOfferDTO.getStoreDTO().getImageUrl();
            Constants.loadImage(this, imgUrl, store_image);
        }
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
        double amount = 0;
        try {
            amount = Double.valueOf(total_amount.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (amount == 0) {
            Constants.showDialog(this, "Please specify a valid amount");
            return;
        }

        try {
//            apiInterface.submitTransaction(storeOfferDTO.getOfferId(), amount, Constants.accessToken).enqueue(new Callback<RestModel>() {
//                @Override
//                public void onResponse(Call<RestModel> call, Response<RestModel> response) {
//                    if (response != null && response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
//                        Constants.showDialog(ItemDetailsActivity.this, "Payment has be submitted successfully");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<RestModel> call, Throwable t) {
//                    Constants.showDialog(ItemDetailsActivity.this, "Cannot process transaction at this moment");
//                    Constants.debugLog(TAG, "" + t.getMessage());
//                }
//            });

        } catch (Exception e) {
            Constants.showDialog(ItemDetailsActivity.this, "Cannot process transaction at this moment");
            Constants.debugLog(TAG, "" + e.getMessage());
        }
    }
}
