package com.jianpei.jpeducation.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jianpei.jpeducation.utils.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseFragment extends Fragment {


    public Context context;

    private Unbinder unbinder;

    private Dialog dialog;
    private Toast toast;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(initLayout(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initData(context);
        return rootView;

    }

    protected abstract int initLayout();

    protected abstract void initView(final View view);

    protected abstract void initData(Context mContext);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示加载框
     *
     * @param message
     */
    public void showLoading(String message) {
        //加载弹窗
        if (dialog == null) {
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(true)//返回键是否可点击
                    .setCancelOutside(false);//窗体外是否可点击
            dialog = loadBuilder.create();
        }
        dialog.show();//显示弹窗

    }

    /**
     * 取消加载框
     */
    public void dismissLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    public void shortToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}
