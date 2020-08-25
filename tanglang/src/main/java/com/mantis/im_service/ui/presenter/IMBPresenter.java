package com.mantis.im_service.ui.presenter;

import com.mantis.im_service.ui.base.IBasePresenter;
import com.mantis.im_service.ui.callback.MantisMBViewCB;

public interface IMBPresenter<V extends MantisMBViewCB> extends IBasePresenter<V> {
    void sendRequest(String name, String phone, String content);
}
