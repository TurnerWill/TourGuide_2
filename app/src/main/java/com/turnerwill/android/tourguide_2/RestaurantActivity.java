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
        mCardData = new CardData("Creole Soul", getString(R.string.description_creole), getString(R.string.creole_soul_phone), "43.0596", "-76.1520", R.drawable.creole_soul);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Koto Bar and Grill", getString(R.string.description_koto),getString(R.string.koto_grill_phone),"43.0697","-76.1740",
                R.drawable.koto_steakhouse);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("Pavone's Pizza", getString(R.string.description_pavones), getString(R.string.pavones_pizza_phone),"43.0475", "-76.1505",
                R.drawable.pavones_pizza);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("The Spaghetti Warehouse", getString(R.string.description_spaghetti), getString(R.string.spaghetti_phone),
               "43.0585","-76.1571", R.drawable.spaghetti_warehouse);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("The Rescue Mission Kitchen", getString(R.string.description_rescue), getString(R.string.rescue_phone),
               "43.0433", "-76.1557", R.drawable.rescue_mission);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("restaurant name", getString(R.string.description_general), null,
               "43.04", "-76.14", R.drawable.placeholder);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("restaurant name", getString(R.string.description_general), null,
               "43.04", "-76.14", R.drawable.placeholder);
        mRestaurantList.add(mCardData);
        mCardData = new CardData("restaurant name", getString(R.string.description_general), null,
               "43.04", "-76.14", R.drawable.placeholder);
        mRestaurantList.add(mCardData);

        // here the boolean 'map' indicates a DetailMapActivity(only one boolean should be true, all other bools should be false)
        MyAdapter myAdapter = new MyAdapter(RestaurantActivity.this, mRestaurantList, true, false);
        mRecyclerView.setAdapter(myAdapter);

    }
}
