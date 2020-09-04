package com.mantis.imview.ui.callback;

import com.mantis.core.bean.ChatEntity;

import java.util.List;

public interface MantisRecvCB {

    void onRecvMsg(ChatEntity msg);//接收的消息

    void onRecvWelMsg(List<ChatEntity> recChatMsg, String agentName);//接收欢迎语消息

    void onRecvHisMsg(List<ChatEntity> msgs, String agentName);//接收历史消息

    void onChatStatus(String status);

    void onNetWorkStatus(int status);
}
