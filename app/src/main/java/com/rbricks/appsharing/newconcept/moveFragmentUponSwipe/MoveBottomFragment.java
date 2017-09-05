package com.rbricks.appsharing.newconcept.moveFragmentUponSwipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rbricks.appsharing.R;

/**
 * Created by gopi on 05/09/17.
 */

public class MoveBottomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_bottom, container, false);
        return view;
    }
}
