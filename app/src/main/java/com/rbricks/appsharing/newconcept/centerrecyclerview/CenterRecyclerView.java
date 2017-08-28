package com.rbricks.appsharing.newconcept.centerrecyclerview;

import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;

import com.rbricks.appsharing.concept.Application.AppSharingApplication;
import com.rbricks.appsharing.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gopi on 12/10/17.
 */
public class CenterRecyclerView {

    private List<CenterRecyclerViewItem> itemList;

    private CenterRecyclerViewSelectListener centerViewSelectListener;

    RecyclerView recyclerView;

    private CenterRecyclerViewAdapter centerRecyclerViewAdapter;

    private int viewWidth;

    private float itemWidth;

    private float firstItemWidth, allPixelsMove;

    private int selectedItem;

    private static int ITEM_MARGIN_IN_DP = 8;

    private static int ITEM_WIDTH_IN_DP = 70;

    public static final String NONE = "NONE";

    private int initialSelectedItem = 0;

    private String mSource;

//    private String scrollSource = HikeConstants.SWIPE_MASK;

    private int oldItem = -1;

    private long time = 0;


    public CenterRecyclerView(View recyclerView, CenterRecyclerViewSelectListener centerViewSelectListener, List<CenterRecyclerViewItem> itemList) {
        this.recyclerView = (RecyclerView) recyclerView;
        this.centerViewSelectListener = centerViewSelectListener;
        this.itemList = new ArrayList<>();
//        createItemListFromGivenList(itemList);
        this.itemList.addAll(itemList);
        createRecyclerView();
        selectedItem = 1;
        setVisibility(View.GONE);
        time = System.currentTimeMillis();
    }

    public CenterRecyclerView(View recyclerView, CenterRecyclerViewSelectListener centerViewSelectListener, List<CenterRecyclerViewItem> itemList, int initialSelectedItem, String mSource) {
        this(recyclerView, centerViewSelectListener, itemList);
        this.initialSelectedItem = initialSelectedItem;
        this.mSource = mSource;
    }

   /* private void createItemListFromGivenList(List<CenterRecyclerViewItem> itemList) {
        this.itemList.clear();
        this.itemList.add(new CenterRecyclerViewItem(true));
        for (CenterRecyclerViewItem item : itemList) {
            this.itemList.add(item);
        }
        this.itemList.add(new CenterRecyclerViewItem(true));
    }

    public void setItemList(List<CenterRecyclerViewItem> itemList) {
        createItemListFromGivenList(itemList);
        if (initialSelectedItem < 0 || initialSelectedItem > itemList.size() - 1) {
            initialSelectedItem = 0;
        }
        centerRecyclerViewAdapter.notifyDataSetChanged();
        time = System.currentTimeMillis();
    }*/

    public void setItemList(List<CenterRecyclerViewItem> itemList, int initialSelectedItem) {
//        createItemListFromGivenList(itemList);
        this.initialSelectedItem = initialSelectedItem;
        if (this.initialSelectedItem < 0 || this.initialSelectedItem > itemList.size() - 1) {
            this.initialSelectedItem = 0;
        }
        centerRecyclerViewAdapter.notifyDataSetChanged();
        time = System.currentTimeMillis();
    }

    public void setVisibility(final int visibility) {
        recyclerView.setVisibility(visibility);
        if (visibility == View.GONE) {
            return;
        }
        if (visibility == View.VISIBLE) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    int posDiff = initialSelectedItem + 1 - selectedItem;
                    recyclerView.scrollBy((int) (itemWidth * posDiff), 0);
                    allPixelsMove = itemWidth * initialSelectedItem;
                    selectedItem = initialSelectedItem + 1;
                    if (centerViewSelectListener != null) {
                        centerViewSelectListener.onSelect(selectedItem - 1);
                    }
                    time = System.currentTimeMillis();
                }
            });

        }
    }

    public int getVisibility() {
        return recyclerView.getVisibility();
    }

    public void scrollByItem(int count) {
        if (selectedItem + count > itemList.size() - 2 || selectedItem + count < 1) {
            return;
        }
//        scrollSource = HikeConstants.SWIPE_LIVE_SCREEN;
        int dx = (int) (itemWidth * (count));
        recyclerView.smoothScrollBy(dx, 0);

        /*HikeCamUtils.recordCenterRecyclerViewEvent(scrollSource, mSource, HikeCamUtils.getCurrentCamSwitch(), Utils.getDeviceOrientationString(HikeMessengerApp.getInstance().getApplicationContext()), getMaskNameByPath(selectedItem), getMaskNameByPath(selectedItem + count), System.currentTimeMillis() - time, selectedItem == itemList.size() - 2, HikeCamUtils.sessionId);*/
        time = System.currentTimeMillis();
        selectedItem = selectedItem + count;
    }

    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPosition = Math.round((allPixelsMove) / itemWidth);
        selectedItem = expectedPosition + 1;
        scrollListToPosition(recyclerView, expectedPosition);
      // scrollByItem(1);
    }

    public void scrollListToPosition(RecyclerView recyclerView, int expectedPosition) {
        float targetScrollPos = expectedPosition * itemWidth;
        float missingPx = targetScrollPos - allPixelsMove;
        if (missingPx >= 1 || missingPx <= -1) {
//            recyclerView.smoothScrollBy((int) missingPx, 0);
            smoothScrollBy((int) missingPx);
        } else if (centerViewSelectListener != null) {
            centerViewSelectListener.onSelect(expectedPosition);
        }
    }

   /* public void scrollListToPosition(int expectedPosition) {
        float targetScrollPos = expectedPosition * itemWidth;
        float missingPx = targetScrollPos - allPixelsMove;
        if (missingPx >= 1 || missingPx <= -1) {
            recyclerView.smoothScrollBy((int) missingPx, 0);
        } else if (centerViewSelectListener != null) {
            centerViewSelectListener.onSelect(expectedPosition);
        }
    }*/

    /*public String getMaskNameByPath(int position) {
        Logger.getLogger("s").log(Level.ALL, "CenterRecyclerView", position + "");
        if (position < 1 || position > itemList.size() - 2) {
            return null;
        }
        String[] strings = itemList.get(position).getPath().split("/");
        return strings[strings.length - 2];
    }*/

    public int getCurrentPosition() {
        return selectedItem - 1;
    }

    public void createRecyclerView() {
        ViewTreeObserver vto = recyclerView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                viewWidth = CommonUtils.getDeviceWidth();
                itemWidth = Math.round(ITEM_WIDTH_IN_DP * CommonUtils.DensityMultiplier) + (2 * Math.round(ITEM_MARGIN_IN_DP * CommonUtils.DensityMultiplier));
                firstItemWidth = (viewWidth - itemWidth) / 2;
                allPixelsMove = 0;
                final LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(AppSharingApplication.getInstance().getApplicationContext()) {
                    @Override
                    public boolean canScrollHorizontally() {
                        return !isRecording;
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
                };
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        synchronized (this) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                calculatePositionAndScroll(recyclerView);
                                /*if (HikeConstants.SWIPE_MASK.equals(scrollSource)) {
                                    HikeCamUtils.recordCenterRecyclerViewEvent(scrollSource, mSource, HikeCamUtils.getCurrentCamSwitch(), Utils.getDeviceOrientationString(HikeMessengerApp.getInstance().getApplicationContext()), getMaskNameByPath(oldItem), getMaskNameByPath(selectedItem), System.currentTimeMillis() - time, selectedItem == itemList.size() - 2, HikeCamUtils.sessionId);
                                    time = System.currentTimeMillis();
                                }
                                scrollSource = HikeConstants.SWIPE_MASK;*/
                            }
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        allPixelsMove += dx;
                        oldItem = selectedItem;
                    }
                });
                centerRecyclerViewAdapter = new CenterRecyclerViewAdapter(captureBtnOnTouchListener, itemList, (int) firstItemWidth);
                recyclerView.setAdapter(centerRecyclerViewAdapter);
//                SnapHelper snapHelper = new PagerSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerView);  to make it work like ViewPager but is not working smoothly.
                return true;
            }
        });
    }

    public void startAnimation(Animation rightToLeftAnimation) {
        recyclerView.startAnimation(rightToLeftAnimation);
    }

    public void clearAnimation() {
        recyclerView.clearAnimation();
    }

    /*public String getCurrentMaskFilter() {
        return getMaskNameByPath(selectedItem);
    }*/

    public void onClick(View v) {
//        scrollSource = HikeConstants.TAP_MASK;
        int dx = (int) (itemWidth * ((int) v.getTag() - selectedItem));
//        recyclerView.smoothScrollBy(dx, 0);
        smoothScrollBy(dx);
        if (centerViewSelectListener != null && selectedItem == ((int) v.getTag())) {
            centerViewSelectListener.onConfirmed(v, (int) v.getTag() - 1);
        }
        /*HikeCamUtils.recordCenterRecyclerViewEvent(scrollSource, mSource, HikeCamUtils.getCurrentCamSwitch(), Utils.getDeviceOrientationString(HikeMessengerApp.getInstance().getApplicationContext()), getMaskNameByPath(selectedItem), getMaskNameByPath((int) v.getTag()), System.currentTimeMillis() - time, selectedItem == itemList.size() - 2, HikeCamUtils.sessionId);*/
        time = System.currentTimeMillis();
        selectedItem = (int) v.getTag();
    }

    private void smoothScrollBy(int dx) {
        /*ValueAnimator realSmoothScrollAnimation =
        ValueAnimator.ofInt(recyclerView.getScrollX(), recyclerView.getScrollX()+dx);
    realSmoothScrollAnimation.setDuration(2000);
    realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
        @Override
        public void onAnimationUpdate(ValueAnimator animation)
        {
            int scrollTo = (Integer) animation.getAnimatedValue();
            recyclerView.smoothScrollBy(scrollTo, 0);
        }
    });

    realSmoothScrollAnimation.start();*/
        recyclerView.smoothScrollBy(dx, 0);
//        ObjectAnimator.ofInt(recyclerView, "scrollX", dx).setDuration(2000).start();

        /*final Handler timerHandler = new Handler();
    final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            recyclerView.smoothScrollBy(0,5);         // 5 is how many pixels you want it to scroll vertically by
            timerHandler.postDelayed(this, 10);     // 10 is how many milliseconds you want this thread to run
        }
    };*/

    }

    private boolean isRecording;
    private View.OnTouchListener captureBtnOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };
   /* private View.OnTouchListener captureBtnOnTouchListener = new View.OnTouchListener() {
        private float downX;
        public float downY;
        final int videoTriggerMs = 400;
        long touchDownMs = 0;
        boolean isTouchDown = false;

        private void reset() {
            touchDownMs = 0;
            isTouchDown = false;
            isRecording = false;
        }

        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isTouchDown = true;
                    touchDownMs = System.currentTimeMillis();
                    downX = event.getX();
                    downY = event.getY();
                    if (((int) v.getTag()) == selectedItem) {
                        HikeHandlerUtil.getInstance().postRunnableWithDelay(new VerifyTouchDownRunnable(touchDownMs), videoTriggerMs);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    if (isRecording) {
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                centerViewSelectListener.onSelectedItemLongTouchUp(selectedItem);
                                centerRecyclerViewAdapter.hideAllExceptPosn(-1);
                            }
                        });
                    } else {
                        onClick(v);
                    }
                    reset();
                    return true;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_MOVE:
                    if (!isRecording) {
                        //Check if moving out of bounds?
                        if ((Math.abs(event.getX() - downX) > Utils.dpToPx(20)) || (Math.abs(event.getY() - downY) > Utils.dpToPx(20))) {
                            reset();
                        }
                    }
                    break;
            }
            return false;
        }

        class VerifyTouchDownRunnable implements Runnable {
            private final long mId;

            public VerifyTouchDownRunnable(long id) {
                mId = id;
            }

            @Override
            public void run() {
                if (touchDownMs == mId && isTouchDown) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            centerViewSelectListener.onSelectedItemLongTouchDown(selectedItem);
                            centerRecyclerViewAdapter.hideAllExceptPosn(selectedItem);
                            isRecording = true;
                        }
                    });
                }
            }
        }
    };*/

    public interface CenterRecyclerViewSelectListener {
        void onSelect(int position);

        void onConfirmed(View v, int position);

        void onSelectedItemLongTouchDown(int position);

        void onSelectedItemLongTouchUp(int position);
    }
}
