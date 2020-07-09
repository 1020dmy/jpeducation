package com.jianpei.jpeducation.activitys.mine;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.viewmodel.CommentModel;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentClassActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_nums)
    TextView tvNums;
    @BindView(R.id.et_content)
    EditText etContent;

    private CommentModel commentModel;

    private String classId;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_comment_class;
    }

    @Override
    protected void initView() {
        tvTitle.setText("评价");
        tvRight.setText("提交");
        tvRight.setVisibility(View.VISIBLE);

        classId = getIntent().getStringExtra("classId");

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNums.setText("" + s.length());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData() {
        commentModel = new ViewModelProvider(this).get(CommentModel.class);
        commentModel.getCommentSuccessLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
                finish();
            }
        });
        commentModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

    }


    @OnClick({R.id.tv_right, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                showLoading("");
                commentModel.insertComment(classId, etContent.getText().toString());
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }


}
