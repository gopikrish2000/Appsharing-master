package com.rbricks.appsharing.concept.animations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.rbricks.appsharing.R;

/**  For this set the
  1. For both start & end Activities : use theme with <item name="android:windowContentTransitions">true</item>
 <item name="android:windowAllowReturnTransitionOverlap">true</item>
 <item name="android:windowAllowEnterTransitionOverlap">false</item>
  2. setTransitionName for a object in Start Activity & any other object with same setTransitionName in End Activity
  3. StartActivity n EndActivity Shared Elements need not have same Type etc ( but just transitionName same.
  4. ActivityOptions use makeSceneTransitionAnimation n send bundle
 */
public class ActivityTransitionAnimationActivity extends AppCompatActivity {

    private ImageView sharedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_transition_animation);
        sharedImageView = ((ImageView) findViewById(R.id.shared_img));
        sharedImageView.setTransitionName("sharedImage");

        RxView.clicks(sharedImageView).subscribe(s -> {
            //This is where the magic happens. makeSceneTransitionAnimation takes a context, view, a name for the target view.
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActivityTransitionAnimationActivity.this, sharedImageView, "sharedImage");
            Intent intent = new Intent(ActivityTransitionAnimationActivity.this, TransitionDetailsActivity.class);
            startActivity(intent, options.toBundle());
        });

    }
}
