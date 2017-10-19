package com.rbricks.appsharing.concept.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;

public class LifeCycleSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_second);
        RxView.clicks(findViewById(R.id.lifeSecondBtn)).subscribe(s -> {
            setResult(RESULT_OK);
            finish();
        });
        System.out.println("LifeCycleSecondActivity.onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("LifeCycleSecondActivity.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("LifeCycleSecondActivity.onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("LifeCycleSecondActivity.onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("LifeCycleSecondActivity.onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("LifeCycleSecondActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LifeCycleSecondActivity.onDestroy");
    }
}
