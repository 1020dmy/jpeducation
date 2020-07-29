package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mine.FeedbackAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.utils.FileUtils;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SelectphotoUtils;
import com.jianpei.jpeducation.utils.dialog.PhotoSelectDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements MyItemOnClickListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_contact)
    EditText etContact;

    private PhotoSelectDialog photoSelectDialog;
    private SelectphotoUtils selectphotoUtils;


    private FeedbackAdapter feedbackAdapter;

    private List<File> files;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        tvTitle.setText("意见反馈");
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one:
                        break;
                    case R.id.rb_two:
                        break;
                    case R.id.rb_three:
                        break;
                    case R.id.rb_four:
                        break;
                }

            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tvNum.setText(s.length() + "");

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


    }

    @Override
    protected void initData() {
        selectphotoUtils = new SelectphotoUtils(this);

        files = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(files, this);
        feedbackAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(feedbackAdapter);

    }


    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:

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
                files.add(selectphotoUtils.imgFile);
                feedbackAdapter.notifyItemChanged(files.size() - 1);
                break;
            case SelectphotoUtils.SCAN_OPEN_PHONE://相册返回
                L.e("相册返回");
                if (data != null) {
                    if (fileUtils == null) {
                        fileUtils = new FileUtils(this);
                    }
                    files.add(new File(fileUtils.getFilePathByUri(data.getData())));
                    feedbackAdapter.notifyItemChanged(files.size() - 1);

                }
                break;
            case SelectphotoUtils.REQUEST_CROP://裁剪返回
                L.e("裁剪返回");
//                imageUrls.add(selectphotoUtils.mCutUri);
//                postNewsPhotoAdapter.notifyDataSetChanged();
                break;

        }
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.iv_delete:
                files.remove(position);
                feedbackAdapter.notifyDataSetChanged();
                break;
            case R.id.imageView:
                if (photoSelectDialog == null) {
                    photoSelectDialog = new PhotoSelectDialog(this, selectphotoUtils);
                }
                photoSelectDialog.show();
                break;
        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
