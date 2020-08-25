package com.mantis.im_service.ui.callback;

import com.mantis.im_service.ui.base.BasePresenter;
import com.mantis.im_service.ui.base.IBaseView;

public interface MantisMBModuleCB {
    void onSuccess();

    void onError(int code, String desc);
}
