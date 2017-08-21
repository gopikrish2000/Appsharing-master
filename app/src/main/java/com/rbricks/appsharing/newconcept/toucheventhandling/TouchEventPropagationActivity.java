package com.rbricks.appsharing.newconcept.toucheventhandling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.rbricks.appsharing.R;

public class TouchEventPropagationActivity extends AppCompatActivity {

   private OuterParentRelativeLayout touchParentBlueRl;
    private InnerParentRelativeLayout touchChildRedRl;
    private ChildTextView touchChildTv;

    /* See important info at  http://balpha.de/2013/07/android-development-what-i-wish-i-had-known-earlier/
    *
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_handling);

        touchParentBlueRl = (OuterParentRelativeLayout) findViewById(R.id.touch_parent_blue_rl);
        touchChildRedRl = (InnerParentRelativeLayout) findViewById(R.id.touch_child_red_rl);
        touchChildTv = ((ChildTextView) findViewById(R.id.touch_child_tv));
    }


    /*
       ONTOUCHEVENT Propagation is from children to its parent where ever it is true ( consumed) it will stop there .
       ChildTextView -> false ;  InnerParentRelativeLayout -> true ; OuterParentRelativeLayout -> false then output is ChildTextView.onTouchEvent , InnerParentRelativeLayout.onTouchEvent ( stopped at this point bcoz of true)
    */
    /*
    *  . We will talk about a new method now: onInterceptTouchEvent, which only exists on ViewGroup objects, not on regular views.
    *    Before the onTouchEvent is called on any View, all its ancestors are first given the chance to intercept this event. In other words, they can steal it.
    *    ONINTERCEPTTOUCHEVENT return false then it is not consuming so goes to its child etc.
    *
    *    Flow on touching ChildTextView is          onInterceptTouchEvent ( OuterParentRelativeLayout ) -> false ; onInterceptTouchEvent ( InnerParentRelativeLayout ) -> false ;  onTouchEvent ( ChildTextView) -> false ;
    *    onTouch ( InnerParentRelativeLayout) -> true ;   it will stop here bcoz of true in onTouch method.
    *
    *    If View C's onTouchEvent returned false here, things would continue like in the “default case” section – even though the ViewGroups said they didn't want to intercept, they would still receive the event in  onTouchEvent because the child didn't handle it.
    * */

    /*
    * A little thing that may be surprising is the following:
    .  If a ViewGroup intercepts the initial DOWN event, this event will also be passed to the ViewGroup's onTouchEvent.
    .  On the other hand, if the ViewGroup only intercepts a later (say, MOVE) event, this event will be changed to a CANCEL event and passed to the child that handled this gesture previously, but will not be passed (neither as a MOVE nor as a CANCEL event) to the ViewGroup's onTouchEvent. Only the then-next event will end up there.
    * */

    /*
    * Dispatch touch event shouldn't be overridden in normal cases so ignore it.*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("TouchEventPropagationActivity.onTouchEvent " + event.getAction());
        return super.onTouchEvent(event);
    }

}
