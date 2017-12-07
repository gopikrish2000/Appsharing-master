package com.rbricks.appsharing.newconcept.debounceWithoutRxJava;

import android.view.View;

import com.rbricks.appsharing.utils.CommonUtils;

/**
 * Created by gopi on 14/11/17.
 */

public class DebounceClick {

    private final View view;
    private final View.OnClickListener clickListener;

    public DebounceClick(View view, View.OnClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    public void clicks() {
        view.setOnClickListener(DebouncedOnClickListener.wrap(clickListener));
    }

    public static void clicksOther(View view, View.OnClickListener clickListener){
        if (CommonUtils.nonNull(view)) {
            view.setOnClickListener(DebouncedOnClickListener.wrap(clickListener));
        }
    }
}
