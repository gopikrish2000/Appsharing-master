package com.rbricks.appsharing.concept.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.Activities.concept.fragments.ViewPagerPracFragment;

public class ViewPagerPracActivity extends AppCompatActivity {

    // FragmentStatePagerAdapter will remove fragment upon scroll (used for unknown no of fragments) and FragmentPagerAdapter willnot remove also used for fixed
    // number of fragments

        DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
        ViewPager mViewPager;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_pager_prac);

            // ViewPager and its adapters use support library
            // fragments, so use getSupportFragmentManager.
            mDemoCollectionPagerAdapter =
                    new DemoCollectionPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        }
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
     class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ViewPagerPracFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ViewPagerPracFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // Instances of this class are fragments representing a single
// object in our collection.



