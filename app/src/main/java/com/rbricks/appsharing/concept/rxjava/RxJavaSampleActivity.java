package com.rbricks.appsharing.concept.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.rbricks.appsharing.R;

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
        // map is used for changing/transforming the input .Can change the type as well.
        // map blocks next item if current item is taking some time. But flatMap will run all the
        // items as new Observable so it wont be blocked.
        // filter is used to reduce no. of items send to subscriber

        Observable.just("1", "2", "32")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(s -> s.contains("2"))
                .map(s -> {
                    if (s.equalsIgnoreCase("211")) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return s;
                })
                .map(s -> Integer.parseInt(s + "4"))
                .map(s -> s + "Added")
                .subscribe(s -> {
                    tos(s + "");
                });
    }


    public void tos (String string) {
        Toast.makeText(RxJavaSampleActivity.this, string, Toast.LENGTH_SHORT).show();
        String result = rxTextView.getText().toString();
        result += "  " + string + " \n";
        rxTextView.setText(result);
    }
}
