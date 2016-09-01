package com.rbricks.appsharing.concept.animations;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.RefStreams;
import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

import static com.jakewharton.rxbinding.view.RxView.clicks;
import static com.jakewharton.rxbinding.widget.RxTextView.textChangeEvents;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;

public class AdvancedAnimationActivity extends AppCompatActivity {

    private TextView propertyAnimationTv;
    private ImageView animatedImageView;
    private EditText implementationEditText;
    private String currentImplementationText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_animation);
        init();
        clicks(findViewById(R.id.animation_processButton)).subscribe(s -> performCurrentImplementation());
        clicks(findViewById(R.id.animation_recentButton)).subscribe(s -> firstComplexAnimation());
        textChangeEvents(implementationEditText).subscribe(s -> currentImplementationText = s.text().toString());
    }

    private void init() {
        propertyAnimationTv = (TextView) findViewById(R.id.property_animation_tv);
        animatedImageView = ((ImageView) findViewById(R.id.animateImageView));
        implementationEditText = ((EditText) findViewById(R.id.processanimation_et));
    }

    private void performCurrentImplementation() {
        if (isNullOrEmpty(currentImplementationText)) {
            return;
        }

        switch (currentImplementationText) {
            case "first":
                firstComplexAnimation();
                break;
        }
    }

    private void firstComplexAnimation() {
        float x = propertyAnimationTv.getX();
        float y = propertyAnimationTv.getY();
        Path path = new Path();
        path.moveTo(propertyAnimationTv.getX(),propertyAnimationTv.getY());
       /* path.rLineTo(50f,50f);
        path.rLineTo(-50f,-50f);*/
//        path.arcTo(x,y+200,x+300,y,30f,30f,true);
        /*path.quadTo(x+150,y+150,x+300,y);
        path.quadTo(x-150,y+150,x,y);*/
        path.rCubicTo(450,-450,450,-900,0,0);
        RectF bounds = new RectF(x, y+190, x+190, y);
        path.addOval(bounds, Path.Direction.CW);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(propertyAnimationTv, View.X, View.Y, path).setDuration(9000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.setRepeatCount(1);
//        objectAnimator.setRepeatMode(2);
        objectAnimator.start();

    }

}


