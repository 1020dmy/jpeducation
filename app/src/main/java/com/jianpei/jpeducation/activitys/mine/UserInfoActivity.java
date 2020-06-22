package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
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

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseNoStatusActivity implements ShareBoardlistener {

    @BindView(R.id.iv_back)
    ImageView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_photo)
    Button btnPhoto;
    @BindView(R.id.btn_camera)
    Button btnCamera;

    @BindView(R.id.imageView)
    CircleImageView imageView;

    private AlertDialog dialog;

    private SelectphotoUtils selectphotoUtils;

    //分享相关
    private ShareAction mShareAction;
    private UMShareListener mShareListener;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人信息");

    }

    @Override
    protected void initData() {

        mShareAction = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(this);

    }

    @OnClick({R.id.iv_back, R.id.btn_photo, R.id.btn_camera, R.id.btn_pdf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_photo:
            case R.id.btn_camera:
                if (dialog == null) {
                    viewInit();
                }
                dialog.show();//显示对话框
                break;
            case R.id.btn_pdf:
//                startActivity(new Intent(this, PdfReaderActivity.class));
                startActivity(new Intent(this, ShareActivity.class));

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SelectphotoUtils.REQUEST_TAKE_PHOTO: // 拍照并进行裁剪
                L.e("拍照返回");
                selectphotoUtils.cropPhoto(selectphotoUtils.imgUri, true);
                break;
            case SelectphotoUtils.SCAN_OPEN_PHONE://相册返回
                L.e("相册返回");
                if (data != null) {
                    selectphotoUtils.cropPhoto(data.getData(), false);
                }
                break;
            case SelectphotoUtils.REQUEST_CROP://裁剪返回
                L.e("裁剪返回");
                Glide.with(this).load(selectphotoUtils.mCutUri).into(imageView);
                break;
        }
    }


    public void viewInit() {
        if (selectphotoUtils == null) {
            selectphotoUtils = new SelectphotoUtils(this);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//创建对话框
        View layout = getLayoutInflater().inflate(R.layout.dialog_photo_camera, null);//获取自定义布局
        TextView takePhotoTV = layout.findViewById(R.id.photograph);
        TextView choosePhotoTV = layout.findViewById(R.id.photo);
        TextView cancelTV = layout.findViewById(R.id.cancel);
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        takePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    selectphotoUtils.takePhone();

                    dialog.dismiss();
                } catch (Exception e) {
                    L.e(e.getMessage());
                }

            }
        });
        choosePhotoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectphotoUtils.openGallery();
                dialog.dismiss();
            }
        });

        builder.setView(layout);//设置对话框的布局
        dialog = builder.create();//生成最终的对话框
    }


    @Override
    protected void onDestroy() {
        if (selectphotoUtils != null) {
            selectphotoUtils.Release();
            selectphotoUtils = null;
        }
        super.onDestroy();

    }


    @Override
    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        UMWeb web = new UMWeb("http://mobile.umeng.com/social");
        web.setTitle("来自分享面板标题");
        web.setDescription("来自分享面板内容");
        web.setThumb(new UMImage(this, com.jianpei.umeng.R.drawable.ic_launcher));
        new ShareAction(this).withMedia(web)
                .setPlatform(share_media)
                .setCallback(mShareListener)
                .share();
    }

    private  class CustomShareListener implements UMShareListener {

        private WeakReference<ShareActivity> mActivity;

        private CustomShareListener(ShareActivity activity) {
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
