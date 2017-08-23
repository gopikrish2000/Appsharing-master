package com.rbricks.appsharing.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewItem;

import java.util.List;

/**
 * Created by gopi on 23/08/17.
 */

public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<CenterRecyclerViewItem> itemList;

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public ItemViewHolder(View view) {
            super(view);
        }
    }

    public EmptyRecyclerViewAdapter(List<CenterRecyclerViewItem> itemList) {
        super();
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        CenterRecyclerViewItem centerRecyclerViewItem = itemList.get(position);
        return 0;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resize_recyclerview_singleitem,
                    parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 0) {
            ItemViewHolder centerItemViewHolder = (ItemViewHolder) holder;
            CenterRecyclerViewItem centerRecyclerViewItem = itemList.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
