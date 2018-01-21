package mastersunny.unitedclub.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import mastersunny.unitedclub.Adapter.PopularVerticalAdapter;
import mastersunny.unitedclub.Model.PopularDTO;
import mastersunny.unitedclub.R;

public class PopularActivity extends AppCompatActivity {

    private RecyclerView popular_rv;
    private PopularVerticalAdapter popularVerticalAdapter;
    private ArrayList<PopularDTO> popularDTOS;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        popularDTOS = new ArrayList<>();
        initlayout();
    }

    private void initlayout() {
        for (int i = 0; i < 20; i++) {
            PopularDTO popularDTO = new PopularDTO();
            popularDTO.setCompanyName("Dominos");
            popularDTO.setTotalOffer(i);
            popularDTOS.add(popularDTO);
        }

        popular_rv = findViewById(R.id.popular_rv);
        popular_rv.setHasFixedSize(true);
        popular_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        popularVerticalAdapter = new PopularVerticalAdapter(this, popularDTOS);
        popular_rv.setAdapter(popularVerticalAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
