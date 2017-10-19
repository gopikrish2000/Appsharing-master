package com.rbricks.appsharing.architecture.MVVM.withoutDataBinding;


import io.reactivex.Observable;

/**
 * Created by gopi on 18/10/17.
 */

public class MVVMViewModel {

    Observable<String> email;
    Observable<String> phone;

    public void setEmail(Observable<String> email) {
        this.email = email;
    }

    public void setPhone(Observable<String> phone) {
        this.phone = phone;
    }

    public MVVMViewModel() {
    }

    public Observable<Boolean> getResultObservable() {
        return Observable.combineLatest(email, phone, (emailRes, phoneRes) -> {
            if (emailRes.length() > 5 && phoneRes.length() > 2) {
                return true;
            }
            return false;
        });
    }
}
