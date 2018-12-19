package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<CardData> mRestaurantList;
    CardData mCardData;

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
        mToolbar.setTitle(R.string.restaurants);

        // enable back navigation to MainActivity from toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent back = new Intent(RestaurantActivity.this, MainActivity.class);
                startActivity(back);
            }
        });


        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(RestaurantActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRestaurantList = new ArrayList<CardData>();
        mCardData = new CardData("Thornden Rose Park", "description goes here", getString(R.string.chipotle_grill),R.drawable.going_asian2);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Carnation", getString(R.string.description_general),getString(R.string.chipotle_grill),
                R.drawable.american_psycho);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Tulip", getString(R.string.description_general), getString(R.string.chipotle_grill),
                R.drawable.click_bait);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Thornden Rose Park", getString(R.string.description_thornden), null,
                R.drawable.park_thornden_rose);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Burnet Park Zoo", getString(R.string.description_burnet), null,
                R.drawable.tiger_yawn);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Erie Canal Waterway & Green Lakes State Park", getString(R.string.description_erie), null,
                R.drawable.erie_canal);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Jamesville Beach/Syracuse Balloonfest", getString(R.string.description_jamesville), null,
                R.drawable.jamesville_balloon);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Clark Reservation State Park", getString(R.string.description_clark), null,
                R.drawable.clark_reservation);
        mRestaurantList.add(mCardData);

        MyAdapter myAdapter = new MyAdapter(RestaurantActivity.this, mRestaurantList, true);
        mRecyclerView.setAdapter(myAdapter);

    }
}
