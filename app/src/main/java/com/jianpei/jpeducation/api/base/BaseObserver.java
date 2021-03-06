package com.jianpei.jpeducation.api.base;

import android.accounts.NetworkErrorException;
import android.content.Intent;

import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.utils.SpUtils;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {


    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        onRequestEnd();
        try {
            if (tBaseEntity.isLogin()) {
                SpUtils.remove(SpUtils.ID);
                MyApplication.getInstance().startActivity(new Intent(MyApplication.getInstance(), LoginActivity.class).setFlags(FLAG_ACTIVITY_NEW_TASK));
                onComplete();
                return;
            }
            onSuccees(tBaseEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {
    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
    }

    protected void onRequestEnd() {

    }

}
