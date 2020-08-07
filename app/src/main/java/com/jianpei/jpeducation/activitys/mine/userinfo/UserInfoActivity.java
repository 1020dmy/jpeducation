package com.jianpei.jpeducation.activitys.mine.userinfo;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.BindPhoneActivity;
import com.jianpei.jpeducation.activitys.login.ForgetPwdActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
import com.jianpei.jpeducation.utils.dialog.ChangeNameDialog;
import com.jianpei.jpeducation.utils.dialog.ChangeSexDialog;
import com.jianpei.jpeducation.utils.dialog.DatePickerDialog;
import com.jianpei.jpeducation.utils.dialog.PhotoSelectDialog;
import com.jianpei.jpeducation.viewmodel.UploadFileModel;
import com.jianpei.jpeducation.viewmodel.UserInfoModel;
import com.jianpei.jpeducation.viewmodel.WxLoginModel;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_status)
    TextView tvStatus;


    private SelectphotoUtils selectphotoUtils;

    //日期弹窗
    private DatePickerDialog datePickerDialog;
    //修改头像弹窗
    private PhotoSelectDialog photoSelectDialog;
    //修改姓名弹窗
    private ChangeNameDialog changeNameDialog;
    //性别修改弹窗
    private ChangeSexDialog changeSexDialog;


    private UserInfoModel userInfoModel;
    private WxLoginModel wxLoginModel;


    private UploadFileModel uploadFileModel;

    private String sexType;

    private UserInfoBean mUserInfoBean;

    private String birthDay;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人信息");
        tvStatus.setVisibility(View.VISIBLE);
        setTitleViewPadding(tvStatus);
        userInfoModel = new ViewModelProvider(this).get(UserInfoModel.class);
        uploadFileModel = new ViewModelProvider(this).get(UploadFileModel.class);
        //微信
        wxLoginModel = new ViewModelProvider(this).get(WxLoginModel.class);

    }

    @Override
    protected void initData() {
        //初始化相册工具
        selectphotoUtils = new SelectphotoUtils(this);
        //

        //用户信息
        userInfoModel.getUserInfoBeanLiveData().observe(this, new Observer<UserInfoBean>() {
            @Override
            public void onChanged(UserInfoBean userInfoBean) {
                dismissLoading();
                setData(userInfoBean);
            }
        });
        //错误返回
        userInfoModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //头像上传成功
        uploadFileModel.getSuccessLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {

                userInfoModel.editUser(strings.get(0), tvName.getText().toString(), sexType, tvBirthday.getText().toString());
            }
        });
        //上传失败
        uploadFileModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });
        //授权成功
        wxLoginModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                userInfoModel.userInfo();
            }
        });
        wxLoginModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
            }
        });
        showLoading("");
        userInfoModel.userInfo();
    }

    private void setData(UserInfoBean userInfoBean) {

        if (userInfoBean == null)
            return;
        mUserInfoBean = userInfoBean;
        Glide.with(this).load(userInfoBean.getImg()).placeholder(R.drawable.ic_launcher).into(imageView);
        tvName.setText(userInfoBean.getUser_name());
        sexType = userInfoBean.getSex();
        if ("1".equals(sexType)) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        birthDay = userInfoBean.getBirthday();
        tvBirthday.setText(userInfoBean.getBirthday());
        if (TextUtils.isEmpty(userInfoBean.getPhone())) {
            tvPhone.setText("去绑定");
        } else {
            tvPhone.setText(userInfoBean.getPhone());
        }
        if (TextUtils.isEmpty(userInfoBean.getOpenid())) {
            tvWx.setText("未绑定");
        } else {
            tvWx.setText("已绑定");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SelectphotoUtils.REQUEST_TAKE_PHOTO: // 拍照并进行裁剪
                L.e("拍照返回");
                if (resultCode != Activity.RESULT_CANCELED)
                    selectphotoUtils.cropPhoto(selectphotoUtils.getUri(selectphotoUtils.imgFile), true);
                break;
            case SelectphotoUtils.SCAN_OPEN_PHONE://相册返回
                L.e("相册返回");
                if (resultCode != Activity.RESULT_CANCELED) {
                    selectphotoUtils.cropPhoto(data.getData(), false);
                }
                break;
            case SelectphotoUtils.REQUEST_CROP://裁剪返回
                L.e("裁剪返回");

                if (resultCode != Activity.RESULT_CANCELED) {
                    showLoading("");
                    Glide.with(this).load(selectphotoUtils.mCutFile).placeholder(R.mipmap.ic_launcher).into(imageView);
                    List<File> files = new ArrayList<>();
                    files.add(selectphotoUtils.mCutFile);
                    uploadFileModel.uploadFile("image", files);
                }
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showLoading("");
        userInfoModel.userInfo();
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
                    changeNameDialog = new ChangeNameDialog(this, tvName.getText().toString());
                    changeNameDialog.setMyOnItemClickListener(new ChangeNameDialog.MyOnItemClickListener() {
                        @Override
                        public void onSaveClick(String name) {
                            if (!TextUtils.isEmpty(name)) {
                                showLoading("");
                                tvName.setText(name);
                                userInfoModel.editUser(mUserInfoBean.getImg(), tvName.getText().toString(), mUserInfoBean.getSex(), mUserInfoBean.getBathday());
                                changeNameDialog.dismiss();
                            } else {
                                shortToast("昵称不能为空");
                            }
                        }
                    });
                }
                changeNameDialog.show();
                break;
            case R.id.ll_sex:
                if (changeSexDialog == null) {
                    changeSexDialog = new ChangeSexDialog(this, sexType);
                    changeSexDialog.setMyOnItemClickListener(new ChangeSexDialog.MyOnItemClickListener() {
                        @Override
                        public void onChangeSex(String asexType) {
                            if (!asexType.equals(sexType)) {
                                showLoading("");
                                userInfoModel.editUser(mUserInfoBean.getImg(), tvName.getText().toString(), asexType, mUserInfoBean.getBathday());
                            }

                        }
                    });
                }
                changeSexDialog.setSex(sexType);
                changeSexDialog.show();
                break;
            case R.id.ll_birthday:
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(this);
                    datePickerDialog.setMyOnItemClickListener(new DatePickerDialog.MyOnItemClickListener() {
                        @Override
                        public void onChangeBirthday(String date) {
                            if (!date.equals(mUserInfoBean.getBathday())) {
                                tvBirthday.setText(date);
                                showLoading("");
                                userInfoModel.editUser(mUserInfoBean.getImg(), tvName.getText().toString(), sexType, date);
                            }
                        }
                    });
                }
                datePickerDialog.setBirthday(birthDay);
                datePickerDialog.show();
                break;
            case R.id.ll_phone:
                if (TextUtils.isEmpty(mUserInfoBean.getPhone())) {
                    startActivity(new Intent(this, BindPhoneActivity.class));
                } else {
                    startActivity(new Intent(this, ChangePhoneActivity.class).putExtra("phone", mUserInfoBean.getPhone()));
                }
                break;
            case R.id.ll_wx:
                if (TextUtils.isEmpty(mUserInfoBean.getOpenid())) {
                    aaa();
                }
                break;
            case R.id.ll_pwd:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void aaa() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            if (map != null) {
                showLoading("");
                wxLoginModel.wxLogin(
                        map.get("refreshToken"),
                        map.get("expiration"),
                        map.get("screen_name"),
                        map.get("access_token"),
                        map.get("city"),
                        map.get("gender"),
                        map.get("openid"),
                        map.get("province"),
                        map.get("iconurl"));
            }

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            shortToast("授权失败");

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            shortToast("取消授权");

        }
    };
}

