package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.SendCodeContract;
import com.jianpei.jpeducation.repository.SendCodeRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SendCodeModel extends BaseViewModel implements SendCodeContract.Model {


    private SendCodeRepository sendCodeRepository;

    public SendCodeModel() {
        sendCodeRepository = new SendCodeRepository();
    }

    /**
     * 发送手机号
     *
     * @param phone
     * @param type
     */
    private MutableLiveData<String> successCodeLiveData;

    public MutableLiveData<String> getSuccessCodeLiveData() {
        if (successCodeLiveData == null)
            successCodeLiveData = new MutableLiveData<>();
        return successCodeLiveData;
    }

    @Override
    public void sendCode(String phone, String type) {
        if (TextUtils.isEmpty(phone)) {
            errData.setValue("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            return;
        }
        sendCodeRepository.sendCode(phone, type).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    successCodeLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });


    }
}
