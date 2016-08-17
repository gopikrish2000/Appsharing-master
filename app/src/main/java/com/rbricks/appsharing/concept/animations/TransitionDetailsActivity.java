package com.rbricks.appsharing.concept.animations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.rbricks.appsharing.R;

public class TransitionDetailsActivity extends AppCompatActivity {

    private ImageView sharedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_details);

        sharedImageView = ((ImageView) findViewById(R.id.sharedImageView));
        sharedImageView.setTransitionName("sharedImage");
    }
}
