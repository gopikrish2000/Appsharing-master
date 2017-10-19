package com.rbricks.appsharing.architecture.MVVM.withDataBinding;

import android.databinding.ObservableField;
import android.widget.Toast;

import com.rbricks.appsharing.utils.CommonUtils;
import com.rbricks.appsharing.utils.ReadOnlyField;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.rbricks.appsharing.utils.FieldUtils.toField;
import static com.rbricks.appsharing.utils.FieldUtils.toObservable;

/**
 * Created by gopi on 18/10/17.
 */

public class MVVMDataBindingViewModel {

    public ObservableField<String> phoneOF  = new ObservableField<>("");
    public ObservableField<String> emailOF= new ObservableField<>("");
    public ReadOnlyField<String> submitTextOF;
//    public ReadOnlyField<Boolean> submitResultEnabilityOF;


    public MVVMDataBindingViewModel() {
        getResultObservable();
    }

    public void getResultObservable() {
        /*Observable<Boolean> submitEnabledObservable = Observable.combineLatest(toObservable(emailOF), toObservable(phoneOF), (s, s2) -> s.length() > 5 && s2.length() > 2);
        submitResultEnabilityOF = toField(submitEnabledObservable);*/

        Observable<String> submitEnabledObservable = Observable.combineLatest(toObservable(emailOF), toObservable(phoneOF), (s, s2) -> s + s2);
        submitTextOF = toField(submitEnabledObservable);
        submitEnabledObservable.debounce(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
            CommonUtils.showToast(s);
        });

    }
}
