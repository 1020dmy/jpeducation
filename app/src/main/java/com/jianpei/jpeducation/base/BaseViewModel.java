package com.jianpei.jpeducation.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.utils.L;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseViewModel<B> extends ViewModel {

    protected MutableLiveData<B> successData;


    protected MutableLiveData<String> errData;


    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public MutableLiveData<B> getScuucessData() {
        if (successData == null) {
            successData = new MutableLiveData<>();
        }
        return successData;
    }

    public MutableLiveData<String> getErrData() {
        if (errData == null) {
            errData = new MutableLiveData<String>();
        }
        return errData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        successData = null;
        errData = null;
    }
}
