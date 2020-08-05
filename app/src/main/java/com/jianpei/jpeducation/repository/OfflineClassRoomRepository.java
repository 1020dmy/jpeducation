package com.jianpei.jpeducation.repository;

import com.aliyun.downloader.AliDownloaderFactory;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.contract.OfflineClassRoomContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.FileUtils;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/3
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OfflineClassRoomRepository extends BaseRepository implements OfflineClassRoomContract.Repository {

    @Override
    public Observable<List<ViodBean>> getRoomViodBean(int status) {
        return Observable.create(new ObservableOnSubscribe<List<ViodBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ViodBean>> emitter) throws Exception {
                emitter.onNext(MyRoomDatabase.getInstance().viodBeanDao().getViodBeans(status));

                emitter.onComplete();


            }
        });
    }

//    @Override
//    public Observable<List<DownloadMediaInfo>> getCompleteData(int status) {
//        return Observable.create(new ObservableOnSubscribe<List<DownloadMediaInfo>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<DownloadMediaInfo>> emitter) throws Exception {
//                List<DownloadMediaInfo> downloadMediaInfos = MyRoomDatabase.getInstance().downloadMediaInfoDao().getViodBeans(status);
//                emitter.onNext(downloadMediaInfos);
//                emitter.onComplete();
//
//
//            }
//        });
//    }

    @Override
    public Observable<List<DirectoryBean>> getOfflineCompleteData() {
        return Observable.create(new ObservableOnSubscribe<List<DirectoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DirectoryBean>> emitter) throws Exception {

                List<DirectoryBean> directoryBeans = MyRoomDatabase.getInstance().directoryDao().getAllDirectoryBeans();
                Iterator<DirectoryBean> directoryBeanIterator = directoryBeans.iterator();
                while (directoryBeanIterator.hasNext()) {
                    DirectoryBean directoryBean = directoryBeanIterator.next();
                    List<ViodBean> downloadMediaInfos = MyRoomDatabase.getInstance().viodBeanDao().getViodBeans(directoryBean.getId(), 5);
                    if (downloadMediaInfos != null && downloadMediaInfos.size() > 0) {//
                        directoryBean.setViods(downloadMediaInfos);
                    } else {
                        directoryBeanIterator.remove();
                    }
                }
                emitter.onNext(directoryBeans);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<DirectoryBean>> getUndone() {
        return Observable.create(new ObservableOnSubscribe<List<DirectoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DirectoryBean>> emitter) throws Exception {

                List<DirectoryBean> directoryBeans = MyRoomDatabase.getInstance().directoryDao().getAllDirectoryBeans();
                Iterator<DirectoryBean> directoryBeanIterator = directoryBeans.iterator();
                while (directoryBeanIterator.hasNext()) {
                    DirectoryBean directoryBean = directoryBeanIterator.next();
                    List<ViodBean> downloadMediaInfos = MyRoomDatabase.getInstance().viodBeanDao().getUndone(directoryBean.getId(), 5);
                    if (downloadMediaInfos != null && downloadMediaInfos.size() > 0) {//
                        directoryBean.setViods(downloadMediaInfos);
                    } else {
                        directoryBeanIterator.remove();
                    }
                }
                emitter.onNext(directoryBeans);
                emitter.onComplete();


            }
        });
    }

    @Override
    public Observable<String> deleteViodBean(ViodBean viodBean) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (viodBean != null) {
                    L.e("=====:" + viodBean.getSavePath() + "," + viodBean.getVid() + "," + viodBean.getFormat() + "," + viodBean.getQualityIndex());
                    int result = AliDownloaderFactory.deleteFile(viodBean.getSavePath(), viodBean.getVid(), viodBean.getFormat(), viodBean.getQualityIndex());
                    MyRoomDatabase.getInstance().viodBeanDao().delete(viodBean);
                    FileUtils.deleteFile(new File(viodBean.getSavePath()));
                    String file = viodBean.getSavePath().replace("." + viodBean.getFormat(), "");
                    FileUtils.deleteDirWihtFile(new File(file));
                    emitter.onNext("删除完成" + result);
                }
                emitter.onComplete();


            }
        });
    }
}
