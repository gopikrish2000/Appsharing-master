package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rbricks.appsharing.R;

public class TouchEventPropagationWithListenersActivity extends AppCompatActivity {

    private OuterParentRelativeLayout touchParentBlueRl;
    private InnerParentRelativeLayout touchChildRedRl;
    private TextView touchChildTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_propagation_with_listeners);

        touchParentBlueRl = (OuterParentRelativeLayout) findViewById(R.id.touch_parent_blue_rl);
        touchChildRedRl = (InnerParentRelativeLayout) findViewById(R.id.touch_child_red_rl);
        touchChildTv = ((TextView) findViewById(R.id.touch_child_tv));

        touchChildRedRl.setOnTouchListener(((view, motionEvent) -> {
            System.out.println("TouchEventPropagationWithListenersActivity.onTouchEvent() ");
            return true;
        }));

//        JpegImageMetadata
//        TiffImageMetadata
    }
}
