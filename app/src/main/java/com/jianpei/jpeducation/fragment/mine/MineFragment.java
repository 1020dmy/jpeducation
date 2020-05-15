package com.jianpei.jpeducation.fragment.mine;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.UserInfoActivity;
import com.jianpei.jpeducation.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {


    @BindView(R.id.btn_info)
    Button btnInfo;

    @Override
    protected int initLayout() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Context mContext) {

    }


    @OnClick(R.id.btn_info)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), UserInfoActivity.class));
    }
}
