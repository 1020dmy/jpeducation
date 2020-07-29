package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.mine.MessageDataBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MineMessageContract {

    interface Repository {
        Observable<BaseEntity<MessageDataBean>> messageData(int pageIndex, int pageSize);

        Observable<BaseEntity<String>> updateMessageStatus(String message_id);
    }


    interface Model {
        void messageData(int pageIndex, int pageSize);

        void updateMessageStatus(String message_id);

    }
}
