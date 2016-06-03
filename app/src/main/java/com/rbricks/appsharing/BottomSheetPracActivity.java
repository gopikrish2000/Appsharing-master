package com.rbricks.appsharing;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BottomSheetPracActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_prac);
        Button button1 = (Button) findViewById( R.id.button_1 );
        button1.setOnClickListener(this);
    }

    public BottomSheetBehavior shareFunctionalityHelpchat(GridView gridView) {
        final List<ResolveInfo> resInfosNew = new ArrayList<>();
        List<String> packagesList = new ArrayList<>();
        final ChooserArrayAdapter chooserArrayAdapter = new ChooserArrayAdapter(this, packagesList);
        gridView.setAdapter(chooserArrayAdapter);

        final BottomSheetBehavior<GridView> mBottomSheetBehavior = BottomSheetBehavior.from(gridView);
        mBottomSheetBehavior.setPeekHeight(300);

        resInfosNew.addAll(getShareHelpchatPackageResolveInfos());
        packagesList.addAll(getPackagesList(resInfosNew));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chooserArrayAdapter.notifyDataSetChanged();
            }
        }, 1000);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int item, long id) {
                invokeApplication(resInfosNew.get(item).activityInfo.packageName, resInfosNew.get(item));
            }
        });

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return mBottomSheetBehavior;
    }

    private List<String> getPackagesList(List<ResolveInfo> resolveInfoList) {
        List<String> packagesList = new ArrayList<>();
        for (int i = 0; i < resolveInfoList.size(); i++) {
            packagesList.add(resolveInfoList.get(i).activityInfo.packageName);
        }
        System.out.println("packagesList in getPackagesList method= " + packagesList);
        return packagesList;
    }

    private List<ResolveInfo> getShareHelpchatPackageResolveInfos() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        final List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
        List<ResolveInfo> resolveInfoList = new ArrayList<>();
        Set<String> hashSet = new HashSet<>(8);
        hashSet.addAll(Arrays.asList("com.google.android.apps.plus",
                "com.google.android.talk",
                "com.whatsapp",
                "com.linkedin.android",
                "com.facebook.orca", "com.facebook.katana", "com.twitter.android"));
        if (!resInfos.isEmpty()) {
            System.out.println("Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;
                System.out.print(" " + packageName + " ");
                if (hashSet.contains(packageName) || packageName.contains("mail") || packageName.contains("messaging")) {
                    resolveInfoList.add(resInfo);
                }
            }
        }
        return resolveInfoList;
    }

    private void invokeApplication(String packageName, ResolveInfo resolveInfo) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi guys, I found amazing thing to share. Send your love and care in form of gifts to your loved ones from anywhere in world. Log on to giftjaipur.com or download app" + "https://goo.gl/YslIVT" + "and use coupon code app50 to get Rs 50 off on your first purchase.");
        intent.putExtra(Intent.EXTRA_SUBJECT, "GiftJaipur 50 Rs Coupon...");
        intent.setPackage(packageName);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.button_1: {
                GridView bottomSheet = ((GridView) findViewById(R.id.bottom_sheet_listview));
                BottomSheetBehavior mBottomSheetBehavior = shareFunctionalityHelpchat(bottomSheet);
                break;
            }
        }
    }
}
