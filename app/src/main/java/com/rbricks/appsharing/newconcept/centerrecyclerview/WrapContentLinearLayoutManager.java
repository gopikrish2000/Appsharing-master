package com.rbricks.appsharing.newconcept.centerrecyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

// https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
public class WrapContentLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = WrapContentLinearLayoutManager.class.getSimpleName();

//    private static TriState consumeIOOBE = TriState.UNSET;

    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                          int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(TAG + "  index out of bounds : " + e.getLocalizedMessage());
        }
        /*if (consumeIOOBException()) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException ioobe) {
//                Logger.e(TAG, "index out of bounds : ", ioobe);
                System.out.println(TAG + "  index out of bounds : " + ioobe);
            }
        } else {
            super.onLayoutChildren(recycler, state);
        }*/
    }

    /*private static boolean consumeIOOBException() {
        if (!consumeIOOBE.isSet()) {
            consumeIOOBE = TriState.valueOf(HikeSharedPreferenceUtil.getInstance().getDataSafe(SharedPrefConst.SP_CATCH_IOOBE_EXCEPTION, true));
        }
        return consumeIOOBE.asBoolean(true);
    }*/
}