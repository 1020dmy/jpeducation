package com.jianpei.jpeducation.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jianpei.jpeducation.utils.LoadingDialog;
import com.jianpei.jpeducation.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toast toast;
    private Unbinder unbinder;

    private Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setTransparent(this);


        setContentView(setLayoutView());
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }


    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int setLayoutView();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 吐司
     *
     * @param message
     */
    public void longToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public void shortToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public void inActivity() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void outActivity() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 显示加载框
     *
     * @param message
     */
    public void showLoading(String message) {
        //加载弹窗
        if (dialog == null) {
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(this)
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

    public void setStatusColor(int color) {

        StatusBarUtil.setColor(this, color);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

//    public void setTitleViewPadding(View view) {
//        int statusH = getStatusBarHeight();
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        linearParams.height = statusH;// 控件的宽强制设成30
//        view.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
//
//    }
//
//    private int getStatusBarHeight() {
//        return getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
//    }
}
