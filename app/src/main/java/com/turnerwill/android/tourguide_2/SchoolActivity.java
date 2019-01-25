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

public class SchoolActivity extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    List<CardData> mSchoolList;
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
        mToolbar.setTitle("Schools");

        // enable back navigation to MainActivity from toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent back = new Intent(SchoolActivity.this, MainActivity.class);
                startActivity(back);
            }
        });


        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(SchoolActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mSchoolList = new ArrayList<CardData>();
        mCardData = new CardData("Upstate Medical University", R.drawable.suny_logo, "android.resource://com.turnerwill.android.tourguide_2/"+R.raw.upstate_video);
        mSchoolList.add(mCardData);
        mCardData = new CardData("Syracuse University", R.drawable.su_logo, "android.resource://com.turnerwill.android.tourguide_2/"+R.raw.su_video);
        mSchoolList.add(mCardData);
        mCardData = new CardData("Onondaga Community College", R.drawable.occ_logo, "android.resource://com.turnerwill.android.tourguide_2/"+R.raw.occ_video);
        mSchoolList.add(mCardData);
        mCardData = new CardData("SUNY Oswego", R.drawable.oswego_logo, "android.resource://com.turnerwill.android.tourguide_2/"+R.raw.oswego_video);
        mSchoolList.add(mCardData);

       /* mCardData = new CardData("Syracuse University", "description goes here", null, R.drawable.su_logo);
        mSchoolList.add(mCardData);
        mCardData = new CardData("Lemoyne College", getString(R.string.description_general), null,
                R.drawable.lemoyne_logo);
        mSchoolList.add(mCardData);
        mCardData = new CardData("Onondaga Community College", getString(R.string.description_thornden), null,
                R.drawable.occ_logo);
        mSchoolList.add(mCardData);
        mCardData = new CardData("SUNY Oswego", getString(R.string.description_burnet), null,
                R.drawable.oswego_logo);
        mSchoolList.add(mCardData);
        mCardData = new CardData("SUNY Enviromental School of Science and Forestry", getString(R.string.description_erie), null,
                R.drawable.esf_logo);
        mSchoolList.add(mCardData);*/


        // here the boolean 'video' indicates a DetailVideoActivity(only one boolean should be true, all other bools should be false)
        MyAdapter myAdapter = new MyAdapter(SchoolActivity.this, mSchoolList, false, true);
        mRecyclerView.setAdapter(myAdapter);

    }
}