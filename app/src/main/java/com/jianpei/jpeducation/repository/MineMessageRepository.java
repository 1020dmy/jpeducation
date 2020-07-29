package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.TopicDataJson;
import com.jianpei.jpeducation.bean.json.UpdateMessageStatusJson;
import com.jianpei.jpeducation.bean.mine.MessageDataBean;
import com.jianpei.jpeducation.contract.MineMessageContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineMessageRepository extends BaseRepository implements MineMessageContract.Repository {

    @Override
    public Observable<BaseEntity<MessageDataBean>> messageData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().messageData(new TopicDataJson(pageIndex, pageSize));
    }

    @Override
    public Observable<BaseEntity<String>> updateMessageStatus(String message_id) {
        return RetrofitFactory.getInstance().API().updateMessageStatus(new UpdateMessageStatusJson(message_id));
    }
}
