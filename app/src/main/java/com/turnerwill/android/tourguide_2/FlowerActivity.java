package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FlowerActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<CardData> mFlowerList;
    CardData mCardData;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check for portrait orientation to enable slide transition to next activity
        // if not in portrait orientation then no slide animation
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        setContentView(R.layout.activity_card);

        //display toolbar and set title for toolbar
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.flowers);

        // enable back navigation to MainActivity from toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent back = new Intent(FlowerActivity.this, MainActivity.class);
                startActivity(back);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(FlowerActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mFlowerList = new ArrayList<CardData>();
        mCardData = new CardData("Rose", getString(R.string.description_general),null,
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Carnation", getString(R.string.description_general), null,
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Tulip", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Daisy", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Sunflower", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Daffodil", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Gerbera", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Orchid", "description goes here", getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Iris", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);
        mCardData = new CardData("Lilac", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.going_asian);
        mFlowerList.add(mCardData);

        MyAdapter myAdapter = new MyAdapter(FlowerActivity.this, mFlowerList);
        mRecyclerView.setAdapter(myAdapter);

    }
}
