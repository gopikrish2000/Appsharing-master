package com.rbricks.appsharing.newconcept.centerrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rbricks.appsharing.R;

import java.util.ArrayList;
import java.util.List;

public class CenterRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView centerRecyclerViewRv;
    private CenterRecyclerView centerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_recycler_view);

        centerRecyclerViewRv = ((RecyclerView) findViewById(R.id.center_rv));

        List<CenterRecyclerViewItem> list = new ArrayList<>(3);
        list.add(new CenterRecyclerViewItem(true));
        list.add(new CenterRecyclerViewItem("VIDEO"));
        list.add(new CenterRecyclerViewItem("CAMERA"));
        list.add(new CenterRecyclerViewItem("TEXT"));
//        list.add(new CenterRecyclerViewItem("TEXT1"));
//        list.add(new CenterRecyclerViewItem("TEXT2"));
        list.add(new CenterRecyclerViewItem(true));

        centerRecyclerView = new CenterRecyclerView(centerRecyclerViewRv,
                new CenterRecyclerView.CenterRecyclerViewSelectListener() {
                    @Override
                    public void onSelect(int position) {
                        System.out.println("on item selected " + position);
                    }

                    @Override
                    public void onConfirmed(View v, int position) {

                    }

                    @Override
                    public void onSelectedItemLongTouchDown(int position) {

                    }

                    @Override
                    public void onSelectedItemLongTouchUp(int position) {

                    }}, list, 1, "source");

        centerRecyclerView.setVisibility(View.VISIBLE);
//        centerRecyclerView.setItemList(list, 1);
    }
}
