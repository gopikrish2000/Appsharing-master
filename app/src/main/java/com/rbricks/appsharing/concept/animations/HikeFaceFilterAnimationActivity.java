package com.rbricks.appsharing.concept.animations;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.notesapp.utils.ViewUtils;
import com.rbricks.appsharing.utils.CommonUtils;

import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setGone;
import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setVisibleView;
import static com.rbricks.appsharing.utils.CommonUtils.dpToPx;

public class HikeFaceFilterAnimationActivity extends AppCompatActivity {

    private RelativeLayout hikeFacefilterParent;
    private View button1;
    private View button2;
    private View animationButton;
    View rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_animation);

        rootView = findViewById(R.id.hike_rootview);
        button1 = findViewById(R.id.hike_anim_btn1);
        button2 = findViewById(R.id.hike_anim_btn2);
        animationButton = findViewById(R.id.hike_animationbtn);
        hikeFacefilterParent = (RelativeLayout) findViewById(R.id.hike_facefilter_parent);
        initViews();
    }

    private void initViews() {
        setGone(hikeFacefilterParent);
        hikeFacefilterParent.setTranslationY(dpToPx(112));
        RxView.clicks(animationButton).subscribe(s -> {
            setVisibleView(hikeFacefilterParent);
            doAnimation();
        });
    }

    private void doAnimation() {
        /*rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                float button1Y = button1.getY();
                float button2Y = button2.getY();
//                ObjectAnimator buttonOneAnimator = ObjectAnimator.ofFloat(button1, "y", button1Y, button1Y - 200);  // one more way of setting x, y
//                ObjectAnimator buttonTwoAnimator = ObjectAnimator.ofFloat(button2, "y", button2Y, button2Y - 200);

            }
        });*/  // not required as of now.

        // Use translationX, Y for moving a view rather than fixed x & y . So that you don't need currentX, Y & ViewTreeObserver

        ObjectAnimator buttonOneAnimator = ObjectAnimator.ofFloat(button1, "translationY", dpToPx(-80));
        ObjectAnimator buttonTwoAnimator = ObjectAnimator.ofFloat(button2, "translationY", dpToPx(-80));
        ObjectAnimator faceFilterParentAnimator = ObjectAnimator.ofFloat(hikeFacefilterParent, "translationY", 0);

        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.hike_facefilter_capture);
        animatorSet.setTarget(button1);
        animatorSet.setDuration(500);
        animatorSet.playTogether(buttonOneAnimator, buttonTwoAnimator, faceFilterParentAnimator);
        animatorSet.start();

    }


}
