package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gopi on 21/08/17.
 */

public class OuterParentRelativeLayout extends TouchRelativeLayoutWrapper {
    public OuterParentRelativeLayout(Context context) {
        super(context);
        initProcess();
    }

    public OuterParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProcess();
    }

    public OuterParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProcess();
    }

    private void initProcess()
    {
//        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("OuterParentRelativeLayout.onInterceptTouchEvent " + ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("OuterParentRelativeLayout.onTouchEvent " + event.getAction());
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("OuterParentRelativeLayout.dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
//        return true;
    }
}
