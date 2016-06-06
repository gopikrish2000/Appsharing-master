package com.rbricks.appsharing.concept.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rbricks.appsharing.R;

public class LifeCycleFirstActivity extends AppCompatActivity {

    /* Complete Execution with fragment from FirstActivity to Second
    *   LifeCycleFirstActivity.onCreate Completed Successfully
        LifeCycleFragment.onAttach
        LifeCycleFragment.onCreate
        LifeCycleFragment.onCreateView
        LifeCycleFragment.onActivityCreated

        LifeCycleFirstActivity.onStart
        LifeCycleFragment.onStart
        LifeCycleFirstActivity.onResume
        LifeCycleFragment.onResume

        LifeCycleFragment.onPause
        LifeCycleFirstActivity.onPause

        //Second activity starting
        LifeCycleSecondActivity***.onCreate
        LifeCycleSecond***.onStart
        LifeCycleSecond***.onResume

        // After OnResume of Second first is stopped
        LifeCycleFirstActivity.onSaveInstanceState
        LifeCycleFragment.onStop
        LifeCycleFirstActivity.onStop
    *
    * */

    /*
    * 1. The order of Execution is FirstOnPause , SecondOnCreate - OnStart - OnResume , FirstOnSaveInstanceState , FirstResume
    * 2. You should avoid performing CPU-intensive work during onPause(), such as writing to a database, because it can slow the visible transition to the next activity (you should instead perform heavy-load shutdown operations during onStop()).
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle_first);
        ((Button) findViewById(R.id.lifeCycleButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCycleFirstActivity.this, LifeCycleSecondActivity.class);
                startActivity(intent);
            }
        });

        LifeCycleFragment lifeCycleFragment = new LifeCycleFragment();
        getFragmentManager().beginTransaction().add(lifeCycleFragment, lifeCycleFragment.getClass().getName()).commit();
        System.out.println("LifeCycleFirstActivity.onCreate Completed Successfully");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("LifeCycleFirstActivity.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("LifeCycleFirstActivity.onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("LifeCycleFirstActivity.onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("LifeCycleFirstActivity.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("LifeCycleFirstActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("LifeCycleFirstActivity.onDestroy");
    }
}
