package com.rbricks.appsharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.utils.CommonUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by gopi on 18/10/17.
 */

public class BaseActivity extends AppCompatActivity {

    CompositeSubscription baseCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseCompositeSubscription = new CompositeSubscription();
    }

    public void add(Subscription subscription) {
        baseCompositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.doUnsubscribe(baseCompositeSubscription);
    }
}
