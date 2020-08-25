package com.mantis.im_service.ui.callback;

import com.example.mantis_im_sdk.bean.ChatEntity;
import com.example.mantis_im_sdk.bean.FirstChatEntity;
import com.mantis.im_service.ui.base.BasePresenter;
import com.mantis.im_service.ui.base.IBaseView;

import java.util.List;

public interface MantisViewCB<P extends BasePresenter> extends IBaseView<P> {
    void onMsgSuccess();

    void onMsgFailed(int code, String desc, ChatEntity failChat); //失败的消息 用户updata Adapter

    void onRecvMsg(ChatEntity msg);//接收的消息

    void onRecvWelMsg(List<ChatEntity> recChatMsg,String agentName);//接收欢迎语消息

    void onRecvHisMsg(List<ChatEntity> msgs,String agentName);//接收历史消息

    void onKick(); //被踢

    void onTransfer();//转接对话

    void onAgentLeave();//咨询师离线

    void onChatFail();//首次建立连接失败

    void onNetWork(int status);
}
