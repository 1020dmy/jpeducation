package com.mantis.imview.ui.callback;

import com.mantis.core.bean.ChatEntity;

public interface MantisSendCB {
    void onSuccess();

    void onFailed(int code, String desc, ChatEntity failChat);

}
