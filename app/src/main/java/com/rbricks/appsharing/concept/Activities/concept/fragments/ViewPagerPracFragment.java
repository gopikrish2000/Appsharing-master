package com.rbricks.appsharing.concept.Activities.concept.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rbricks.appsharing.R;

/**
 * Created by gopikrishna on 6/6/16.
 */
public class ViewPagerPracFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    public ViewPagerPracFragment() {

    }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.view_pager_fragment_prac, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.sampleTextview)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }
