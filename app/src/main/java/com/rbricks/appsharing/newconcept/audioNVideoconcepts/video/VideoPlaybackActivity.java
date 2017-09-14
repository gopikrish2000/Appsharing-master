package com.rbricks.appsharing.newconcept.audioNVideoconcepts.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rbricks.appsharing.R;

import java.io.File;

public class VideoPlaybackActivity extends AppCompatActivity {

    private Button playBtn;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        playBtn = ((Button) findViewById(R.id.video_play_pause));
        videoView = ((VideoView) findViewById(R.id.video_container));

         MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

              //specify the location of media file
//           Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
        String uriString = "android.resource://com.rbricks.appsharing/raw/samplevid";
        String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevid;
        Uri uri=Uri.parse(path);

              //Setting MediaController and URI, then starting the videoView
           videoView.setMediaController(mediaController);
           videoView.setVideoURI(uri);
           videoView.requestFocus();
           videoView.start();  // only sound is audible but not the video. ( Saying video cannot be played )


    }
}
