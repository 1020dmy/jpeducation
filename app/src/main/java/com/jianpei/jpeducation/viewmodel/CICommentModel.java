package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.contract.CICommentContract;
import com.jianpei.jpeducation.repository.CICommentRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CICommentModel extends BaseViewModel implements CICommentContract.Model {

    private CICommentRepository ciCommentRepository;

    private MutableLiveData<CommentListBean> commentListBeanMutableLiveData;

    public MutableLiveData<CommentListBean> getCommentListBeanMutableLiveData() {
        if (commentListBeanMutableLiveData == null) {
            commentListBeanMutableLiveData = new MutableLiveData<>();
        }
        return commentListBeanMutableLiveData;
    }

    public CICommentModel() {
        ciCommentRepository = new CICommentRepository();
    }

    @Override
    public void commentList(String groupId, int page_index, int size) {

        if (TextUtils.isEmpty(groupId)) {
            return;
        }

        ciCommentRepository.commentList(groupId, page_index, size).compose(setThread()).subscribe(new BaseObserver<CommentListBean>() {

            @Override
            protected void onSuccees(BaseEntity<CommentListBean> t) throws Exception {

                if (t.isSuccess()) {
                    commentListBeanMutableLiveData.setValue(t.getData());

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
