package com.rbricks.appsharing.newconcept.audioNVideoconcepts.audio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.rbricks.appsharing.R;

public class AudioPlaybackActivity extends AppCompatActivity {

    private Button playBtn;
    MediaPlayer mediaPlayer;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_playback);

        playBtn = ((Button) findViewById(R.id.audio_play_pause));
        mediaPlayer = MediaPlayer.create(this, R.raw.unnatu);


        playBtn.setOnClickListener(view -> {
            if (state == 0) {
                state = 1;
//                mediaPlayer.seekTo(0);  //To seek to position
                mediaPlayer.start();
            } else {
                state = 0;
                mediaPlayer.pause();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
