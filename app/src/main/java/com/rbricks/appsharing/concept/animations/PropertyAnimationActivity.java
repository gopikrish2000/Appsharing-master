package com.rbricks.appsharing.concept.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.rbricks.appsharing.R;

public class PropertyAnimationActivity extends AppCompatActivity {

    private TextView propertyAnimationTv;
    public Button playAnimationButton;
    public Button playAnimationButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        propertyAnimationTv = (TextView) findViewById(R.id.property_animation_tv);
        playAnimationButton = (Button) findViewById(R.id.property_playanimation_button);
        playAnimationButton2 = (Button) findViewById(R.id.property_playanimation_button2);

        RxView.clicks(playAnimationButton).subscribe(s -> animationSample1());
        RxView.clicks(playAnimationButton2).subscribe(s -> animationSample3());
        RxView.clicks(propertyAnimationTv).subscribe(s -> Toast.makeText(PropertyAnimationActivity.this, "text clicked", Toast.LENGTH_SHORT).show());
    }

    public void animationSample1() {
        /*ObjectAnimator textAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "text", 0f, 1f);
        textAnimator.setDuration(2000);
        textAnimator.setInterpolator(new BounceInterpolator());*/

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "alpha", 1f,0.4f,0.2f,0.1f).setDuration(2000);
        alphaAnimator.setRepeatCount(1);
        alphaAnimator.setRepeatMode(2);
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "rotation", 180f).setDuration(2000);
        translateAnimator.setRepeatCount(1);
        translateAnimator.setRepeatMode(2);
        translateAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "translationX", 0f,100f,180f,240f).setDuration(4000);
        moveAnimator.setInterpolator(new BounceInterpolator());


        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playSequentially(alphaAnimator,translateAnimator,moveAnimator);

        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                System.out.println("PropertyAnimationActivity.onAnimationStart " + animator);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                System.out.println("PropertyAnimationActivity.onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                System.out.println("PropertyAnimationActivity.onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                System.out.println("PropertyAnimationActivity.onAnimationRepeat");
            }
        });
        animationSet.start();
    }

    public void animationSample2() {
        propertyAnimationTv.animate().translationXBy(100f).translationYBy(100f).setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(1f).alpha(0.2f).start();
    }

    public void animationSample3() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(PropertyAnimationActivity.this,
                R.animator.animation_sample3);
        set.setTarget(propertyAnimationTv);
        set.start();
    }
}
