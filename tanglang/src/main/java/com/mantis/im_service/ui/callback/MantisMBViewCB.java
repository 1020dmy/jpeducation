package com.mantis.im_service.ui.callback;

import com.example.mantis_im_sdk.bean.ChatEntity;
import com.mantis.im_service.ui.base.BasePresenter;
import com.mantis.im_service.ui.base.IBaseView;

import java.util.List;

public interface MantisMBViewCB<P extends BasePresenter> extends IBaseView<P> {
    void onSuccess();

    void onError(String error);
}
