package com.rbricks.appsharing.newconcept.snappyRecyclerView;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.Application.AppSharingApplication;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewAdapter;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewItem;
import com.rbricks.appsharing.newconcept.centerrecyclerview.WrapContentLinearLayoutManager;
import com.rbricks.appsharing.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class SnappyRecyclerViewActivity extends AppCompatActivity implements View.OnTouchListener {

    private SnappyRecyclerView snappyRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snappy_recycler_view);

        snappyRv = ((SnappyRecyclerView) findViewById(R.id.snappy_rv));

        List<CenterRecyclerViewItem> list = new ArrayList<>(3);
        list.add(new CenterRecyclerViewItem(true));
        list.add(new CenterRecyclerViewItem("VIDEO"));
        list.add(new CenterRecyclerViewItem("CAMERA"));
        list.add(new CenterRecyclerViewItem("TEXT"));
//        list.add(new CenterRecyclerViewItem("TEXT1"));
//        list.add(new CenterRecyclerViewItem("TEXT2"));
        list.add(new CenterRecyclerViewItem(true));

        int viewWidth = CommonUtils.getDeviceWidth();
        int itemWidth = Math.round(70 * CommonUtils.DensityMultiplier) + (2 * Math.round(8 * CommonUtils.DensityMultiplier));
        int firstItemWidth = (viewWidth - itemWidth) / 2;
        CenterRecyclerViewAdapter adapter = new CenterRecyclerViewAdapter(this, list, firstItemWidth);

       /* final LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(AppSharingApplication.getInstance().getApplicationContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                        super.smoothScrollToPosition(recyclerView, state, position);
                final LinearSmoothScroller linearSmoothScroller =
                        new LinearSmoothScroller(recyclerView.getContext()) {
                            private static final float MILLISECONDS_PER_INCH = 10000f;

                            @Override
                            public PointF computeScrollVectorForPosition(int targetPosition) {
                                return super.computeScrollVectorForPosition(targetPosition);
                            }

                            @Override
                            protected float calculateSpeedPerPixel
                                    (DisplayMetrics displayMetrics) {
                                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                            }
                        };
                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            }
        };*/
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        snappyRv.setLayoutManager(layoutManager);
        snappyRv.setAdapter(adapter);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
