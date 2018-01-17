package com.rbricks.appsharing.concept.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.notesapp.utils.ViewUtils;

import static com.jakewharton.rxbinding2.view.RxView.clicks;

public class QuickTestActivity extends AppCompatActivity {

    private RelativeLayout firstParentRl;
    private ImageView firstIv;
    private TextView firstTv;
    private View firstBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_test);
        firstParentRl = (RelativeLayout) findViewById(R.id.firstParentRl);
        firstParentRl.setAlpha(0.4f);
        firstIv = (ImageView) findViewById(R.id.first_iv);
        firstTv = (TextView) findViewById(R.id.first_tv);
        firstBtn = findViewById(R.id.first_btn);
        clicks(firstBtn).subscribe(s -> {
            ViewUtils.viewVisibilityWithBoolean(firstParentRl, !ViewUtils.isViewVisible(firstParentRl));
        });
    }
}
