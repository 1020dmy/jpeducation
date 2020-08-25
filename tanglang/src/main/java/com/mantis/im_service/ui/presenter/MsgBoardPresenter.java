package com.mantis.im_service.ui.presenter;

import com.mantis.im_service.ui.base.BasePresenter;
import com.mantis.im_service.ui.callback.MantisMBModuleCB;
import com.mantis.im_service.ui.callback.MantisMBViewCB;
import com.mantis.im_service.ui.module.ChatModule;

public class MsgBoardPresenter<V extends MantisMBViewCB> extends BasePresenter<V> implements IMBPresenter<V> {
    private ChatModule module;

    public MsgBoardPresenter() {
        module = new ChatModule();
    }

    @Override
    public void sendRequest(String name, String phone, String content) {
        if (mView == null) {
            throw new NullPointerException("presenter mView is empty");
        }
        if (module == null) {
            mView.onError("presenter module instance is empty ");
        }
        module.reqMsgBoard(name, phone, content, new MantisMBModuleCB() {
            @Override
            public void onSuccess() {
                mView.onSuccess();
            }

            @Override
            public void onError(int code, String desc) {
                mView.onError(desc);
            }
        });
    }
}
