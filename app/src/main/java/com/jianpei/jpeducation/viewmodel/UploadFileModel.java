package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.UploadFileContract;
import com.jianpei.jpeducation.repository.UploadFileRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UploadFileModel extends BaseViewModel implements UploadFileContract.Model {


    private UploadFileRepository uploadFileRepository;

    public UploadFileModel() {
        uploadFileRepository = new UploadFileRepository();
    }

    private MutableLiveData<List<String>> successLiveData;

    public MutableLiveData<List<String>> getSuccessLiveData() {
        if (successLiveData == null)
            successLiveData = new MutableLiveData<>();
        return successLiveData;
    }

    @Override
    public void uploadFile(String type, List<File> files) {
        if (files == null || files.size() == 0) {
            return;
        }
        List<MultipartBody.Part> multipartBody = getMu(files);
        uploadFileRepository.uploadFile(type, multipartBody).compose(setThread()).subscribe(new BaseObserver<List<String>>() {
            @Override
            protected void onSuccees(BaseEntity<List<String>> t) throws Exception {
                if (t.isSuccess()) {
                    successLiveData.setValue(t.getData());
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

    public List<MultipartBody.Part> getMu(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/jpeg")); ///< "multipart/from-data"
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file[]", file.getName(), requestBody);
            parts.add(filePart);

        }
        return parts;

    }
}
