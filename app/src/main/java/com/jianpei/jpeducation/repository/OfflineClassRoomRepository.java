package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.contract.OfflineClassRoomContract;
import com.jianpei.jpeducation.room.MyRoomDatabase;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;

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
    public Observable<Integer> getDownloadMedialInfos(int status) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(VideoDownloadManager.getInstance().getDownloadingList().size());
//                if (VideoDownloadManager.getInstance().getDownloadingList()==null){
//                    List<DownloadMediaInfo> downloadMediaInfos = MyRoomDatabase.getInstance().downloadMediaInfoDao().getViodBeans(status);
//                    emitter.onNext(downloadMediaInfos);
//                }else{
//                    emitter.onNext(v);
//                }
                emitter.onComplete();


            }
        });
    }

    @Override
    public Observable<List<DownloadMediaInfo>> getCompleteData(int status) {
        return Observable.create(new ObservableOnSubscribe<List<DownloadMediaInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DownloadMediaInfo>> emitter) throws Exception {
                List<DownloadMediaInfo> downloadMediaInfos = MyRoomDatabase.getInstance().downloadMediaInfoDao().getViodBeans(status);
                emitter.onNext(downloadMediaInfos);
                emitter.onComplete();


            }
        });
    }
}
