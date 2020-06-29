package com.jianpei.jpeducation.activitys.classinfo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupResultActivity extends BaseActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_back)
    Button btnBack;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_group_result;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.btn_one, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_one:
                break;
            case R.id.btn_back:
                break;
        }
    }
}
