package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gopi on 21/08/17.
 */

public class ChildTextView extends android.support.v7.widget.AppCompatTextView {
    public ChildTextView(Context context) {
        super(context);
    }

    public ChildTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        requestDisallowInterceptTouchEvent();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("ChildTextView.onTouchEvent " + event.getAction());
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("ChildTextView.dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
//        return false;
    }
}
