package com.rbricks.appsharing.newconcept.centerrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.List;


/**
 * Created by Gopi on 12/10/16.
 */
public class CenterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnTouchListener onTouchListener;

    private List<CenterRecyclerViewItem> itemList;

    private static final int VIEW_TYPE_PADDING = 1;

    private static final int VIEW_TYPE_ITEM = 2;

    private int paddingWidth;

    private SparseArray<View> recyclerAdapterIcons = new SparseArray();

    public CenterRecyclerViewAdapter(View.OnTouchListener argOnTouchListener, List<CenterRecyclerViewItem> itemList, int paddingWidth) {
        super();
        this.itemList = itemList;
        this.onTouchListener = argOnTouchListener;
        this.paddingWidth = paddingWidth;
    }

    @Override
    public int getItemViewType(int position) {
        CenterRecyclerViewItem centerRecyclerViewItem = itemList.get(position);
        if (centerRecyclerViewItem.isPaddingItem()) {
            return VIEW_TYPE_PADDING;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public CenterItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_recyclerview_normal_item,
                    parent, false);
            return new CenterItemViewHolder(view, true);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.center_recyclerview_padding_item,
                    parent, false);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams.width = paddingWidth;
            view.setLayoutParams(layoutParams);
            return new CenterItemViewHolder(view, false);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            CenterItemViewHolder centerItemViewHolder = (CenterItemViewHolder) holder;
            CenterRecyclerViewItem centerRecyclerViewItem = itemList.get(position);
            centerItemViewHolder.centerRecyclerviewNormalTv.setText(centerRecyclerViewItem.getName());
//            centerItemViewHolder.centerImage.setVisibility(View.VISIBLE);
            /*File file = new File(((CenterRecyclerViewItem) centerRecyclerViewItem).getPath());
            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                centerItemViewHolder.centerImage.setImageBitmap(myBitmap);
                centerItemViewHolder.centerImage.setTag(position);
            } else {
                centerItemViewHolder.centerImage.setImageResource(R.drawable.hike_caller_logo);
                centerItemViewHolder.centerImage.setTag(position);
            }*//*
            recyclerAdapterIcons.put(position, centerItemViewHolder.centerImage);*/
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void hideAllExceptPosn(int excludeHidePosn) {
        for (int i = 1; i <= recyclerAdapterIcons.size(); i++) {
            if (excludeHidePosn == -1) {
                recyclerAdapterIcons.get(i).setVisibility(View.VISIBLE);
            } else if (i != excludeHidePosn) {
                recyclerAdapterIcons.get(i).setVisibility(View.GONE);
            }
        }

    }

    class CenterItemViewHolder extends RecyclerView.ViewHolder {

//        ImageView centerImage;
        private RelativeLayout centerRecyclerviewNormalParent;
        private TextView centerRecyclerviewNormalTv;

        public CenterItemViewHolder(View view, boolean isTypeItem) {
            super(view);
            if (isTypeItem) {
//                centerImage = (ImageView) view.findViewById(R.id.hike_center_item);
                centerRecyclerviewNormalParent = (RelativeLayout) view.findViewById(R.id.center_recyclerview_normal_rl);
                centerRecyclerviewNormalTv = (TextView) view.findViewById(R.id.center_recyclerview_normal_tv);
                centerRecyclerviewNormalParent.setOnTouchListener(onTouchListener);
            }
        }
    }

}