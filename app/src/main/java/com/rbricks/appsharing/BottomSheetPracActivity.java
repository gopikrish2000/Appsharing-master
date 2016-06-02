package com.rbricks.appsharing;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BottomSheetPracActivity extends ActionBarActivity implements View.OnClickListener {

    BottomSheetBehavior<View> mBottomSheetBehavior;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_prac);
        View bottomSheet = findViewById( R.id.bottom_sheet );
        Button button1 = (Button) findViewById( R.id.button_1 );
        Button button2 = (Button) findViewById( R.id.button_2 );
        Button button3 = (Button) findViewById( R.id.button_3 );

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(BottomSheetPracActivity.this, "Layout Refreshing", Toast.LENGTH_LONG).show();
//                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(300);
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                System.out.println("state which is getting changed is = " + newState);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                    Toast.makeText(BottomSheetPracActivity.this, "Write Refresh Logic", Toast.LENGTH_LONG);
//                    System.out.println(" Do Refresh Code " );
//                    mBottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                if (slideOffset == 0) {
//                    Toast.makeText(BottomSheetPracActivity.this, "Write Refresh Logic", Toast.LENGTH_LONG);
                    System.out.println(" Do Refresh Code in Behaviour" );
                    mSwipeRefreshLayout.setRefreshing(true);

//                    mBottomSheetBehavior.setPeekHeight(0);

                }
                System.out.println("Slide got called = " + slideOffset);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.button_1: {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            }
        }
    }
}
