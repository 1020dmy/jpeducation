package com.jianpei.jpeducation.activitys.mine;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.SigninAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;

import butterknife.BindView;

public class IntegralActivity extends BaseNoStatusActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private SigninAdapter signinAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_integral;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


    }

    @Override
    protected void initData() {
        signinAdapter = new SigninAdapter(this);
        recyclerView.setAdapter(signinAdapter);

    }


}
