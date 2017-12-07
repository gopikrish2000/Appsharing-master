package com.rbricks.appsharing.newconcept.debounceWithoutRxJava;

import android.os.SystemClock;
import android.view.View;

/**
 * A Debounced OnClickListener
 * Rejects clicks that are too close together in time.
 */
public abstract class DebouncedOnClickListener implements View.OnClickListener {

    private long threshold;
    private long lastClickMillis;

    /**
     * Implement this in your subclass instead of onClick
     * @param v The view that was clicked
     */
    public abstract void onDebouncedClick(View v);

    public DebouncedOnClickListener() {
        threshold = 400l;
    }

    /**
     * @param threshold The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    public DebouncedOnClickListener(long threshold) {
        this.threshold = threshold;
    }

    @Override
    public void onClick(View clickedView) {
        long currentTimestamp = SystemClock.uptimeMillis();

        if ((currentTimestamp - lastClickMillis > threshold)) {
            onDebouncedClick(clickedView);
        }
        lastClickMillis = currentTimestamp;
    }

    static View.OnClickListener wrap(final View.OnClickListener onClickListener) {
        return new DebouncedOnClickListener() {
            @Override
            public void onDebouncedClick(View v) {
                onClickListener.onClick(v);
            }
        };
    }
}