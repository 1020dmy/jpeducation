package com.mantis.imview.ui.base;


public interface  IBaseView<P extends IBasePresenter> {

    P createPresenter();

}
