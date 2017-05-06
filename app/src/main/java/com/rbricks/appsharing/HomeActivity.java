package com.rbricks.appsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.rbricks.appsharing.bookingdotcom.FindViewByIdActivity;
import com.rbricks.appsharing.concept.customcanvasdrawing.CustomDrawingActivity;
import com.rbricks.appsharing.concept.scaleaview.ScaleAViewActivity;
import com.rbricks.appsharing.home.adapters.HomeAdapter;
import com.rbricks.appsharing.home.domains.HomeItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView mainRv;
    private ArrayList<HomeItem> homeItemList;
    private HomeAdapter homeAdapter;
//    private Unbinder unBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        unBinder = ButterKnife.bind(this);
        initViews();
        initData();
    }

    private void initViews() {
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        mainRv = ((RecyclerView) findViewById(R.id.main_rv));
    }

    private void initData() {
        homeItemList = new ArrayList<>();
        fillHomeItems();
        homeAdapter = new HomeAdapter(homeItemList);
        mainRv.setLayoutManager(new LinearLayoutManager(this));
        mainRv.setAdapter(homeAdapter);
        homeAdapter.itemClickSubject.subscribe(s -> {
            Intent intent = new Intent(this, s.first.activityClass);
            startActivity(intent);
        });

    }

    private void fillHomeItems() {
        /*homeItemList.add(new HomeItem("Notes Listing", NotesListingActivity.class));
        homeItemList.add(new HomeItem("New Notes Listing", NewNotesListingActivity.class));
        homeItemList.add(new HomeItem("BrindaAnimationActivity", BrindaAnimationActivity.class));
        homeItemList.add(new HomeItem("RxJavaAdvancedActivity", RxJavaAdvancedActivity.class));
        homeItemList.add(new HomeItem("StreamsActivity", StreamsActivity.class));
        homeItemList.add(new HomeItem("PropertyAnimationActivity", PropertyAnimationActivity.class));
        homeItemList.add(new HomeItem("AdvancedAnimationActivity", AdvancedAnimationActivity.class));
        homeItemList.add(new HomeItem("ServiceTestActivity", ServiceTestActivity.class));
        homeItemList.add(new HomeItem("TransitionDetailsActivity", TransitionDetailsActivity.class));
        homeItemList.add(new HomeItem("ConstraintActivity", ConstraintLayoutActivity.class));
        homeItemList.add(new HomeItem("AlaramManagerActivity", AlaramManagerActivity.class));
        homeItemList.add(new HomeItem("BitmapMemoryEfficientActivity", BitmapMemoryActivity.class));
        homeItemList.add(new HomeItem("ResizableRecyclerviewActivity", ResizableRecyclerviewActivity.class));
        homeItemList.add(new HomeItem("CustomLambdaReferenceActivity", CustomLambdaReferenceActivity.class));
        homeItemList.add(new HomeItem("KeyboardEventsActivity", KeyboardEventsActivity.class));*/

        homeItemList.add(new HomeItem("CustomDrawingActivity", CustomDrawingActivity.class));
        homeItemList.add(new HomeItem("ScaleAViewActivity", ScaleAViewActivity.class));
        homeItemList.add(new HomeItem("FindViewByIdActivity", FindViewByIdActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unBinder.unbind();
    }
}
