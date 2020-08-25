package com.mantis.im_service.ui.presenter;

import com.example.mantis_im_sdk.bean.ChatEntity;
import com.mantis.im_service.ui.base.IBasePresenter;
import com.mantis.im_service.ui.callback.MantisViewCB;

public interface IChatPresenter<V extends MantisViewCB> extends IBasePresenter<V> {
    void sendMsg(ChatEntity entity);

    void initMsgListener();


}
