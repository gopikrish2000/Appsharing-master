package com.rbricks.appsharing.concept.scaleaview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.ArrayList;
import java.util.List;

public class ScaleAViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout optionsParent;
    private ScaleAViewAdapter scaleAViewAdapter;
    private GridLayoutManager layoutManager;
    private List<ScaleAViewItem> itemList;
    private boolean isFirstTimeOnLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_aview);
        initViews();
        initLogic();
    }

    private void initViews() {
        recyclerView = ((RecyclerView) findViewById(R.id.scale_recyclerview_rv));
        optionsParent = ((LinearLayout) findViewById(R.id.scale_custom_option_list));
    }

    private void initLogic() {
        getOptionViewFor();
        getOptionViewFor();
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        itemList = getData();
        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_spacing));
        scaleAViewAdapter = new ScaleAViewAdapter(itemList);
        recyclerView.setAdapter(scaleAViewAdapter);
        isFirstTimeOnLaunch = true;

       /* new ViewTreeObserver.OnGlobalLayoutListener(){

        }*/

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFirstTimeOnLaunch) {
                    return;
                }
                isFirstTimeOnLaunch = false;
                boolean isNotScrollable = isNotScrollable();
                int recyclerViewHeight = recyclerView.getHeight();
                int itemHeight = (recyclerViewHeight /2);
                int dy = recyclerView.computeVerticalScrollExtent();
                int diff = Math.abs(recyclerViewHeight - dy);
                float heightratio = ((float)recyclerViewHeight / (recyclerViewHeight + diff));
                System.out.println("recyclerViewHeight " + recyclerViewHeight + " dy = " + dy + " diff "+ diff+ " heightratio " + heightratio);

                /*int heightDiff = recyclerView.getHeight() - mItemListView.getHeight();
                int pos = ((LinearLayoutManager) mItemListView.getLayoutManager()).findLastVisibleItemPosition();
                if (pos < mAdapter.getItemCount())
                    mParentScrollView.smoothScrollTo(0, mItemListView.getHeight() + 10);*/
                if (!isNotScrollable) {
                    scaleAViewAdapter = new ScaleAViewAdapter(itemList);
                    scaleAViewAdapter.scaleOfItem = heightratio;
                    scaleAViewAdapter.heightOfItem = itemHeight;
                    recyclerView.setAdapter(scaleAViewAdapter);
                }

            }
        });
    }

    private boolean isNotScrollable() {
        return layoutManager.findLastCompletelyVisibleItemPosition() + 1 == itemList.size();
    }

    private List<ScaleAViewItem> getData() {
        List<ScaleAViewItem> list = new ArrayList<>();
        list.add(new ScaleAViewItem("title1", "subtitle1"));
        list.add(new ScaleAViewItem("title2", "subtitle2"));
        list.add(new ScaleAViewItem("title3", "subtitle3"));
        list.add(new ScaleAViewItem("title4", "subtitle4"));
        return list;
    }

    private void getOptionViewFor() {
        View view = getLayoutInflater().inflate(R.layout.location_suggetion_custom_option_layout, null);
        String abc = "abc";
        ((TextView) view.findViewById(R.id.text)).setText(abc);
        optionsParent.addView(view);
    }
}
