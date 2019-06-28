package com.turnerwill.android.tourguide_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class DetailActivityVideo extends AppCompatActivity {

    Toolbar mToolbar;
    ImageView mImage;
    String videoPath;
    VideoView mVideo;
    MediaController mediaC;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);
        mToolbar = findViewById(R.id.toolbar);
        mImage = findViewById(R.id.ivImage);
        mVideo = findViewById(R.id.schoolVideo);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mToolbar.setTitle(mBundle.getString("Title"));
            mImage.setImageResource(mBundle.getInt("Image"));
            videoPath = mBundle.getString("Videopath");

            //configure and display toolbar
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // back press
                    Intent back = new Intent(DetailActivityVideo.this, MainActivity.class);
                    startActivity(back);
                }
            });

            mediaC = new MediaController(DetailActivityVideo.this);
            mediaC.setAnchorView(mVideo);
            Uri uri = Uri.parse(videoPath);
            mVideo.setMediaController(mediaC);
            mVideo.setVideoURI(uri);
            mVideo.requestFocus();
            mVideo.start();
        }
    }

}
