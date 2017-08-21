package com.rbricks.appsharing.newconcept.hikecameraconcepts.basiccamera;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rbricks.appsharing.R;

public class BasicVideoActivity extends AppCompatActivity {

    private boolean recording;
    private VideoCapture videoCapture;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_video);

        videoCapture = (VideoCapture) findViewById(R.id.vid_videocapture);
        stop= (Button) findViewById(R.id.vid_takbtn);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                videoCapture.stopCapturingVideo();
                /*setResult(Activity.RESULT_OK);
                finish();*/
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.vid_takbtn:
                /*try{
                    if (recording) {
                        recorder.stop();
                        recording = false;
                        // Let's initRecorder so we can record again
                        //initRecorder();
                        //prepareRecorder();
                    } else {
                        recording = true;
                        recorder.start();
                    }

                }catch(Exception e){

                }*/
            videoCapture.stopCapturingVideo();
        default:
            break;

        }
    }

}
