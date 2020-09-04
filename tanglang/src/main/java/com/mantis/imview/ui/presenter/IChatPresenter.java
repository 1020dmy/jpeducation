package com.mantis.imview.ui.presenter;

import com.mantis.core.bean.ChatEntity;
import com.mantis.imview.ui.base.IBasePresenter;
import com.mantis.imview.ui.callback.MantisViewCB;

public interface IChatPresenter<V extends MantisViewCB> extends IBasePresenter<V> {
    void sendMsg(ChatEntity entity);

    void initMsgListener();


}
