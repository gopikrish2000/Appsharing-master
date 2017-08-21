package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by gopi on 21/08/17.
 */

public class TouchRelativeLayoutWrapper extends RelativeLayout {
    public TouchRelativeLayoutWrapper(Context context) {
        super(context);
    }

    public TouchRelativeLayoutWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchRelativeLayoutWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }




}
