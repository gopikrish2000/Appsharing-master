package com.rbricks.appsharing.concept.rxjava;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.rbricks.appsharing.R;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jakewharton.rxbinding.view.RxView.clicks;
import static com.jakewharton.rxbinding.widget.RxTextView.textChangeEvents;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;

public class RxJavaAdvancedActivity extends AppCompatActivity {

    private TextView resultsTV;
    private EditText implementationEditText;
    private String currentImplementationText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);
        init();
        clicks(findViewById(R.id.processButton)).subscribe(s -> performCurrentImplementation());
        clicks(findViewById(R.id.recentButton)).subscribe(s -> countWithReduce());
        textChangeEvents(implementationEditText).subscribe(s -> currentImplementationText = s.text().toString());
    }

    private void init() {
        resultsTV = (TextView) findViewById(R.id.results_tv);
        implementationEditText = ((EditText) findViewById(R.id.process_et));
    }

    private void performCurrentImplementation() {
        if (isNullOrEmpty(currentImplementationText)) {
            return;
        }

        switch (currentImplementationText) {
            case "first":
                first();
                break;
            case "concat":
                concatmap();
                break;
            case "count":
                countWithReduce();
                break;
        }
    }

    private void concatmap() {
        Observable.concat(getO1(), getO2())
                .toSortedList().map(s -> Arrays.toString(s.toArray()))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(s -> resultsTV.setText(s + ""));
    }


    private Observable<Integer> getO1() {
        return Observable.create(s -> {
            int i = 0;
            while (i++ < 10) {
                s.onNext(i);
            }
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        });
    }

    private Observable<Integer> getO2() {
        return Observable.create(s -> {
            int i = 10;
            while (i++ < 20) {
                s.onNext(i);
            }
        });
    }

    private void countWithReduce() {
        Observable.just(1, -2, 4, 3, 10, 8, 9).scan(new Pair<>(0,0), (a, b) -> new Pair<>(a.first+1,b)).subscribe(s -> {
            System.out.println("s.first = " + s.first + " s.second " + s.second);
            resultsTV.setText("count : "+ s.first + " item is "+ s.second);
        });
    }

    private void first() {
        Observable<List<Integer>> collectIntList = Observable.just(1, -2, 4, 3, 10, 8, 9).filter(s -> s > 1).toSortedList();
        collectIntList.switchMap(Observable::from).filter(s -> s > 3).map(s -> s + "").reduce("", (a, b) -> a + b + " , | ").map(s -> s.substring(0, s.length() - 4))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> resultsTV.setText(s + ""));
    }


}


