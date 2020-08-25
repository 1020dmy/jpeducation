package com.mantis.im_service.ui.callback;

import com.example.mantis_im_sdk.bean.ChatEntity;
import com.example.mantis_im_sdk.bean.FirstChatEntity;
import com.tencent.openqq.protocol.imsdk.msg;

import java.util.List;

public interface MantisRecvCB {

    void onRecvMsg(ChatEntity msg);//接收的消息

    void onRecvWelMsg(List<ChatEntity> recChatMsg, String agentName);//接收欢迎语消息

    void onRecvHisMsg(List<ChatEntity> msgs, String agentName);//接收历史消息

    void onChatStatus(String status);

    void onNetWorkStatus(int status);
}
