package com.jianpei.jpeducation.base;

import android.app.Activity;
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
import com.jianpei.umeng.ShareActivity;
import com.umeng.socialize.ShareAction;
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


    //分享相关
    public ShareAction mShareAction;
    private UMShareListener mShareListener;

    public void initShare() {
        mShareListener = new CustomShareListener(getActivity());


        mShareAction = new ShareAction(getActivity()).setDisplayList(
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
                web.setThumb(new UMImage(getActivity(), com.jianpei.umeng.R.drawable.ic_launcher));
                new ShareAction(getActivity()).withMedia(web)
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
