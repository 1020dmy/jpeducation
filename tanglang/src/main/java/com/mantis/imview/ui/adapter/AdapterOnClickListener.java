package com.mantis.imview.ui.adapter;

import com.mantis.core.bean.ChatEntity;
import com.mantis.core.bean.ReceiveEntity;

/**
 * 接收消息 : 点击选择内容回调界面接口
 */
public interface AdapterOnClickListener {
    void recMsgOnClick(ReceiveEntity.ContentBean contentBean);//收到的消息点击回显回调

    void bigPicOnClick(String path);//点击图片放大回调

    void reSendMsg(ChatEntity chatEntity);//点击红感叹号重新发送消息
}
