package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.contract.CommentContract;
import com.jianpei.jpeducation.repository.CommentRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentModel extends BaseViewModel implements CommentContract.Model {


    private CommentRepository commentRepository;

    public CommentModel() {
        commentRepository = new CommentRepository();
    }

    /**
     * 评论
     *
     * @param class_id
     * @param content
     */
    private MutableLiveData<String> commentSuccessLiveData;

    public MutableLiveData<String> getCommentSuccessLiveData() {
        if (commentSuccessLiveData == null)
            commentSuccessLiveData = new MutableLiveData<>();
        return commentSuccessLiveData;
    }

    @Override
    public void insertComment(String class_id, String content) {
        if (TextUtils.isEmpty(class_id))
            return;
        if (TextUtils.isEmpty(content)) {
            errData.setValue("评价内容不能为空！");
            return;
        }
        commentRepository.insertComment(class_id, content).compose(setThread()).subscribe(new BaseObserver<String>() {


            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

                if (t.isSuccess()) {
                    commentSuccessLiveData.setValue(t.getData());

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

    /**
     * 评论列表
     *
     * @param type
     * @param groupId
     * @param class_id
     * @param page_index
     * @param size
     */
    private MutableLiveData<CommentListBean> commentListBeanLiveData;

    public MutableLiveData<CommentListBean> getCommentListBeanLiveData() {
        if (commentListBeanLiveData == null)
            commentListBeanLiveData = new MutableLiveData<>();
        return commentListBeanLiveData;
    }

    @Override
    public void commentList(String type, String groupId, String class_id, int page_index, int size) {


        commentRepository.commentList(type, groupId, class_id, page_index, size).compose(setThread()).subscribe(new BaseObserver<CommentListBean>() {

            @Override
            protected void onSuccees(BaseEntity<CommentListBean> t) throws Exception {

                if (t.isSuccess()) {
                    commentListBeanLiveData.setValue(t.getData());

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
