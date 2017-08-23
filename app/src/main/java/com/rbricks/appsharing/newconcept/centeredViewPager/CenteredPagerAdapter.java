package com.rbricks.appsharing.newconcept.centeredViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.newconcept.centerrecyclerview.CenterRecyclerViewItem;

import java.util.List;

/**
 * Created by gopi on 23/08/17.
 */

public class CenteredPagerAdapter extends PagerAdapter {

     private final List<CenterRecyclerViewItem> itemList;
    private final int paddingWidth;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    /**
     * Instantiates a new home slides pager adapter.
     *
     * @param context       the context
     * @param carousalImage
     */
    public CenteredPagerAdapter(Context context, List<CenterRecyclerViewItem> carousalImage, int paddingWidth) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.itemList = carousalImage;
        this.paddingWidth = paddingWidth;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup parent, final int position) {
       /* final View itemView = mLayoutInflater.inflate(R.layout.carousal_page, container,
                false);*/
        CenterRecyclerViewItem item = itemList.get(position);
        boolean isPaddingItem = item.isPaddingItem();
        View view;
        if (!isPaddingItem) {
            view = mLayoutInflater.inflate(R.layout.center_recyclerview_normal_item,
                    parent, false);
            RelativeLayout centerRecyclerviewNormalParent = (RelativeLayout) view.findViewById(R.id.center_recyclerview_normal_rl);
            TextView centerRecyclerviewNormalTv = (TextView) view.findViewById(R.id.center_recyclerview_normal_tv);
            centerRecyclerviewNormalTv.setText(item.getName());
        } else {
            view = mLayoutInflater.inflate(R.layout.center_recyclerview_padding_item,
                    parent, false);
            ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) view.getLayoutParams();
            layoutParams.width = paddingWidth;
            view.setLayoutParams(layoutParams);
        }

        parent.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
