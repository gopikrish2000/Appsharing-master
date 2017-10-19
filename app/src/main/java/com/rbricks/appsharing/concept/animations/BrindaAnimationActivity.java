package com.rbricks.appsharing.concept.animations;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class BrindaAnimationActivity extends AppCompatActivity {

    private ImageView brindImageView;
    private TextView brindDiwaliStartTv;
    private TextView brindDiwaliEndTv;
    private Button button;
    private RelativeLayout rootView;
    private TextView bottomTv;
    private Vibrator vibrator;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brinda_animation);

        brindImageView = (ImageView) findViewById(R.id.brind_imageView);
        brindDiwaliStartTv = (TextView) findViewById(R.id.brind_diwali_start_tv);
        brindDiwaliEndTv = (TextView) findViewById(R.id.brind_diwali_end_tv);
        bottomTv = (TextView) findViewById(R.id.brind_bottom_tv);
        button = ((Button) findViewById(R.id.brind_perform_animation));
        rootView = ((RelativeLayout) findViewById(R.id.brind_root_layout));
        /*ObjectAnimator animator = ObjectAnimator.ofFloat(brindDiwaliStartTv, "x", 20, 40);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);
        animator.start();*/
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        bottomTv.setVisibility(View.INVISIBLE);
        bottomTv.setAlpha(0.2f);
        brindImageView.setVisibility(View.INVISIBLE);
        brindDiwaliEndTv.setVisibility(View.INVISIBLE);
        brindDiwaliStartTv.setVisibility(View.INVISIBLE);
        RxView.clicks(button).subscribe(s -> {
            performAnimation();
        });

    }

    private void performAnimation() {
        brindImageView.setVisibility(View.VISIBLE);
        brindDiwaliEndTv.setVisibility(View.VISIBLE);
        brindDiwaliStartTv.setVisibility(View.VISIBLE);
        float targetView = brindDiwaliStartTv.getY() + 100f;
        brindDiwaliStartTv.animate().xBy(-250).setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(0.8f).alpha(0.6f).alpha(0.4f).alpha(0.2f).setDuration(4000).start();

        brindDiwaliEndTv.animate().xBy(250).setInterpolator(new AccelerateDecelerateInterpolator())
                .alpha(0.8f).alpha(0.6f).alpha(0.4f).alpha(0.2f).setDuration(4000).start();

        /*brindImageView.animate().translationYBy(-400).yBy(-100).y(targetView).setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(4000).start();*/
        moveViewToScreenCenter(brindImageView);

        Observable.just(1).delay(4, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            bottomTv.setVisibility(View.VISIBLE);
            bottomTv.animate().translationYBy(-250).setInterpolator(new AccelerateDecelerateInterpolator()).alpha(1f)
                    .setDuration(4000).start();
            vibrator.vibrate(4000);
        });

        button.setVisibility(View.GONE);
    }

    private void moveViewToScreenCenter(View view) {
        RelativeLayout root = rootView;
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();
        height = dm.heightPixels / 2;

        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        int xDest = dm.widthPixels / 2;
        xDest -= (view.getMeasuredWidth() / 2);
        int yDest = dm.heightPixels / 2 - (view.getMeasuredHeight() / 2) - statusBarOffset;
//        yDest -= 100;

        TranslateAnimation anim = new TranslateAnimation(0, xDest - originalPos[0], 0, yDest - originalPos[1]);
        anim.setDuration(4000);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }


}
