package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
import com.jianpei.umeng.ShareActivity;


import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseNoStatusActivity {

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

}
