package com.rbricks.appsharing.concept.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rbricks.appsharing.R;

public class LifeCycleSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_second);
        System.out.println("LifeCycleSecondActivity.onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("LifeCycleSecond***.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("LifeCycleSecond***.onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("LifeCycleSecond***.onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("LifeCycleSecondActivity.onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("LifeCycleSecond***.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LifeCycleSecond***.onDestroy");
    }
}
