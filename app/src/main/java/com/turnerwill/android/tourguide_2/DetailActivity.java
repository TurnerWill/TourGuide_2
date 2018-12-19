package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImage;
    TextView mDescription;
    String phone;
    FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = findViewById(R.id.toolbar);
        mImage = findViewById(R.id.ivImage);
        mDescription = findViewById(R.id.tvDescription);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mToolbar.setTitle(mBundle.getString("Title"));
            mImage.setImageResource(mBundle.getInt("Image"));
            mDescription.setText(mBundle.getString("Description"));
            // allow description text to scroll
            mDescription.setMovementMethod(new ScrollingMovementMethod());

            //configure and display toolbar
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // back press
                    Intent back = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(back);
                }
            });

            // prep String phone for possible implicit Intent.Action_Dial from a FAB click
            phone = "tel:+" + mBundle.getString("Phone_number");
            fab = (FloatingActionButton)findViewById(R.id.fab);

            // DetailActivity with null Phone_number values hides fab button
            // DetailActivity with Phone_number values launches Intent.Action_Dial from fab click
            if(mBundle.getString("Phone_number") == null)
                fab.hide();
            else{
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(phone));
                            startActivity(intent);
                        }
                    });
            }

        }
    }

}
