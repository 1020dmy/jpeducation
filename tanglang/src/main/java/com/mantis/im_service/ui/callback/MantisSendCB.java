package com.mantis.im_service.ui.callback;

import com.example.mantis_im_sdk.bean.ChatEntity;

public interface MantisSendCB {
    void onSuccess();

    void onFailed(int code, String desc, ChatEntity failChat);

}
