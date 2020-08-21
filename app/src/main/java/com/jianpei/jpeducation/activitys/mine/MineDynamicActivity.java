package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.school.PostInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mine.MineDynamicAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.school.AttentionResultBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.MThreadBean;
import com.jianpei.jpeducation.bean.school.MThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.userinfo.UserInfoBean;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineDynamicActivity extends BaseNoStatusActivity implements MyItemOnClickListener {


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
    @BindView(R.id.tv_fensi)
    TextView tvFensi;
    @BindView(R.id.tv_guanzhu)
    TextView tvGuanzhu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.btn_status)
    Button btn_status;

    private SchoolModel schoolModel;

    private List<ThreadDataBean> threadDataBeans;

    private MineDynamicAdapter dynamicAdapter;

    private int page = 1, pageSize = 10;

//    private UserInfoBean userInfoBean;

    private int mPosition;

    private String userId, threadId;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_dynamic;
    }

    @Override
    protected void initView() {
        tvTitle.setText("个人主页");
        tvTitle.setTextColor(getResources().getColor(R.color.transparents));
        setTitleViewPadding(tvStatus);
        tvStatus.setVisibility(View.VISIBLE);
        llTitle.setBackgroundColor(getResources().getColor(R.color.transparents));
        ivBack.setImageResource(R.drawable.info_back);

//        userInfoBean = getIntent().getParcelableExtra("mUserInfoBean");
        userId = getIntent().getStringExtra("userId");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page = 1;
                schoolModel.mThreadData(page, pageSize, userId);


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                schoolModel.mThreadData(page, pageSize, userId);
            }
        });
    }

    @Override
    protected void initData() {
        threadDataBeans = new ArrayList<>();
        dynamicAdapter = new MineDynamicAdapter(threadDataBeans, this);
        dynamicAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(dynamicAdapter);
        //我的动态
        schoolModel.getmThreadDataBeanLiveData().observe(this, new Observer<MThreadDataBean>() {
            @Override
            public void onChanged(MThreadDataBean mThreadDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                setData(mThreadDataBean);
            }
        });
        schoolModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);

            }
        });
        //关注/取消关注
        schoolModel.getAttentionLiveData().observe(this, new Observer<AttentionResultBean>() {
            @Override
            public void onChanged(AttentionResultBean ss) {
                dismissLoading();
                changeStatus(ss.getIs_attention());

            }
        });

        //删除我的动态
        schoolModel.getDelThreadLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
                if (mPosition != -1) {
                    threadDataBeans.remove(mPosition);
                    dynamicAdapter.notifyDataSetChanged();
                }
                mPosition = -1;


            }
        });
        //点赞/取消点赞
        schoolModel.getGardenPraiseBeanLiveData().observe(this, new Observer<GardenPraiseBean>() {
            @Override
            public void onChanged(GardenPraiseBean gardenPraiseBean) {
                dismissLoading();
                threadDataBeans.get(mPosition).setIs_praise(gardenPraiseBean.getThread_info().getIs_praise());
                threadDataBeans.get(mPosition).setLike_num(gardenPraiseBean.getThread_info().getLike_num());
                dynamicAdapter.notifyItemChanged(mPosition);
            }
        });


        showLoading("");
        schoolModel.mThreadData(page, pageSize, userId);

    }


    private void changeStatus(int status) {
        if (0 == status) {
            btn_status.setVisibility(View.VISIBLE);
            btn_status.setText("关注+");
            btn_status.setTextColor(getResources().getColor(R.color.cE73B30));
            btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
        } else if (1 == status) {
            btn_status.setVisibility(View.VISIBLE);
            btn_status.setText("取消关注");
            btn_status.setTextColor(getResources().getColor(R.color.cA5A7B0));
            btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
        } else if (2 == status) {
            btn_status.setVisibility(View.GONE);
        }

    }

    private void setData(MThreadDataBean mThreadDataBean) {
        if (mThreadDataBean == null)
            return;
        UserInfoBean userInfoBean = mThreadDataBean.getData().getUserInfo();
        changeStatus(userInfoBean.getIs_attention());

        Glide.with(this).load(userInfoBean.getImg()).placeholder(R.drawable.head_icon).into(civHead);
        tvName.setText(userInfoBean.getUser_name());
        if ("1".equals(userInfoBean.getSex())) {
            ivSex.setImageResource(R.drawable.sex_boy);
        } else {
            ivSex.setImageResource(R.drawable.sex_gril);
        }
        MThreadBean.AttentionBean attentionBean = mThreadDataBean.getData().getAttention();
        tvFensi.setText(attentionBean.getAtten_count() + " 粉丝");
        tvGuanzhu.setText(attentionBean.getU_count() + " 关注");

        if (page == 1) {
            threadDataBeans.clear();
        }
        threadDataBeans.addAll(mThreadDataBean.getData().getData());
        dynamicAdapter.setIsAttention(userInfoBean.getIs_attention());
        dynamicAdapter.notifyDataSetChanged();


    }


    @Override
    public void onItemClick(int position, View view) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.ib_delete:
                showLoading("");
                schoolModel.delThread(threadDataBeans.get(position).getId());
                break;
            case R.id.relativeLayout:
                startActivity(new Intent(this, PostInfoActivity.class).putExtra("threadDataBean", threadDataBeans.get(position)));
                break;
            case R.id.iv_share:
                if (mShareAction == null)
                    initShare();
                mShareAction.open();
                break;
            case R.id.tv_dianzan:
            case R.id.iv_dianzan:
                showLoading("");
                schoolModel.gardenPraise("2", threadDataBeans.get(position).getId(), "", "");
                break;

        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }


    @OnClick({R.id.iv_back, R.id.btn_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_status:
                showLoading("");
                schoolModel.attention(userId, "", "", null);
                break;
        }
    }
}
