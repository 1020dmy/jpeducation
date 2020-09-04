package com.mantis.imview.ui.callback;

import com.mantis.imview.ui.base.BasePresenter;
import com.mantis.imview.ui.base.IBaseView;

public interface MantisMBModuleCB {
    void onSuccess();

    void onError(int code, String desc);
}
