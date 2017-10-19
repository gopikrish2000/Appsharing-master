package com.rbricks.appsharing.concept.resizablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;

import java.util.ArrayList;
import java.util.List;

public class ResizableRecyclerviewActivity extends AppCompatActivity {

    private ArrayList<String> values;
    private EditText editText;
    private RecyclerView resizeRv;
    private ResizableRecyclerviewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resizable_recyclerview);
        initLayout();
        initValues();
    }

    private void initLayout() {
        editText = (EditText) findViewById(R.id.resize_et);
        resizeRv = (RecyclerView) findViewById(R.id.resize_rv);
    }

    private void initValues() {
        values = new ArrayList<>();
        values.addAll(getValues(3));
        resizeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        new StaggeredGridLayoutManager()
//        new GridLayoutManager()
        int parentWidth = this.getResources().getDisplayMetrics().widthPixels;
        Toast.makeText(this, " width in pixels is " + parentWidth, Toast.LENGTH_LONG).show();
        adapter = new ResizableRecyclerviewAdapter(values, parentWidth);
        resizeRv.setAdapter(adapter);
        RxView.clicks(findViewById(R.id.resize_btn)).subscribe(s -> {
            values.clear();
            values.addAll(getValues(Integer.parseInt(editText.getText().toString())));
//            adapter.notifyDataSetChanged();
            adapter = new ResizableRecyclerviewAdapter(values, parentWidth);
            resizeRv.setAdapter(adapter);
        });
    }

    private List<String> getValues(int size) {
        List<String> list = new ArrayList<>();
        String initialPartUrl = "https://dummyimage.com/500x500/bf9dbf/0011ff&text=";
        if (size == 1) {
            list.add(initialPartUrl + "1");
        } else if (size == 2) {
            list.add(initialPartUrl + "1");
            list.add(initialPartUrl + "2");
        } else {
            for (int i = 1; i <= size; i++) {
                list.add(initialPartUrl + i + "");
            }
        }
        return list;
    }
}
