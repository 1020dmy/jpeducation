package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.contract.OfflineClassRoomContract;
import com.jianpei.jpeducation.repository.OfflineClassRoomRepository;

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
    private MutableLiveData<Integer> viodBeanLiveData;

    public MutableLiveData<Integer> getViodBeanLiveData() {
        if (viodBeanLiveData == null)
            viodBeanLiveData = new MutableLiveData<>();
        return viodBeanLiveData;
    }

    /**
     * 查询当前下载数量
     * @param status
     */
    @Override
    public void getRoomViodBean(int status) {

        repository.getRoomViodBean(status).compose(setThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer nums) {
                viodBeanLiveData.setValue(nums);
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
     * 获取下载完成的章节和视频
     */
    private MutableLiveData<List<DirectoryBean>> directoryBeansLiveData;

    public MutableLiveData<List<DirectoryBean>> getDirectoryBeansLiveData() {
        if (directoryBeansLiveData == null)
            directoryBeansLiveData = new MutableLiveData<>();
        return directoryBeansLiveData;
    }

    @Override
    public void getOfflineCompleteData() {

        repository.getOfflineCompleteData().compose(setThread()).subscribe(new Observer<List<DirectoryBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<DirectoryBean> directoryBeans) {

                directoryBeansLiveData.setValue(directoryBeans);


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
     * 获取未下载完成的章节信息
     */
    @Override
    public void getUndone() {
        repository.getUndone().compose(setThread()).subscribe(new Observer<List<DirectoryBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<DirectoryBean> directoryBeans) {
                directoryBeansLiveData.setValue(directoryBeans);
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
     * 删除操作
     *
     * @param viodBean
     */

    private MutableLiveData<String> deleteResultLiveData;

    public MutableLiveData<String> getDeleteResultLiveData() {
        if (deleteResultLiveData == null)
            deleteResultLiveData = new MutableLiveData<>();
        return deleteResultLiveData;
    }

    @Override
    public void deleteViodBean(ViodBean viodBean) {
        repository.deleteViodBean(viodBean).compose(setThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                deleteResultLiveData.setValue(s);


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
