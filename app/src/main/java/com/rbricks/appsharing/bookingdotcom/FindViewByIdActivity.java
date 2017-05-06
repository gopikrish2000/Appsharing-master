package com.rbricks.appsharing.bookingdotcom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.rbricks.appsharing.R;

import static com.rbricks.appsharing.utils.CommonUtils.isNull;

public class FindViewByIdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_view_by_id);
        View rootView = findViewById(R.id.find_parent_rel);
        View resultView = myFindViewById(rootView, R.id.find_textview_tv);
        System.out.println("resultView = " + resultView);
    }

    private View myFindViewById(View rootView, int findId) {
        if (isNull(rootView)) {
            return null;
        }
        if (rootView.getId() == findId) {
            return rootView;
        }

        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) rootView;
            if (viewGroup.getChildCount() == 0) {
                return null;
            }

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                View returnView = myFindViewById(childView, findId);
                if (returnView != null) {
                    return returnView;
                }
            }
        }
        return null;
    }
}
