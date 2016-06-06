package com.rbricks.appsharing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gopikrishna on 6/3/16.
 */
public class BottomSheetDialogHelpchat extends BottomSheetDialog {

    // Add other necessary apps to show in BottomSheet
    private static String[] packagesSupportedList = new String[]{"com.google.android.apps.plus","com.google.android.talk",
            "com.whatsapp",
            "com.linkedin.android",
            "com.facebook.orca", "com.facebook.katana", "com.twitter.android"};

    public BottomSheetDialogHelpchat(Context context) {
        super(context);
    }

    public static void shareFunctionalityDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.sheet, null);
        GridView gridView = ((GridView) view.findViewById(R.id.bottom_sheet_listview_new));
        final List<ResolveInfo> resInfosNew = new ArrayList<>();
        List<String> packagesList = new ArrayList<>();
        final ChooserArrayAdapter chooserArrayAdapter = new ChooserArrayAdapter(context, packagesList);
        gridView.setAdapter(chooserArrayAdapter);

        resInfosNew.addAll(getShareHelpchatPackageResolveInfos(context));
        packagesList.addAll(getPackagesList(resInfosNew));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chooserArrayAdapter.notifyDataSetChanged();
            }
        }, 500);

       /* PublishSubject<Integer> subject = PublishSubject.create();
        subject.onNext(2);  // in onItem click*/

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(view);
        BottomSheetBehavior.from(((View) view.getParent()));
        mBottomSheetDialog.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int item, long id) {
                invokeApplication(resInfosNew.get(item).activityInfo.packageName, resInfosNew.get(item),context);
            }
        });

    }

    // Convert ResolveInfos to the List of String package names
    private static List<String> getPackagesList(List<ResolveInfo> resolveInfoList) {
        List<String> packagesList = new ArrayList<>();
        for (int i = 0; i < resolveInfoList.size(); i++) {
            packagesList.add(resolveInfoList.get(i).activityInfo.packageName);
        }
        return packagesList;
    }

    private static List<ResolveInfo> getShareHelpchatPackageResolveInfos(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        final List<ResolveInfo> resInfos = context.getPackageManager().queryIntentActivities(shareIntent, 0);
        List<ResolveInfo> resolveInfoList = new ArrayList<>();
        Set<String> hashSet = new HashSet<>(8);
        hashSet.addAll(Arrays.asList(packagesSupportedList));
        if (!resInfos.isEmpty()) {
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;
                if (hashSet.contains(packageName) || packageName.contains("mail") || packageName.contains("messaging")) {
                    resolveInfoList.add(resInfo);
                }
            }
        }
        return resolveInfoList;
    }

    // Modify logic specific to application if Required
    private static void invokeApplication(String packageName, ResolveInfo resolveInfo,Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi guys, I found amazing thing to share. Send your love and care in form of gifts to your loved ones from anywhere in world. Log on to giftjaipur.com or download app" + "https://goo.gl/YslIVT" + "and use coupon code app50 to get Rs 50 off on your first purchase.");
        intent.putExtra(Intent.EXTRA_SUBJECT, "GiftJaipur 50 Rs Coupon...");
        intent.setPackage(packageName);
        context.startActivity(intent);
    }
}
