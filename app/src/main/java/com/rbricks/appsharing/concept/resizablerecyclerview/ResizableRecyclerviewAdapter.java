package com.rbricks.appsharing.concept.resizablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.resizablerecyclerview.ResizableRecyclerviewAdapter.ResizableViewHolder;

import java.util.List;

/**
 * Created by gopikrishna on 03/02/17.
 */

public class ResizableRecyclerviewAdapter extends RecyclerView.Adapter<ResizableViewHolder> {

    List<String> list;
    private int parentWidth;

    public ResizableRecyclerviewAdapter(List<String> values, int width) {
        list = values;
        this.parentWidth = width;
    }

    @Override
    public ResizableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (list.size() == 1) {
//            resizableViewHolder.parentView.getLayoutParams().width = parentWidth;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resize_recyclerview_singleitem, parent, false);
        } else if (list.size() == 2) {
//            resizableViewHolder.parentView.getLayoutParams().width = ((int)parentWidth/2);
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resize_recyclerview_twoitem, parent, false);
            Toast.makeText(parent.getContext(), (parentWidth / 2) + "", Toast.LENGTH_LONG).show();
            view.findViewById(R.id.resize_item_parent).getLayoutParams().width = parentWidth / 2;   // set width always in Pixels.
        } else {
//            resizableViewHolder.parentView.getLayoutParams().width = 100;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resize_recyclerview_item, parent, false);
        }
        ResizableViewHolder resizableViewHolder = new ResizableViewHolder(view);

        return resizableViewHolder;
    }

    @Override
    public void onBindViewHolder(ResizableViewHolder holder, int position) {
        String url = list.get(position);
        Glide.with(holder.imageView.getContext()).load(url).placeholder(R.drawable.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ResizableViewHolder extends RecyclerView.ViewHolder {
        private View parentView;
        private ImageView imageView;

        public ResizableViewHolder(View itemView) {
            super(itemView);
            parentView = itemView.findViewById(R.id.resize_item_parent);
            imageView = ((ImageView) itemView.findViewById(R.id.resize_item_iv));
        }

//        abstract void populate(ResizableViewHolder holder, int position, )
    }
}
