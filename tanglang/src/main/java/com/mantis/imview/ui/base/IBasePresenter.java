package com.mantis.imview.ui.base;


public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();

}
