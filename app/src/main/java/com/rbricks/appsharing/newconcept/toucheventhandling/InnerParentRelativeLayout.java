package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gopi on 21/08/17.
 */

public class InnerParentRelativeLayout extends TouchRelativeLayoutWrapper {
    public InnerParentRelativeLayout(Context context) {
        super(context);
        initProcess();
    }

    public InnerParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProcess();
    }

    public InnerParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProcess();
    }
     private void initProcess() {
//        getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("InnerParentRelativeLayout.onInterceptTouchEvent " + ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("InnerParentRelativeLayout.onTouchEvent " + event.getAction());
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("InnerParentRelativeLayout.dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
//        return true;
    }
}
