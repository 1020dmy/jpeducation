package com.mantis.imview.ui.presenter;

import com.mantis.imview.ui.base.IBasePresenter;
import com.mantis.imview.ui.callback.MantisMBViewCB;

public interface IMBPresenter<V extends MantisMBViewCB> extends IBasePresenter<V> {
    void sendRequest(String name, String phone, String content);
}
