package com.rbricks.appsharing.concept.rxjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.rbricks.appsharing.R;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaSampleActivity extends AppCompatActivity {

    public TextView rxTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_sample);
        rxTextView = ((TextView) findViewById(R.id.rxTextView));
        findViewById(R.id.buttonClearResult).setOnClickListener(s -> rxTextView.setText(""));
        findViewById(R.id.executeResult).setOnClickListener(s -> sampleRxJavaMethods());

    }

    private void sampleRxJavaMethods() {
        Observable.just("1", "2", "32")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.just(s + "2", s + "3","4"))
                .filter(s -> !s.isEmpty())
//                .map(s -> Integer.parseInt(s + "4"))
                .map(s -> s + "Added")
                .subscribe(s -> {
                    tos(s + "");
                });
    }

    private void secondExample() {
        Observable a = Observable.just(1, 2, 3, 4);
        Observable b = Observable.just(3, 4, 5, 6);

// If you simply want a list of distinct items:
        Observable uniqueItems = a.merge(b).distinct();

// If you just want to filter "a" so it contains none of the items in "b"
       /* Observable filteredA = b.toList().flatMap(itemsInB -> {
            a.filter(item -> !itemsInB.contains(item));
        });*/
       
    }


    public void tos (String string) {
//        Toast.makeText(RxJavaSampleActivity.this, string, Toast.LENGTH_SHORT).show();
        String result = rxTextView.getText().toString();
        result += "  " + string + " \n";
        rxTextView.setText(result);
    }
}
