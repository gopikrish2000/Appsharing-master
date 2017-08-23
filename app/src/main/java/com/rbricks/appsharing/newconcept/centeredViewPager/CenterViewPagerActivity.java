package com.rbricks.appsharing.newconcept.centeredViewPager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewItem;
import com.rbricks.appsharing.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class CenterViewPagerActivity extends AppCompatActivity {

    private static final float ITEM_WIDTH_IN_DP = 60;
    private static final float ITEM_MARGIN_IN_DP = 0;
    private ViewPager centerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_view_pager);
        centerViewPager = ((ViewPager) findViewById(R.id.center_rp));

         List<CenterRecyclerViewItem> list = new ArrayList<>(3);
        list.add(new CenterRecyclerViewItem(true));
        list.add(new CenterRecyclerViewItem("VIDEO"));
        list.add(new CenterRecyclerViewItem("CAMERA"));
        list.add(new CenterRecyclerViewItem("TEXT"));
//        list.add(new CenterRecyclerViewItem("TEXT1"));
//        list.add(new CenterRecyclerViewItem("TEXT2"));
        list.add(new CenterRecyclerViewItem(true));

        int viewWidth = CommonUtils.getDeviceWidth();
        int itemWidth = Math.round(ITEM_WIDTH_IN_DP * CommonUtils.DensityMultiplier) + (2 * Math.round(ITEM_MARGIN_IN_DP * CommonUtils.DensityMultiplier));
        int firstItemWidth = (viewWidth - itemWidth) / 2;
        PagerAdapter pagerAdapter = new CenteredPagerAdapter(this, list, firstItemWidth);
        centerViewPager.setAdapter(pagerAdapter);
//        centerViewPager.setPageMargin(-1 * CommonUtils.dpToPx(64));
// Setting negative page margin will give space between the Pager items.
    }
}
