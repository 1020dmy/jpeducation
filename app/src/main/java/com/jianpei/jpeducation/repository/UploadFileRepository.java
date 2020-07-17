package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.contract.UploadFileContract;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UploadFileRepository extends BaseRepository implements UploadFileContract.Repository {

    @Override
    public Observable<BaseEntity<List<String>>> uploadFile(String type, List<MultipartBody.Part> imgs) {
        return RetrofitFactory.getInstance().API().uploadFile(imgs);
    }
}
