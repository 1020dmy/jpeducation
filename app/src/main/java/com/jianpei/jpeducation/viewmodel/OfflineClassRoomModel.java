package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.OfflineClassRoomContract;
import com.jianpei.jpeducation.repository.OfflineClassRoomRepository;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflineClassRoomModel extends BaseViewModel implements OfflineClassRoomContract.Model {

    private OfflineClassRoomRepository repository;

    public OfflineClassRoomModel() {
        repository = new OfflineClassRoomRepository();
    }


    //获取下载中的数据
    private MutableLiveData<Integer> downloadMedialInfosLiveData;

    public MutableLiveData<Integer> getDownloadMedialInfosLiveData() {
        if (downloadMedialInfosLiveData == null)
            downloadMedialInfosLiveData = new MutableLiveData<>();
        return downloadMedialInfosLiveData;
    }

    @Override
    public void getDownloadMedialInfos(int status) {

        repository.getDownloadMedialInfos(status).compose(setThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer downloadMediaInfos) {
                downloadMedialInfosLiveData.setValue(downloadMediaInfos);
            }

            @Override
            public void onError(Throwable e) {
                errData.setValue(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 获取下载完成的数据
     *
     * @param status
     */

    private MutableLiveData<List<DownloadMediaInfo>> completeDataLiveData;


    public MutableLiveData<List<DownloadMediaInfo>> getCompleteDataLiveData() {
        if (completeDataLiveData == null)
            completeDataLiveData = new MutableLiveData<>();
        return completeDataLiveData;
    }

    @Override
    public void getCompleteData(int status) {

        repository.getCompleteData(status).compose(setThread()).subscribe(new Observer<List<DownloadMediaInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<DownloadMediaInfo> downloadMediaInfos) {
                completeDataLiveData.setValue(downloadMediaInfos);
            }

            @Override
            public void onError(Throwable e) {
                errData.setValue(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
