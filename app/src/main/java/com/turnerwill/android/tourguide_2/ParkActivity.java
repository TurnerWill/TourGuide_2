package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ParkActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<CardData> mParkList;
    CardData mCardData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check for portrait orientation to enable slide transition
        // if not in portrait orientation then no slide animation
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        setContentView(R.layout.activity_card);

        //display toolbar and set title for toolbar
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.parks);

        // enable back navigation to MainActivity from toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent back = new Intent(ParkActivity.this, MainActivity.class);
                startActivity(back);
            }
        });


        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(ParkActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mParkList = new ArrayList<CardData>();
        mCardData = new CardData("Thornden Rose Park", "description goes here", getString(R.string.chipotle_grill),R.drawable.going_asian2);
        mParkList.add(mCardData);
        mCardData = new CardData("Carnation", getString(R.string.description_general),getString(R.string.chipotle_grill),
                R.drawable.american_psycho);
        mParkList.add(mCardData);
        mCardData = new CardData("Tulip", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.click_bait);
        mParkList.add(mCardData);
        mCardData = new CardData("Thornden Rose Park", getString(R.string.description_thornden), null,
                R.drawable.park_thornden_rose);
        mParkList.add(mCardData);
        mCardData = new CardData("Burnet Park Zoo", getString(R.string.description_burnet), null,
                R.drawable.tiger_yawn);
        mParkList.add(mCardData);
        mCardData = new CardData("Erie Canal Waterway & Green Lakes State Park", getString(R.string.description_erie), null,
                R.drawable.erie_canal);
        mParkList.add(mCardData);
        mCardData = new CardData("Jamesville Beach/Syracuse Balloonfest", getString(R.string.description_jamesville), null,
                R.drawable.jamesville_balloon);
        mParkList.add(mCardData);
        mCardData = new CardData("Clark Reservation State Park", getString(R.string.description_clark), null,
                R.drawable.clark_reservation);
        mParkList.add(mCardData);
        
        MyAdapter myAdapter = new MyAdapter(ParkActivity.this, mParkList);
        mRecyclerView.setAdapter(myAdapter);

    }
}
