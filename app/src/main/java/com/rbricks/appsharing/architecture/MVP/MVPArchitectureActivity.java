package com.rbricks.appsharing.architecture.MVP;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.utils.CommonUtils;

import io.reactivex.disposables.CompositeDisposable;


public class MVPArchitectureActivity extends AppCompatActivity implements MVPArchitectureViewContractInterface {

    private MVPArchitecturePresenter mvpArchitecturePresenter;
    private View bgView;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvparchitecture);
        mvpArchitecturePresenter = new MVPArchitecturePresenter(this, new MVPDataInteractor());
        compositeDisposable = new CompositeDisposable();
        initViews();
    }

    private void initViews() {
        bgView = findViewById(R.id.mvp_view_bg);
        compositeDisposable.add(RxView.clicks(findViewById(R.id.mvp_submit)).subscribe(s -> {
            mvpArchitecturePresenter.onSubmitButtonClicked();
        }));
    }

    @Override
    public void changeBackgroundColor(String color) {
        int colorCode = Color.parseColor(color);
        bgView.setBackgroundColor(colorCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.doUnsubscribe(compositeDisposable);
        mvpArchitecturePresenter.unSubscribe();
    }
}
