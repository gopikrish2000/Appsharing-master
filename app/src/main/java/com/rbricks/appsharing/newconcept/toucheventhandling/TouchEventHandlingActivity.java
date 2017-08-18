package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rbricks.appsharing.R;

public class TouchEventHandlingActivity extends AppCompatActivity {

   private RelativeLayout touchParentBlueRl;
    private RelativeLayout touchChildRedRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_handling);

        touchParentBlueRl = (RelativeLayout) findViewById(R.id.touch_parent_blue_rl);
        touchChildRedRl = (RelativeLayout) findViewById(R.id.touch_child_red_rl);
//        findViewById(R.id.touch_child_btn).setOnClickListener(this);
        String simpleName = TouchEventHandlingActivity.class.getSimpleName();
    }



}
