package com.rbricks.appsharing.concept.animations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
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
            case "animationset":
                animationSet();
                break;
            case "animatorset" :
                animationSetExampleWithAnimatorSet();
                break;
            case "path":
                pathAnimation();
                break;
            case "pathwithvalueanim":
                pathAnimationWithValueAnim();
                break;

        }
    }

    public void pathAnimation() {
        Path path = new Path();
        // for absolute path
        /*path.lineTo(400f, 500f);
        path.lineTo(400f, 600f);*/

        // relative path
        path.moveTo(propertyAnimationTv.getX(),propertyAnimationTv.getY());
        path.rLineTo(300f,300f);
        path.rLineTo(-150f,-150f);
        path.rLineTo(-150f,150f);
        path.rLineTo(200f, 200f);

//        path.moveTo(400f, 600f);
//        path.lineTo(1f, 1f);
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "x", "y", path);
        objectAnimator.setDuration(4000).setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public void pathAnimationWithValueAnim() {
        Path path = new Path();
        // for absolute path
        /*path.lineTo(400f, 500f);
        path.lineTo(400f, 600f);*/

        // relative path
        path.moveTo(propertyAnimationTv.getX(),propertyAnimationTv.getY());
        path.rLineTo(300f,300f);
        path.rLineTo(-150f,-150f);
        path.rLineTo(-150f,150f);
        path.rLineTo(200f, 200f);

        float xVal = propertyAnimationTv.getX();
//        ValueAnimator pathAnimator = ValueAnimator.ofFloat(xVal+300, xVal+150,xVal,xVal+200);
        ValueAnimator pathAnimator = ValueAnimator.ofFloat(xVal);

        pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float[] point = new float[2];
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Gets the animated float fraction
                float val = animation.getAnimatedFraction();

                // Gets the point at the fractional path length
                PathMeasure pathMeasure = new PathMeasure(path, true);
                pathMeasure.getPosTan(pathMeasure.getLength() * val, point, null);

                // Sets view location to the above point
                propertyAnimationTv.setX(point[0]);
                propertyAnimationTv.setY(point[1]);
            }
        });

        pathAnimator.setDuration(4000).setInterpolator(new AccelerateDecelerateInterpolator());
        pathAnimator.start();
    }



    public void animationSet() {
        // AnimationSet is old one. Don't use it ( its in android.view package , AnimatorSet is in android.animation package)
        AnimationSet as = new AnimationSet(true);
        as.setFillEnabled(false);
        as.setInterpolator(new BounceInterpolator());

        TranslateAnimation ta = new TranslateAnimation(-300, 100, 0, 0);
        ta.setDuration(2000);
        as.addAnimation(ta);

        TranslateAnimation ta2 = new TranslateAnimation(100, 0, 0, 0);
        ta2.setDuration(2000);
        ta2.setStartOffset(3000); // allowing 2000 milliseconds for ta to finish
//        ta2.setFillBefore(true);
        as.addAnimation(ta2);
        propertyAnimationTv.startAnimation(as);
    }

    public void animationSetExampleWithAnimatorSet() {
//        propertyAnimationTv.animate().xBy(400f).setDuration(2000).xBy(-100);
        // Below 400f will be from current to Absolute 400 not relative . If u want releative to View.getX+400f
        ObjectAnimator first = ObjectAnimator.ofFloat(propertyAnimationTv, "x", 400f).setDuration(2000);
        ObjectAnimator second = ObjectAnimator.ofFloat(propertyAnimationTv, "x", -100f).setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        (animatorSet.play(second).after(6000)).after(first);
        animatorSet.start();
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

            //For Absolute setting values
//        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "translationX", 0f,100f,180f,240f).setDuration(4000);
        // For relatively setting values
        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, "translationX", propertyAnimationTv.getTranslationX(),propertyAnimationTv.getTranslationX()+100f,propertyAnimationTv.getTranslationX()+180f,propertyAnimationTv.getTranslationX()+240f).setDuration(4000);
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
