package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.CommentClassContract;
import com.jianpei.jpeducation.repository.CommentClassRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CommentClassModel extends BaseViewModel implements CommentClassContract.Model {

    private CommentClassRepository commentClassRepository;

    private MutableLiveData<String> successLiveData;

    public MutableLiveData<String> getSuccessLiveData() {
        if(successLiveData==null)
            successLiveData=new MutableLiveData<>();
        return successLiveData;
    }

    public CommentClassModel() {
        commentClassRepository = new CommentClassRepository();
    }

    @Override
    public void insertComment(String class_id, String content) {

        if (TextUtils.isEmpty(class_id))
            return;
        if (TextUtils.isEmpty(content)) {
            errData.setValue("评价内容不能为空！");
            return;
        }
        commentClassRepository.insertComment(class_id, content).compose(setThread()).subscribe(new BaseObserver<String>() {


            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

                if(t.isSuccess()){
                    successLiveData.setValue(t.getData());

                }else{
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
