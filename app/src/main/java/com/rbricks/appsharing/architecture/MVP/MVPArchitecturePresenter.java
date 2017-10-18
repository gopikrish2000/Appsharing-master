package com.rbricks.appsharing.architecture.MVP;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by gopi on 18/10/17.
 */

public class MVPArchitecturePresenter implements MVPArchitecturePresenterInterface {
    private final MVPDataInteractor mvpDataInteractor;  // To delegate Data & other important parts away from Presenter.
    private MVPArchitectureViewContractInterface mvpInterface;

    private static MVPArchitectureViewContractInterface emptyInterface = color -> {};

    public MVPArchitecturePresenter(MVPArchitectureViewContractInterface mvpArchitectureViewContractInterface, MVPDataInteractor mvpDataInteractor) {
        mvpInterface = mvpArchitectureViewContractInterface;
        this.mvpDataInteractor = mvpDataInteractor;
    }


    @Override
    public void unSubscribe() {
        mvpInterface = emptyInterface;
    }


    @Override
    public void onSubmitButtonClicked() {
        String colorStr = mvpDataInteractor.generateRandomColor();
        mvpInterface.changeBackgroundColor(colorStr);
    }
}
