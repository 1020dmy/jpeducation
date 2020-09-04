package com.mantis.imview.ui.module;

import com.mantis.core.MIMConversation;
import com.mantis.core.MIMManager;
import com.mantis.core.MIMUserConfig;
import com.mantis.core.bean.ChatEntity;
import com.mantis.core.callback.MIMCallBack;
import com.mantis.core.callback.MIMMessageListener;
import com.mantis.core.callback.MIMValueCallBack;
import com.mantis.core.common.ChatType;
import com.mantis.imview.ui.callback.MantisMBModuleCB;
import com.mantis.imview.ui.callback.MantisRecvCB;
import com.mantis.imview.ui.callback.MantisSendCB;
import com.mantis.imview.common.MantisCommon;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMUserStatusListener;

import java.util.List;

public class ChatModule {
    private MIMConversation mConversation;

    public void sendMsg(ChatEntity chatEntity, MantisSendCB sendCB) {
        if (chatEntity == null) {
            sendCB.onFailed(-1, "send chat entity is empty!", chatEntity);
            return;
        }
        if (mConversation == null) {
            sendCB.onFailed(-1, "mConversation is empty!", chatEntity);
            return;
        }
        mConversation.sendMessage(chatEntity, new MIMValueCallBack() {
            @Override
            public void onError(int code, String desc) {
                sendCB.onFailed(code, desc, chatEntity);
            }

            @Override
            public void onSuccess() {
                sendCB.onSuccess();
            }
        });
    }

    public void initChat(MantisRecvCB recvCB) {
        MIMUserConfig mimUserConfig = new MIMUserConfig();

        mimUserConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                recvCB.onChatStatus(ChatType.ON_KICK);
            }

            @Override
            public void onUserSigExpired() {

            }
        });
        mimUserConfig.setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                recvCB.onNetWorkStatus(MantisCommon.CONNECT);
            }

            @Override
            public void onDisconnected(int i, String s) {
                recvCB.onNetWorkStatus(MantisCommon.DISCONNECT);
            }

            @Override
            public void onWifiNeedAuth(String s) {
                recvCB.onNetWorkStatus(MantisCommon.WIFI);
            }
        });
        MIMManager.getInstance().setUserConfig(mimUserConfig);
        mConversation = MIMManager.getInstance().getConversation();
        //监听响应消息
        MIMManager.getInstance().addMessageListener(new MIMMessageListener() {
            @Override
            public void onNewMsg(ChatEntity msg) {
                recvCB.onRecvMsg(msg);
            }

            @Override
            public void onWelcomeMsg(List<ChatEntity> welcomes, String agentName) {
                recvCB.onRecvWelMsg(welcomes, agentName);
            }

            @Override
            public void onHistory(List<ChatEntity> msgs, String agentName) {
                recvCB.onRecvHisMsg(msgs, agentName);
            }

            @Override
            public void onChatStatus(String status) {
                recvCB.onChatStatus(status);
            }
        });
    }

    public void reqMsgBoard(String name, String phone, String content, MantisMBModuleCB cb) {
        MIMManager.getInstance().reqMsgBoard(name, phone, content, new MIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                cb.onError(code, desc);
            }

            @Override
            public void onSuccess() {
                cb.onSuccess();
            }
        });
    }
}