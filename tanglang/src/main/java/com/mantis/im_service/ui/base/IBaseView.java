package com.mantis.im_service.ui.base;


public interface  IBaseView<P extends IBasePresenter> {

    P createPresenter();

}
