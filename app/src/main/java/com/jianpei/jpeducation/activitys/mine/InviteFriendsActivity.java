package com.jianpei.jpeducation.activitys.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class InviteFriendsActivity extends BaseNoStatusActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.civ_scan)
    CircleImageView civScan;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button)
    Button button;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initView() {
        tvTitle.setText("邀请好友");
        tvTitle.setTextColor(getResources().getColor(R.color.cEFCBA5));
        ivBack.setImageResource(R.drawable.invite_friends_back);

        tvStatus.setVisibility(View.VISIBLE);
        setTitleViewPadding(tvStatus);


        llTitle.setBackgroundColor(getResources().getColor(R.color.transparents));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.button:
                break;
        }
    }
}
