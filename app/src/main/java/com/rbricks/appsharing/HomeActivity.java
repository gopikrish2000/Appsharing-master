package com.rbricks.appsharing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.rbricks.appsharing.architecture.MVP.MVPArchitectureActivity;
import com.rbricks.appsharing.architecture.MVVM.withDataBinding.MVVMArchitectureDataBindingActivity;
import com.rbricks.appsharing.architecture.MVVM.withoutDataBinding.MVVMArchitectureActivity;
import com.rbricks.appsharing.architecture.OpenGLES2.basic.OpenGl2BasicActivity;
import com.rbricks.appsharing.architecture.OpenGLES2.shapes.triangle.OpenGLTriangleActivity;
import com.rbricks.appsharing.architecture.dataBinding.DataBindingActivity;
import com.rbricks.appsharing.concept.animations.HikeFaceFilterAnimationActivity;
import com.rbricks.appsharing.home.adapters.HomeAdapter;
import com.rbricks.appsharing.home.domains.HomeItem;
import com.rbricks.appsharing.newconcept.audioNVideoconcepts.audio.AudioMixingActivity;
import com.rbricks.appsharing.newconcept.audioNVideoconcepts.audio.AudioPlaybackActivity;
import com.rbricks.appsharing.newconcept.audioNVideoconcepts.video.VideoPlaybackActivity;
import com.rbricks.appsharing.newconcept.centeredViewPager.CenterViewPagerActivity;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewActivity;
import com.rbricks.appsharing.newconcept.debounceWithoutRxJava.DebounceWithoutRxJavaActivity;
import com.rbricks.appsharing.newconcept.moveFragmentUponSwipe.MoveFragmentUponSwipeActivity;
import com.rbricks.appsharing.newconcept.snappyRecyclerView.SnappyRecyclerViewActivity;
import com.rbricks.appsharing.newconcept.toucheventhandling.TouchEventPropagationWithListenersActivity;

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
       /* homeItemList.add(new HomeItem("Notes Listing", NotesListingActivity.class));
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
        homeItemList.add(new HomeItem("KeyboardEventsActivity", KeyboardEventsActivity.class));

        homeItemList.add(new HomeItem("LifeCycleFirstActivity", LifeCycleFirstActivity.class));
        homeItemList.add(new HomeItem("CustomDrawingActivity", CustomDrawingActivity.class));
        homeItemList.add(new HomeItem("ScaleAViewActivity", ScaleAViewActivity.class));
        homeItemList.add(new HomeItem("FindViewByIdActivity", FindViewByIdActivity.class));
        homeItemList.add(new HomeItem("HandlerTestActivity", HandlerTestActivity.class));
        homeItemList.add(new HomeItem("PianoDrawActivity", PianoDrawActivity.class));
        homeItemList.add(new HomeItem("ClickAreaIncreaseActivity", ClickAreaIncreaseActivity.class));*/

      /*  homeItemList.add(new HomeItem("BasicCameraActivity", BasicCameraActivity.class));
        homeItemList.add(new HomeItem("AndroidCameraForwardActivity", AndroidCameraForwardActivity.class));
        homeItemList.add(new HomeItem("BasicVideoActivity", BasicVideoActivity.class));
        homeItemList.add(new HomeItem("TouchEventPropagationActivity", TouchEventPropagationActivity.class));*/
      /*
        homeItemList.add(new HomeItem("TouchEventPropagationWithListenersActivity", TouchEventPropagationWithListenersActivity.class));
        homeItemList.add(new HomeItem("CenterRecyclerViewActivity", CenterRecyclerViewActivity.class));
        homeItemList.add(new HomeItem("CenterViewPagerActivity", CenterViewPagerActivity.class));
        homeItemList.add(new HomeItem("SnappyRecyclerViewActivity", SnappyRecyclerViewActivity.class));
        homeItemList.add(new HomeItem("MoveFragmentUponSwipeActivity", MoveFragmentUponSwipeActivity.class));
        homeItemList.add(new HomeItem("AudioPlaybackActivity", AudioPlaybackActivity.class));
        homeItemList.add(new HomeItem("VideoPlaybackActivity", VideoPlaybackActivity.class));*/
        homeItemList.add(new HomeItem("AudioMixingActivity", AudioMixingActivity.class));
        homeItemList.add(new HomeItem("MVPArchitectureActivity", MVPArchitectureActivity.class));
        homeItemList.add(new HomeItem("MVVMArchitectureActivity", MVVMArchitectureActivity.class));
        homeItemList.add(new HomeItem("MVVMDataBinding", MVVMArchitectureDataBindingActivity.class));
        homeItemList.add(new HomeItem("DataBindingActivity", DataBindingActivity.class));
        homeItemList.add(new HomeItem("OpenGl2BasicActivity", OpenGl2BasicActivity.class));
        homeItemList.add(new HomeItem("OpenGLTriangleActivity", OpenGLTriangleActivity.class));
        homeItemList.add(new HomeItem("DebounceWithoutRxJavaActivity", DebounceWithoutRxJavaActivity.class));
        homeItemList.add(new HomeItem("HikeFaceFilterAnimationActivity", HikeFaceFilterAnimationActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unBinder.unbind();
    }
}
