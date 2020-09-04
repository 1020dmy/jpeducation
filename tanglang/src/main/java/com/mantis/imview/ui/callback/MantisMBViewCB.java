package com.mantis.imview.ui.callback;

import com.mantis.imview.ui.base.BasePresenter;
import com.mantis.imview.ui.base.IBaseView;

public interface MantisMBViewCB<P extends BasePresenter> extends IBaseView<P> {
    void onSuccess();

    void onError(String error);
}
