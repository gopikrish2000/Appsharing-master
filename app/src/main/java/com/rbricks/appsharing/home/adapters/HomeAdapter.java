package com.rbricks.appsharing.home.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.home.domains.HomeItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.subjects.PublishSubject;

import static com.jakewharton.rxbinding2.view.RxView.clicks;


/**
 * Created by gopikrishna on 11/11/16.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MainViewHolder> {

    private List<HomeItem> HomeItemList;
    public PublishSubject<Pair<HomeItem, Integer>> itemClickSubject;

    public HomeAdapter(List<HomeItem> HomeItemList) {
        this.HomeItemList = HomeItemList;
        itemClickSubject = PublishSubject.create();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_listing_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        HomeItem HomeItem = HomeItemList.get(position);
        holder.itemTitle.setText(HomeItem.title);
        holder.itemDescription.setText(HomeItem.description);
        holder.itemDate.setText("");

        clicks(holder.parent).throttleFirst(1, TimeUnit.SECONDS).subscribe(s -> {
            itemClickSubject.onNext(Pair.create(HomeItem, holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return HomeItemList.size();
    }

    class MainViewHolder extends ViewHolder {
        private TextView itemTitle;
        private TextView itemDate;
        private TextView itemDescription;
        private View parent;

        MainViewHolder(View view) {
            super(view);
            itemTitle = (TextView) view.findViewById(R.id.item_title);
            itemDate = (TextView) view.findViewById(R.id.item_date);
            itemDescription = (TextView) view.findViewById(R.id.item_description);
            parent = view;
        }
    }
}
