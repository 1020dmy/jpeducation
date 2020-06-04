package com.jianpei.jpeducation.base;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseModelFragment<T extends BaseViewModel, D> extends BaseFragment {


    protected T mViewModel;


    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        mViewModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String mssage) {
                dismissLoading();
                onError(mssage);
            }
        });

        mViewModel.getScuucessData().observe(this, new Observer<D>() {
            @Override
            public void onChanged(D data) {
                dismissLoading();
                onSuccess(data);
            }
        });

    }

    protected abstract void onError(String message);

    protected abstract void onSuccess(D data);

}
