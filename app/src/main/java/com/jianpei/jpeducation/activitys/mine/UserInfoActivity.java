package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
import com.jianpei.jpeducation.utils.dialog.ChangeNameDialog;
import com.jianpei.jpeducation.utils.dialog.ChangeSexDialog;
import com.jianpei.jpeducation.utils.dialog.DatePickerDialog;
import com.jianpei.jpeducation.utils.dialog.PhotoSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseNoStatusActivity {

    @BindView(R.id.iv_back)
    ImageView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_wx)
    LinearLayout llWx;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;


    private SelectphotoUtils selectphotoUtils;

    //日期弹窗
    private DatePickerDialog datePickerDialog;
    //修改头像弹窗
    private PhotoSelectDialog photoSelectDialog;
    //修改姓名弹窗
    private ChangeNameDialog changeNameDialog;
    //性别修改弹窗
    private ChangeSexDialog changeSexDialog;


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
        //初始化相册工具
        selectphotoUtils = new SelectphotoUtils(this);

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


    @Override
    protected void onDestroy() {
        if (selectphotoUtils != null) {
            selectphotoUtils.Release();
            selectphotoUtils = null;
        }
        super.onDestroy();

    }

    @OnClick({R.id.ll_head, R.id.ll_name, R.id.ll_sex, R.id.ll_birthday, R.id.ll_phone, R.id.ll_wx, R.id.ll_pwd, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_head:
                if (photoSelectDialog == null) {
                    photoSelectDialog = new PhotoSelectDialog(this, selectphotoUtils);
                }
                photoSelectDialog.show();
                break;
            case R.id.ll_name:
                if (changeNameDialog == null) {
                    changeNameDialog = new ChangeNameDialog(this);
                }
                changeNameDialog.show();
                break;
            case R.id.ll_sex:
                if (changeSexDialog == null) {
                    changeSexDialog = new ChangeSexDialog(this);
                }
                changeSexDialog.show();
                break;
            case R.id.ll_birthday:
                if (datePickerDialog == null)
                    datePickerDialog = new DatePickerDialog(this);
                datePickerDialog.show();
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_wx:
                break;
            case R.id.ll_pwd:
                break;
        }
    }
}
