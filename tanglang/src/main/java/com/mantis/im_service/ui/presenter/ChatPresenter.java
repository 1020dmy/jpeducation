package com.mantis.im_service.ui.presenter;

import com.example.mantis_im_sdk.bean.ChatEntity;
import com.example.mantis_im_sdk.common.ChatType;
import com.mantis.im_service.ui.base.BasePresenter;
import com.mantis.im_service.ui.callback.MantisRecvCB;
import com.mantis.im_service.ui.callback.MantisSendCB;
import com.mantis.im_service.ui.callback.MantisViewCB;
import com.mantis.im_service.ui.module.ChatModule;

import java.util.List;


public class ChatPresenter<V extends MantisViewCB> extends BasePresenter<V> implements IChatPresenter<V> {
    private ChatModule module;

    public ChatPresenter() {
        module = new ChatModule();
    }

    @Override
    public void sendMsg(ChatEntity entity) {
        if (module == null) {
            mView.onMsgFailed(-1, "presenter module instance is empty ", entity);
        }
        module.sendMsg(entity, new MantisSendCB() {
            @Override
            public void onSuccess() {
                if (mView != null) {
                    mView.onMsgSuccess();
                }
            }

            @Override
            public void onFailed(int code, String desc, ChatEntity failChat) {
                if (mView != null) {
                    mView.onMsgFailed(code, desc, failChat);
                }
            }
        });
    }

    @Override
    public void initMsgListener() {

        if (module == null) {
            throw new NullPointerException("presenter module instance is empty ");
        }
        module.initChat(new MantisRecvCB() {
            @Override
            public void onRecvMsg(ChatEntity msg) {
                if (mView != null) {
                    mView.onRecvMsg(msg);
                }
            }

            @Override
            public void onRecvWelMsg(List<ChatEntity> recChatMsg,String agentName) {
                if (mView != null) {
                    mView.onRecvWelMsg(recChatMsg,agentName);
                }
            }

            @Override
            public void onRecvHisMsg(List<ChatEntity> msgs,String agentName) {
                if (mView != null) {
                    mView.onRecvHisMsg(msgs,agentName);
                }
            }

            @Override
            public void onChatStatus(String status) {
                if (mView != null) {
                    switch (status) {
                        case ChatType.ON_KICK:
                            mView.onKick();
                            break;
                        case ChatType.ON_CHAT_FAIL:
                            mView.onChatFail();
                            break;
                        case ChatType.ON_TRANSFER:
                            mView.onTransfer();
                            break;
                        case ChatType.ON_AGENT_LEAVE:
                            mView.onAgentLeave();
                            break;
                    }
                }
            }

            @Override
            public void onNetWorkStatus(int status) {
                if (mView != null) {
                    mView.onNetWork(status);
                }
            }
        });
    }
}
