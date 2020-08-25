package com.mantis.im_service.ui.module;

import com.example.mantis_im_sdk.MIMConversation;
import com.example.mantis_im_sdk.MIMManager;
import com.example.mantis_im_sdk.MIMUserConfig;
import com.example.mantis_im_sdk.bean.ChatEntity;
import com.example.mantis_im_sdk.callback.MIMCallBack;
import com.example.mantis_im_sdk.callback.MIMMessageListener;
import com.example.mantis_im_sdk.callback.MIMValueCallBack;
import com.example.mantis_im_sdk.common.ChatType;
import com.mantis.im_service.ui.callback.MantisMBModuleCB;
import com.mantis.im_service.ui.callback.MantisRecvCB;
import com.mantis.im_service.ui.callback.MantisSendCB;
import com.mantis.im_service.common.MantisCommon;
import com.example.mantis_im_sdk.face.Emoji;
import com.example.mantis_im_sdk.face.EmojiUtil;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMUserStatusListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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
        mConversation.sendMessage(chatEntity, new MIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                if (code == 20006) {
                    sendCB.onSuccess();
                } else {
                    sendCB.onFailed(code, desc, chatEntity);
                }
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                sendCB.onSuccess();
            }
        });
    }

    public void initChat(MantisRecvCB recvCB) {
        MIMUserConfig mimUserConfig = new MIMUserConfig();
        //设置会话刷新监听器
        mimUserConfig.setRefreshListener(new TIMRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onRefreshConversation(List<TIMConversation> conversations) {
            }
        });

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
                cb.onError(code,desc);
            }

            @Override
            public void onSuccess() {
                cb.onSuccess();
            }
        });
    }
}