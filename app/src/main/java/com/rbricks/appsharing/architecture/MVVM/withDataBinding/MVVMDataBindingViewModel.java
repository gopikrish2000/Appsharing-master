package com.rbricks.appsharing.architecture.MVVM.withDataBinding;

import android.databinding.ObservableField;

import com.rbricks.appsharing.utils.ReadOnlyField;

import io.reactivex.Observable;

import static com.rbricks.appsharing.utils.FieldUtils.toField;
import static com.rbricks.appsharing.utils.FieldUtils.toObservable;

/**
 * Created by gopi on 18/10/17.
 */

public class MVVMDataBindingViewModel {

    public ObservableField<String> phoneOF  = new ObservableField<>("");
    public ObservableField<String> emailOF= new ObservableField<>("");
    public ObservableField<Boolean> submitResultEnabilityOF = new ObservableField<>(false);


    public MVVMDataBindingViewModel() {
        getResultObservable();
    }

    public void getResultObservable() {
        Observable<Boolean> submitEnabledObservable = Observable.combineLatest(toObservable(emailOF), toObservable(phoneOF), (s, s2) -> s.length() > 5 && s2.length() > 2);
        submitResultEnabilityOF = toField(submitEnabledObservable);
    }
}
