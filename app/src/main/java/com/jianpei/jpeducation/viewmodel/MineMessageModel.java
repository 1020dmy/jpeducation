package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.mine.MessageDataBean;
import com.jianpei.jpeducation.contract.MineMessageContract;
import com.jianpei.jpeducation.repository.MineMessageRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe://////////////////////////////////////////////////////////////////////////////////////////////////\
 */
public class MineMessageModel extends BaseViewModel implements MineMessageContract.Model {


    private MineMessageRepository mineMessageRepository;

    public MineMessageModel() {
        mineMessageRepository = new MineMessageRepository();
    }

    /**
     * 消息列表
     *
     * @param pageIndex
     * @param pageSize
     */
    private MutableLiveData<MessageDataBean> messageDataBeanLiveData;

    public MutableLiveData<MessageDataBean> getMessageDataBeanLiveData() {
        if (messageDataBeanLiveData == null)
            messageDataBeanLiveData = new MutableLiveData<>();
        return messageDataBeanLiveData;
    }

    @Override
    public void messageData(int pageIndex, int pageSize) {

        mineMessageRepository.messageData(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<MessageDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<MessageDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    messageDataBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 改变消息状态
     *
     * @param message_id
     */
    private MutableLiveData<String> stringMutableLiveData;

    public MutableLiveData<String> getStringMutableLiveData() {
        if (stringMutableLiveData == null)
            stringMutableLiveData = new MutableLiveData<>();
        return stringMutableLiveData;
    }

    @Override
    public void updateMessageStatus(String message_id) {
        mineMessageRepository.updateMessageStatus(message_id).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    stringMutableLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }
}
