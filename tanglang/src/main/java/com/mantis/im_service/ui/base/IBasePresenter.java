package com.mantis.im_service.ui.base;


public interface IBasePresenter<V extends IBaseView> {

    void attachView(V view);

    void detachView();

}
