package com.rbricks.appsharing.concept.customdraw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.rbricks.appsharing.R;

public class PianoDrawActivity extends AppCompatActivity {

    private LinearLayout pianoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano_draw);
        setTitle("Piano Custom View");
        pianoContainer = ((LinearLayout) findViewById(R.id.piano_container));
        PianoCustomView pianoCustomView = new PianoCustomView(this);
        pianoContainer.addView(pianoCustomView);
    }
}
