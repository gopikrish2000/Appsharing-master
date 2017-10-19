package com.rbricks.appsharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rbricks.appsharing.utils.CommonUtils;


import org.reactivestreams.Subscription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by gopi on 18/10/17.
 */

public class BaseActivity extends AppCompatActivity {

    CompositeDisposable baseCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseCompositeDisposable = new CompositeDisposable();
    }

    public void add(Disposable subscription) {
        baseCompositeDisposable.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.doUnsubscribe(baseCompositeDisposable);
    }
}
