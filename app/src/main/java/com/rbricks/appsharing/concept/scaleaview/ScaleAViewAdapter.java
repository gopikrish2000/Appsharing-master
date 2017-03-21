package com.rbricks.appsharing.concept.scaleaview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.List;

/**
 * Created by gopi on 21/03/17.
 */

public class ScaleAViewAdapter extends RecyclerView.Adapter<ScaleAViewAdapter.ScaleAViewHolder> {

    private final List<ScaleAViewItem> itemsList;
    public boolean isInvisible = false;
    public int heightOfItem = -1;
    public float scaleOfItem = -1.0f;

    public ScaleAViewAdapter(List<ScaleAViewItem> list) {
        itemsList = list;
    }

    @Override
    public ScaleAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scale_recyclerview_item, parent, false);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredHeight = view.getMeasuredHeight();
        float scaleY = ((float) heightOfItem) / measuredHeight;
        System.out.println("ScaleAViewAdapter.onCreateViewHolder measuredHeight " + measuredHeight + " heightOfItem "+ heightOfItem + " scaleY " + scaleY);
        if (heightOfItem > 0) {
//            view.setScaleY(scaleY);
            view.getLayoutParams().height = heightOfItem;
        }
        if (scaleOfItem > 0) {
//            view.setScaleY(scaleOfItem);
//            view.getLayoutParams().height = heightOfItem;
        }
        return new ScaleAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScaleAViewHolder holder, int position) {
        if (isInvisible) {
            holder.itemView.setVisibility(View.INVISIBLE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
        ScaleAViewItem item = itemsList.get(position);
        holder.scaleTitleTv.setText(item.title);
        holder.scaleSubtitleTv.setText(item.subTitle);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    static class ScaleAViewHolder extends RecyclerView.ViewHolder {
        private ImageView scaleImageIv;
        private TextView scaleTitleTv;
        private TextView scaleSubtitleTv;


        public ScaleAViewHolder(View view) {
            super(view);
            scaleImageIv = (ImageView) view.findViewById(R.id.scale_image_iv);
            scaleTitleTv = (TextView) view.findViewById(R.id.scale_title_tv);
            scaleSubtitleTv = (TextView) view.findViewById(R.id.scale_subtitle_tv);
        }
    }
}
