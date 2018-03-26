package com.rbricks.appsharing.HikeHackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rbricks.appsharing.R;

public class HikeHackathonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_hackathon);
        init();
    }

    private void init() {
        NetworkHelper.uploadToServer("/sdcard/Hike/CameraStudio/Studio_25_16_06_09.mp4", "http://104.199.159.119/video?topLeft_bg_normalized_1=0.3&topLeft_bg_normalized_2=0.0&selected_bg_width_normalized=0.4&selected_bg_height_normalized=0.8&bg_filename=sky_news.jpg").subscribe((str) -> {
            Toast.makeText(this, "video file uploaded Success " + str, Toast.LENGTH_SHORT).show();
        }, (error) -> {
            Toast.makeText(this, "video file uploaded ERROR", Toast.LENGTH_SHORT).show();
        });
    }
}
