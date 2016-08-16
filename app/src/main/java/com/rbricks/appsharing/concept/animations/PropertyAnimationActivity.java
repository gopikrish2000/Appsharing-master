package com.rbricks.appsharing.concept.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rbricks.appsharing.R;

import static com.jakewharton.rxbinding.view.RxView.clicks;
import static com.jakewharton.rxbinding.widget.RxTextView.textChangeEvents;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;

public class PropertyAnimationActivity extends AppCompatActivity {

    private TextView propertyAnimationTv;
    public Button playAnimationButton;
    public Button playAnimationButton2;
    public Button playAnimationButton3;
    private ImageView animatedImageView;
    private EditText implementationEditText;
    private String currentImplementationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        init();

        clicks(playAnimationButton).subscribe(s -> animationSample1());
        clicks(playAnimationButton2).subscribe(s -> animationSample2());
        clicks(propertyAnimationTv).subscribe(s -> Toast.makeText(PropertyAnimationActivity.this, "text clicked", Toast.LENGTH_SHORT).show());
        clicks(findViewById(R.id.animation_processButton)).subscribe(s -> checkImplementation());
        textChangeEvents(implementationEditText).subscribe(s -> currentImplementationText = s.text().toString());
    }

    private void init() {
        propertyAnimationTv = (TextView) findViewById(R.id.property_animation_tv);
        playAnimationButton = (Button) findViewById(R.id.property_playanimation_button);
        playAnimationButton2 = (Button) findViewById(R.id.property_playanimation_button2);
        playAnimationButton3 = (Button) findViewById(R.id.property_playanimation_button3);
        animatedImageView = ((ImageView) findViewById(R.id.animateImageView));
        implementationEditText = ((EditText) findViewById(R.id.processanimation_et));
    }

    private void checkImplementation() {
        if (isNullOrEmpty(currentImplementationText)) {
            return;
        }
        switch (currentImplementationText) {
            case "1":
                animationSample1();
                break;
            case "2":
                animationSample2();
                break;
            case "set":
                animatorSetExample();
                break;
            case "frame":
                frameAnimationExample();
                break;
            case "color":
                colorAnimator();
                break;
            case "valueanimator":
                valueAnimator();
                break;
            case "valuecolor":
                valueColor();
                break;

        }
    }

    private void valueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(3000).start();
        valueAnimator.addUpdateListener(animator -> {
            int animatedValue = ((Integer) animator.getAnimatedValue());
            propertyAnimationTv.setText("animatedVal : "+animatedValue);
        });
    }

    public void valueColor() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), getResources().getColor(R.color.red), getResources().getColor(R.color.blue)).setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(s -> {
            int color = (Integer) s.getAnimatedValue();
//            propertyAnimationTv.setText(" value :: "+ color);
            propertyAnimationTv.setTextColor(color); ;
        });
        valueAnimator.start();
    }

    private void colorAnimator() {
        /*ObjectAnimator textAnimator = ObjectAnimator.ofObject(propertyAnimationTv, "text", (fraction, start, end) -> {
            return fraction + 0.2f;
        }, "abc", "def");*/
//        ObjectAnimator textAnimator = ObjectAnimator.ofInt(propertyAnimationTv, "backgroundColor", 0xff0000,0x0000ff);
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(propertyAnimationTv, "textColor", new ArgbEvaluator(), getResources().getColor(R.color.red), getResources().getColor(R.color.blue));
        colorAnimator.setDuration(2000);
        colorAnimator.start();
    }

    public static int interpolateRGB(final int colorA, final int colorB, final float bAmount) {
        final float aAmount = 1.0f - bAmount;
        final int red = (int) (Color.red(colorA) * aAmount + Color.red(colorB) * bAmount);
        final int green = (int) (Color.green(colorA) * aAmount + Color.green(colorB) * bAmount);
        final int blue = (int) (Color.blue(colorA) * aAmount + Color.blue(colorB) * bAmount);
        return Color.rgb(red, green, blue);
    }


    public void animationSample1() {

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

    public void animatorSetExample() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(PropertyAnimationActivity.this,
                R.animator.animation_sample3);
        set.setTarget(propertyAnimationTv);
        set.start();
    }

    public void frameAnimationExample() {
        animatedImageView.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable animationDrawable = ((AnimationDrawable) animatedImageView.getBackground());
//        animationDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.DARKEN));
        animationDrawable.start();
    }
}
