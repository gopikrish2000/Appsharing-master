package com.rbricks.appsharing.newconcept.moveFragmentUponSwipe;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setGone;
import static com.rbricks.appsharing.utils.CommonUtils.DisplayWidthPixels;

// For reference https://stackoverflow.com/questions/21026409/fragment-transaction-animation-slide-in-and-slide-out
public class MoveFragmentUponSwipeActivity extends AppCompatActivity {

    private FrameLayout moveBottomFrame;
    private FrameLayout moveTopFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_fragment_upon_swipe);

        moveBottomFrame = (FrameLayout) findViewById(R.id.move_bottomFrame);
        moveTopFrame = (FrameLayout) findViewById(R.id.move_topFrame);

        MoveBottomFragment moveBottomFragment = new MoveBottomFragment();
        MoveTopFragment moveTopFragment = new MoveTopFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.move_bottomFrame, moveBottomFragment, "BottomFrag").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.move_topFrame, moveTopFragment, "TopFrag").commit();
        moveTopFrame.setTranslationX(DisplayWidthPixels);
//        slideToRightTopFragment();
//        slideToLeftFragment();
        slideWithFingerOneOverOther();


    }

    private void slideWithFingerOneOverOther() {
        moveBottomFrame.setOnTouchListener(new View.OnTouchListener() {

            float startX, startY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        startY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                       /* startX = Integer.MIN_VALUE;
                        startY = Integer.MIN_VALUE;*/
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float endX = motionEvent.getX();
                        float endY = motionEvent.getY();

                        float diffX = endX - startX;
                        moveTopFrame.setTranslationX(moveTopFrame.getTranslationX() + diffX);
                        startX = endX;
                        break;
                }
                return true;
            }
        });
    }

    private void slideToLeftFragment() {
        // SLide to left
        Observable.just(1).delay(4, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            // First way
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(moveTopFrame, "translationX", DisplayWidthPixels, DisplayWidthPixels / 2).setDuration(4000);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

          /*  ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(moveTopFrame, "translationX", CommonUtils.DisplayWidthPixels / 2,  (CommonUtils.DisplayWidthPixels/2 + 100)).setDuration(4000);
            objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
//            objectAnimator.start();
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(objectAnimator, objectAnimator1);
            set.start();*/

            /*
            Second way
            moveTopFrame.animate().translationXBy(CommonUtils.DisplayWidthPixels / 2).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(4000).start();*/
        });
    }

    private void slideToRightTopFragment() {
        //        Slide to right
        Observable.just(1).delay(4, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            // First way
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(moveTopFrame, "translationX", 0, DisplayWidthPixels / 2).setDuration(4000);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//            objectAnimator.start();

            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(moveTopFrame, "translationX", DisplayWidthPixels / 2, (DisplayWidthPixels / 2 + 100)).setDuration(4000);
            objectAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
//            objectAnimator.start();
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(objectAnimator, objectAnimator1);
            set.start();

            /*
            Second way
            moveTopFrame.animate().translationXBy(CommonUtils.DisplayWidthPixels / 2).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(4000).start();*/
        });
    }
}
