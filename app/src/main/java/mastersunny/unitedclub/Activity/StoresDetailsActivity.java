package mastersunny.unitedclub.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.Adapter.StoreOfferAdapter;
import mastersunny.unitedclub.Model.StoreDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "StoresDetailsActivity";
    private TextView total_offer;
    private ImageView store_image;
    private TextView store_name;
    private StoreOfferAdapter storeOfferAdapter;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private RecyclerView offer_rv;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private ApiInterface apiService;
    private StoreDTO storeDTO;

    public static void start(Context context, StoreDTO storeDTO) {
        Intent intent = new Intent(context, StoresDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.STORE_DTO, storeDTO);
        context.startActivity(intent);
    }

    private void getIntentData() {
        storeDTO = (StoreDTO) getIntent().getSerializableExtra(Constants.STORE_DTO);
    }

    private void updateStoreInfo() {
        store_name.setText(storeDTO.getStoreName());
        String imgUrl = ApiClient.BASE_URL + "" + storeDTO.getImageUrl();
        Constants.loadImage(this, imgUrl, store_image);
        total_offer.setText(storeDTO.getTotalOffer() + " Offers");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stores_details);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        storeOfferDTOS = new ArrayList<>();

        getIntentData();
        initLayout();
        loadData();
    }

    private void initLayout() {
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(StoresDetailsActivity.this, Float.toString(v), Toast.LENGTH_LONG).show();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.back_button).setOnClickListener(this);

        total_offer = findViewById(R.id.total_offer);
        store_image = findViewById(R.id.store_image);
        store_name = findViewById(R.id.store_name);

        updateStoreInfo();

        offer_rv = findViewById(R.id.offer_rv);
        offer_rv.setHasFixedSize(true);
        offer_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapter(StoresDetailsActivity.this, storeOfferDTOS);
        offer_rv.setAdapter(storeOfferAdapter);

        findViewById(R.id.follow_layout).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {
        try {
            Constants.debugLog(TAG, "accessToken: " + Constants.accessToken);
            if (TextUtils.isEmpty(storeDTO.getStoreName())) {
                apiService.getStoreById(storeDTO.getStoreId(), Constants.accessToken).enqueue(new Callback<StoreDTO>() {
                    @Override
                    public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Constants.debugLog(TAG, "StoreDTO: " + response.body());
                            storeDTO = response.body();
                            updateStoreInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreDTO> call, Throwable t) {
                        Constants.debugLog(TAG, "" + t.getMessage());
                    }
                });
            }

            apiService.getStoreOffers(storeDTO.getStoreId(), Constants.accessToken)
                    .enqueue(new Callback<List<StoreOfferDTO>>() {
                        @Override
                        public void onResponse(Call<List<StoreOfferDTO>> call, Response<List<StoreOfferDTO>> response) {
                            Constants.debugLog(TAG, "" + response);
                            if (response.isSuccessful() && response.body() != null) {
                                Constants.debugLog(TAG, "StoreOfferDTO: " + response.body());
                                storeOfferDTOS.addAll(response.body());
                                if (storeOfferAdapter != null) {
                                    storeOfferAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<StoreOfferDTO>> call, Throwable t) {
                            Constants.debugLog(TAG, "" + t.getMessage());
                        }
                    });
        } catch (Exception e) {
            Constants.debugLog(TAG, "" + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                StoresDetailsActivity.this.finish();
                break;
            case R.id.follow_layout:
                Toast.makeText(StoresDetailsActivity.this, "Followed successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
