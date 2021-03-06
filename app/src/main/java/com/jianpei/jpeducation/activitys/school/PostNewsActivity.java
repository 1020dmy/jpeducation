package com.jianpei.jpeducation.activitys.school;


import android.content.Intent;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.school.PostNewsPhotoAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.utils.BitmapUtil;
import com.jianpei.jpeducation.utils.FileUtils;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
import com.jianpei.jpeducation.utils.dialog.PostNewsResultDialog;
import com.jianpei.jpeducation.utils.keyboard.OnSoftKeyBoardChangeListener;
import com.jianpei.jpeducation.utils.keyboard.SoftKeyBoardListener;
import com.jianpei.jpeducation.view.contentedittext.TEditText;
import com.jianpei.jpeducation.view.contentedittext.TObject;
import com.jianpei.jpeducation.viewmodel.PostNewsModel;
import com.jianpei.jpeducation.viewmodel.UploadFileModel;
import com.shuyu.textutillib.RichEditBuilder;
import com.shuyu.textutillib.RichEditText;
import com.shuyu.textutillib.listener.OnEditTextUtilJumpListener;
import com.shuyu.textutillib.model.TopicModel;
import com.shuyu.textutillib.model.UserModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PostNewsActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    //    @BindView(R.id.et_content)
//    ContentEditText etContent;
    @BindView(R.id.et_content)
    RichEditText etContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_huati)
    ImageView ivHuati;
    @BindView(R.id.iv_people)
    ImageView ivPeople;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private SelectphotoUtils selectphotoUtils;

    private PostNewsPhotoAdapter postNewsPhotoAdapter;

    private List<File> files;

//    private ArrayList<TopicBean> selectTopicBean;//选择的话题

    //选择的关注
//    private ArrayList<AttentionBean> selectAttentionBeans;

    private ArrayList<TopicModel> topicModels = new ArrayList<>();
    private ArrayList<UserModel> userModels = new ArrayList<>();

    private PostNewsModel postNewsModel;

    private PostNewsResultDialog postNewsResultDialog;

    private UploadFileModel uploadFileModel;//上传图片

//    private StringBuilder stringBuilder;

//    private String topics;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_post_news;
    }

    @Override
    protected void initView() {

        tvTitle.setText("发布动态");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("发布");

        SoftKeyBoardListener.setListener(this, new OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow() {
                //显示
                llBottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide() {
                //隐藏
                llBottom.setVisibility(View.GONE);

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        RichEditBuilder richEditBuilder = new RichEditBuilder();
        richEditBuilder.setEditText(etContent)
                .setTopicModels(topicModels)
                .setUserModels(userModels)
                .setEditTextAtUtilJumpListener(new OnEditTextUtilJumpListener() {
                    @Override
                    public void notifyAt() {
//                        JumpUtil.goToUserList(MainActivity.this, MainActivity.REQUEST_USER_CODE_INPUT);
                    }

                    @Override
                    public void notifyTopic() {
//                        JumpUtil.goToTopicList(MainActivity.this, MainActivity.REQUEST_TOPIC_CODE_INPUT);
                    }
                })
                .builder();
    }

    @Override
    protected void initData() {
        files = new ArrayList<>();
        postNewsPhotoAdapter = new PostNewsPhotoAdapter(files, this);
        postNewsPhotoAdapter.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                files.remove(position);
                postNewsPhotoAdapter.notifyItemChanged(position);

            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

            }
        });
        recyclerView.setAdapter(postNewsPhotoAdapter);

        postNewsModel = new ViewModelProvider(this).get(PostNewsModel.class);
        postNewsModel.getSuccessLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                if (postNewsResultDialog == null)
                    postNewsResultDialog = new PostNewsResultDialog(PostNewsActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissLoading();
                            finish();
                        }
                    });
                postNewsResultDialog.show();
            }
        });

        postNewsModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //上传图片
        uploadFileModel = new ViewModelProvider(this).get(UploadFileModel.class);
        uploadFileModel.getSuccessLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                postNewsModel.insertGarden(etContent.getText().toString(), strings, userModels, topicModels);
            }
        });
        uploadFileModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

    }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_icon, R.id.iv_huati, R.id.iv_people, R.id.iv_image, R.id.iv_camera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                L.e("=====size:" + topicModels.size() + "," + userModels.size());
//                etContent.getRealTopicList();
//                etContent.getRealUserList();
//                L.e("=====cotent:"+etContent.getRealText());
                showLoading("");
                if (files.size() > 0) {
                    uploadFileModel.uploadFile("image", files);
                } else {
                    postNewsModel.insertGarden(etContent.getText().toString(), null, userModels, topicModels);
                }
                break;
            case R.id.iv_icon:
                break;
            case R.id.iv_huati:
                startActivityForResult(new Intent(this, TopicListActivity.class), 101);
                break;
            case R.id.iv_people:
                startActivityForResult(new Intent(this, MineAttentionActivity.class), 102);
                break;
            case R.id.iv_image:
                if (files.size() < 6) {
                    if (selectphotoUtils == null)
                        selectphotoUtils = new SelectphotoUtils(this);
                    selectphotoUtils.openGallery();
                } else {
                    shortToast("最多添加6张照片");
                }
                break;
            case R.id.iv_camera:
                if (files.size() < 6) {
                    if (selectphotoUtils == null)
                        selectphotoUtils = new SelectphotoUtils(this);
                    selectphotoUtils.takePhone();
                } else {
                    shortToast("最多添加6张照片");
                }

                break;
        }
    }

    FileUtils fileUtils;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SelectphotoUtils.REQUEST_TAKE_PHOTO: // 拍照并进行裁剪
                L.e("拍照返回");
//                selectphotoUtils.cropPhoto(selectphotoUtils.imgUri, true);
                String aaa = BitmapUtil.compressImage2(selectphotoUtils.imgFile.getPath());//图片压缩
                files.add(new File(aaa));
                postNewsPhotoAdapter.notifyItemChanged(files.size() - 1);
                break;
            case SelectphotoUtils.SCAN_OPEN_PHONE://相册返回
                L.e("相册返回");
                if (data != null) {
                    if (fileUtils == null) {
                        fileUtils = new FileUtils(this);
                    }
                    String bbb = BitmapUtil.compressImage(fileUtils.getFilePathByUri(data.getData()), getExternalCacheDir() + "/image");//图片压缩
                    files.add(new File(bbb));
                    postNewsPhotoAdapter.notifyItemChanged(files.size() - 1);
                }
                break;
            case SelectphotoUtils.REQUEST_CROP://裁剪返回
                L.e("裁剪返回");
//                imageUrls.add(selectphotoUtils.mCutUri);
//                postNewsPhotoAdapter.notifyDataSetChanged();
                break;
            case 101:
                if (data == null)
                    return;
                List<TopicBean> topicBeans = data.getParcelableArrayListExtra("selectTopicBean");
                getResultData(topicBeans);
                break;
            case 102:
                if (data == null)
                    return;
                List<AttentionBean> attentionBeans = data.getParcelableArrayListExtra("selectAttentionBeans");
                getResultData2(attentionBeans);
                break;

        }
    }


    private void getResultData(List<TopicBean> topicBeans) {
        if (topicBeans != null && topicBeans.size() > 0) {
            for (TopicBean topicBean : topicBeans) {
                TopicModel topicModel = new TopicModel(topicBean.getTitle(), topicBean.getId());
//                topicModels.add(topicModel);
                etContent.resolveTopicResult(topicModel);
            }
        }
    }

    private void getResultData2(List<AttentionBean> selectAttentionBeans) {
        if (selectAttentionBeans != null && selectAttentionBeans.size() > 0) {
            for (AttentionBean attentionBean : selectAttentionBeans) {
                UserModel userModel = new UserModel(attentionBean.getUser_name(), attentionBean.getId());
//                userModels.add(userModel);
                etContent.resolveAtResult(userModel);

            }
        }
    }


    @Override
    protected void onDestroy() {
        if (postNewsResultDialog != null)
            postNewsResultDialog.dismiss();
        postNewsResultDialog = null;
        super.onDestroy();
    }
    public void showSoftInputFromWindow(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onResume() {
        showSoftInputFromWindow(etContent);
        super.onResume();
    }
}
