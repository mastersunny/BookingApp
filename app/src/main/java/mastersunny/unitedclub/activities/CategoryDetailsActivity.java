package mastersunny.unitedclub.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mastersunny.unitedclub.adapters.CategoryOfferAdapterDetails;
import mastersunny.unitedclub.Model.CategoryDTO;
import mastersunny.unitedclub.Model.StoreOfferDTO;
import mastersunny.unitedclub.R;
import mastersunny.unitedclub.Rest.ApiClient;
import mastersunny.unitedclub.Rest.ApiInterface;
import mastersunny.unitedclub.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "CategoryDetailsActivity";
    private CategoryOfferAdapterDetails storeOfferAdapter;
    private ArrayList<StoreOfferDTO> storeOfferDTOS;
    private RecyclerView offer_rv;
    private ApiInterface apiInterface;
    private CategoryDTO categoryDTO;
    private TextView store_name;

    public static void start(Context context, CategoryDTO categoryDTO) {
        Intent intent = new Intent(context, CategoryDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constants.CATEGORY_DTO, categoryDTO);
        context.startActivity(intent);
    }

    private void getIntentData() {
        categoryDTO = (CategoryDTO) getIntent().getSerializableExtra(Constants.CATEGORY_DTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stores_details);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        storeOfferDTOS = new ArrayList<>();

        getIntentData();
        initLayout();
        loadData();
        Constants.debugLog(TAG, "CATEGORY DTO" + categoryDTO.toString());
        store_name.setText(categoryDTO.getCategoryName());
    }

    private void initLayout() {
        offer_rv = findViewById(R.id.offer_rv);
        offer_rv.setHasFixedSize(true);
        offer_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        storeOfferAdapter = new CategoryOfferAdapterDetails(this, storeOfferDTOS, categoryDTO);
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
            apiInterface.getCategoryOffers(categoryDTO.getCategoryId(), Constants.accessToken)
                    .enqueue(new Callback<List<StoreOfferDTO>>() {
                        @Override
                        public void onResponse(Call<List<StoreOfferDTO>> call, Response<List<StoreOfferDTO>> response) {
                            Constants.debugLog(TAG, "" + response);
                            if (response.isSuccessful() && response.body() != null) {
                                Constants.debugLog(TAG, "" + response.body());
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
                CategoryDetailsActivity.this.finish();
                break;
            case R.id.follow_layout:
                Toast.makeText(CategoryDetailsActivity.this, "Followed successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
