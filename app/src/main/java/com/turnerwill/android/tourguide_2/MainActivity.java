package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);


        // establish clickable TextView field for flowers category
        TextView flowersView = (TextView)findViewById(R.id.flowers);

        flowersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent flowersIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(flowersIntent);

            }
        });

        // establish clickable TextView field for parks category
        TextView parksView = (TextView)findViewById(R.id.parks);

        parksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent parksItent = new Intent(MainActivity.this, ParkActivity.class);
                startActivity(parksItent);

            }
        });

        // establish clickable TextView field for schools category
        TextView schoolsView = (TextView)findViewById(R.id.schools);

        schoolsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent schoolsItent = new Intent(MainActivity.this, SchoolActivity.class);
                startActivity(schoolsItent);

            }
        });

        // establish clickable TextView field for restaurants category
        TextView restaurantsView = (TextView)findViewById(R.id.restaurants);

        restaurantsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent restaurantsItent = new Intent(MainActivity.this, RestaurantActivity.class);
                startActivity(restaurantsItent);

            }
        });

    }
}
