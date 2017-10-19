package com.rbricks.appsharing.concept.notesapp.utils;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gopikrishna on 13/11/16.
 */

public class RxApiUtil {

    public static <T> Observable<T> build(Observable<T> observable) {
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
