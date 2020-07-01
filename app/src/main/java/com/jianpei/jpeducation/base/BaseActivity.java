package com.jianpei.jpeducation.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.utils.LoadingDialog;
import com.jianpei.jpeducation.utils.StatusBarUtil;
import com.jianpei.umeng.ShareActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

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
        UMShareAPI.get(this).release();
        unbinder.unbind();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    //分享相关
    public ShareAction mShareAction;
    private UMShareListener mShareListener;

    public void initShare() {
        mShareListener = new CustomShareListener(this);


        mShareAction = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                UMWeb web = new UMWeb("http://mobile.umeng.com/social");
                web.setTitle("来自分享面板标题");
                web.setDescription("来自分享面板内容");
                web.setThumb(new UMImage(BaseActivity.this, com.jianpei.umeng.R.drawable.ic_launcher));
                new ShareAction(BaseActivity.this).withMedia(web)
                        .setPlatform(share_media)
                        .setCallback(mShareListener)
                        .share();
            }
        });

    }

    private class CustomShareListener implements UMShareListener {

        private WeakReference<ShareActivity> mActivity;

        private CustomShareListener(Activity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {


            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
