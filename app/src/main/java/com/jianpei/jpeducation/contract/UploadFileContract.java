package com.jianpei.jpeducation.contract;

import android.net.Uri;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
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
public interface UploadFileContract {


    interface Repository {


        Observable<BaseEntity<List<String>>> uploadFile(String type, List<MultipartBody.Part> imgs);

    }


    interface Model {

        void uploadFile(String type, List<File> files);


    }
}
