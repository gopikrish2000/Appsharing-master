package com.rbricks.appsharing.utils;

import android.util.Log;


import org.reactivestreams.Subscriber;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by gopikrishna on 6/8/16.
 */
public class RxJavaFactory {

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(new ObservableOnSubscribe<T>(){
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(func.call());
                    e.onComplete();
                } catch (Exception ex) {
                    Log.e("RxJava ", "Error reading from the database", ex);
                    e.onError(ex);
                }
            }
        });
    }

}
