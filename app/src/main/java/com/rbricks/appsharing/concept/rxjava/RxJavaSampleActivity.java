package com.rbricks.appsharing.concept.rxjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.util.Predicate;
import com.rbricks.appsharing.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class RxJavaSampleActivity extends AppCompatActivity {

    public TextView rxTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_sample);
        rxTextView = ((TextView) findViewById(R.id.rxTextView));
        findViewById(R.id.buttonClearResult).setOnClickListener(s -> rxTextView.setText(""));
        findViewById(R.id.executeResult).setOnClickListener(s -> zomatoTwoCallsExceptionCheck());

    }

    private void sampleRxJavaMethods() {
        Observable.just("1", "2", "32")

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(s -> {
                    System.out.println("s = " + s);
                    return Observable.just(s, "4");
                })
                .filter(s -> !s.isEmpty())
//                .map(s -> Integer.parseInt(s + "4"))
//                .map(s -> s + "Added")
                .subscribe(s -> {
                    tos(s + "");
                }, error -> {
                    System.out.println("error = " + error);
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

    private void thirdExample() {
        Observable.just(1l,2l,3l,4l)
//                .interval(1, TimeUnit.SECONDS)   // interval starts from 0 n increment
                .map(input -> {
                    if (input == 3l ) {
                        throw new RuntimeException();
                    }
                    return input;
                })
                //compose will change instanteously on whole initial stream. not like FlatMap which creates/works on onNext only.
                .compose(s -> s.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()))
//                .onErrorReturn(error -> "Uh oh")  // will return "Uh oh" once after exception
                .onErrorResumeNext(s -> Observable.just(1000l, 2000l))  // will continue with new Observable on Error.
//                .onExceptionResumeNext(Observable.just(101l,102l))  // will continue with new Observable on Error.
                .subscribe(s -> System.out.println(s));
    }

    private void fourthExample() {
//        PublishSubject<Object> publishSubject = PublishSubject.create(); // First : 3,4,5 , Second : 5
//        BehaviorSubject<Object> publishSubject = BehaviorSubject.create(); // First: 3,4,5 , Second: 4,5 ( one before subscribing)
//        ReplaySubject<Object> publishSubject = ReplaySubject.create(); // First: 3,4,5 , Second: 3,4,5 ( ALL ITEMS)
        AsyncSubject<Object> publishSubject = AsyncSubject.create(); // First: 5 , Second: 5 ( ALL ITEMS)
        publishSubject.subscribe(s -> {
            System.out.println(" First one "+s);
        });
        publishSubject.onNext("3");
        publishSubject.onNext("4");

        publishSubject.subscribe(s -> {
            System.out.println(" Second one " + s);
        });
        publishSubject.onNext("5");
        publishSubject.onCompleted();
    }

    interface PredicateMy {
        int operation(int a, int b);
    }

    private void fifthExample() {

        /*PredicateMy addition = (a, b) -> a + b;
        int value = addition.operation(1, 2);
        System.out.println("value = " + value);

        Predicate<Integer> predicate = n -> n>5;
        if (predicate.apply(15)) {
            System.out.println("predicate value > 5 = " + predicate);
        }

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        strings.stream().filter(s -> s.length()>2).forEach(s -> System.out.println("s = " + s));

        Function<String, Integer> converter = Integer::parseInt;
        Integer result = converter.apply("2");
        System.out.println("result = " + result);*/

    }

    private void zomatoTwoCallsExceptionCheck() {
        Observable<String> firstObservable = Observable.just("2");
        Observable<String> secondObservable = Observable.<String>create(s -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();}).onErrorResumeNext(Observable.just(null));
        Observable.zip(firstObservable, secondObservable, (s, s2) -> new Object[]{s,s2}).subscribe(s -> System.out.println("s[0] = " + s[0] + " s[1] " + s[1]), e -> {
            e.printStackTrace();
            System.out.println("inside error");
        });

    }


    public void tos (String string) {
//        Toast.makeText(RxJavaSampleActivity.this, string, Toast.LENGTH_SHORT).show();
        String result = rxTextView.getText().toString();
        result += "  " + string + " \n";
        rxTextView.setText(result);
    }
}
