package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mastersunny.unitedclub.models.TransactionDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "TransactionDetailsActivity";
    private ImageView store_image;
    private TextView store_name, offer_details, client_name, email,
            phone_number, transaction_date, total_amount;
    private TransactionDTO transactionDTO;
    private NestedScrollView nestedScrollView;
    private RelativeLayout hidden_toolbar, normal_toolbar;
    private ApiInterface apiInterface;
    private int transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_transaction_details);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getIntentData();
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        try {
            Constants.debugLog(TAG, "" + transactionId + " token " + Constants.accessToken);
            apiInterface.getTransactionDetails(transactionId, Constants.accessToken).enqueue(new Callback<TransactionDTO>() {
                @Override
                public void onResponse(Call<TransactionDTO> call, Response<TransactionDTO> response) {
                    Constants.debugLog(TAG, response + "");
                    if (response != null && response.isSuccessful() && response.body() != null) {
                        transactionDTO = response.body();
                        Constants.debugLog(TAG, transactionDTO.toString());
                        updateInfo();
                    }
                }

                @Override
                public void onFailure(Call<TransactionDTO> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    public static void start(Context context, int transactionId) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.TRANSACTION_ID, transactionId);
        context.startActivity(intent);
    }

    public void getIntentData() {
        transactionId = getIntent().getIntExtra(Constants.TRANSACTION_ID, 0);
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
        client_name = findViewById(R.id.client_name);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        transaction_date = findViewById(R.id.transaction_date);
        total_amount = findViewById(R.id.total_amount);

//        store_name.setTypeface(Constants.getRegularFace(this));
//        offer_details.setTypeface(Constants.getRegularFace(this));
//        description_text.setTypeface(Constants.getMediumFace(this));
//        description_details.setTypeface(Constants.getMediumFace(this));


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
        if (!TextUtils.isEmpty(transactionDTO.getStoreOfferDTO().getStoreDTO().getImageUrl())) {
            String imgUrl = ApiClient.BASE_URL + "" + transactionDTO.getStoreOfferDTO().getStoreDTO().getImageUrl();
            if (!isFinishing()) {
                Constants.loadImage(this, imgUrl, store_image);
            }
        }
        store_name.setText(transactionDTO.getStoreOfferDTO().getStoreDTO().getStoreName());
        offer_details.setText(transactionDTO.getStoreOfferDTO().getOffer());
        client_name.setText(transactionDTO.getUserDTO().getFirstName() + " " + transactionDTO.getUserDTO().getLastName());
        email.setText(transactionDTO.getUserDTO().getEmail());
        phone_number.setText(transactionDTO.getUserDTO().getPhoneNumber());
        transaction_date.setText(transactionDTO.getTransactionDate());
        total_amount.setText(transactionDTO.getAmount() + "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal_toolbar:
            case R.id.hidden_toolbar:
                TransactionDetailsActivity.this.finish();
                break;
        }
    }
}
