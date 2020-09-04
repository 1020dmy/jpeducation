package com.mantis.imview.ui.base;


public  class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected  V mView;


    @Override
    public void attachView(V view) {
        mView =  view;
    }

    @Override
     public void detachView() {
        mView = null;
    }
}
