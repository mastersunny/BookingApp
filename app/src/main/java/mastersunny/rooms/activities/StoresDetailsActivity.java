package mastersunny.rooms.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mastersunny.rooms.adapters.StoreOfferAdapterDetails;
import mastersunny.rooms.models.OfferDTO;
import mastersunny.rooms.models.StoreDTO;
import mastersunny.rooms.R;
import mastersunny.rooms.rest.ApiClient;
import mastersunny.rooms.rest.ApiInterface;
import mastersunny.rooms.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoresDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "StoresDetailsActivity";
    private StoreOfferAdapterDetails storeOfferAdapter;
    private ArrayList<OfferDTO> offerDTOS;
    private RecyclerView offer_rv;
    private ApiInterface apiService;
    private StoreDTO storeDTO;
    private TextView store_name;

    public static void start(Context context, StoreDTO storeDTO) {
        Intent intent = new Intent(context, StoresDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.STORE_DTO, storeDTO);
        context.startActivity(intent);
    }

    private void getIntentData() {
        storeDTO = (StoreDTO) getIntent().getSerializableExtra(Constants.STORE_DTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stores_details);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        offerDTOS = new ArrayList<>();

        getIntentData();
        initLayout();
        loadData();
        store_name.setText(storeDTO.getStoreName());
    }

    private void initLayout() {
        offer_rv = findViewById(R.id.offer_rv);
        offer_rv.setHasFixedSize(true);
        offer_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new StoreOfferAdapterDetails(StoresDetailsActivity.this, offerDTOS, storeDTO);
        offer_rv.setAdapter(storeOfferAdapter);

        findViewById(R.id.back_button).setOnClickListener(this);
        store_name = findViewById(R.id.store_name);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {
        try {
            Constants.debugLog(TAG, "accessToken: " + Constants.accessToken);
            apiService.getStoreById(storeDTO.getStoreId(), Constants.accessToken).enqueue(new Callback<StoreDTO>() {
                @Override
                public void onResponse(Call<StoreDTO> call, Response<StoreDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Constants.debugLog(TAG, "StoreDTO: " + response.body());
                        storeDTO = response.body();
                        store_name.setText(storeDTO.getStoreName());
                        if (storeOfferAdapter != null) {
                            storeOfferAdapter.setStoreDTO(storeDTO);
                            storeOfferAdapter.notifyItemChanged(0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<StoreDTO> call, Throwable t) {
                    Constants.debugLog(TAG, "" + t.getMessage());
                }
            });

            apiService.getStoreOffers(storeDTO.getStoreId(), Constants.accessToken)
                    .enqueue(new Callback<List<OfferDTO>>() {
                        @Override
                        public void onResponse(Call<List<OfferDTO>> call, Response<List<OfferDTO>> response) {
                            Constants.debugLog(TAG, "" + response);
                            if (response.isSuccessful() && response.body() != null) {
                                Constants.debugLog(TAG, "OfferDTO: " + response.body());
                                offerDTOS.addAll(response.body());
                                if (storeOfferAdapter != null) {
                                    storeOfferAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OfferDTO>> call, Throwable t) {
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
