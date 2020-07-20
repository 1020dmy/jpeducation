package com.jianpei.jpeducation.activitys.school;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.school.ReplyListAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.ReplyDataBean;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.tv_dianzan_num)
    TextView tvDianzanNum;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_reply_name)
    TextView tvReplyName;
    @BindView(R.id.et_reply_content)
    EditText etReplyContent;
    @BindView(R.id.tv_reply_send)
    TextView tvReplySend;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;

    private EvaluationDataBean evaluationDataBean;

    private SchoolModel schoolModel;

    private List<ReplyDataBean> mReplyDataBeans;
    private ReplyListAdapter replyListAdapter;

    private String startId = "0", endId = "0";


    @Override
    protected int setLayoutView() {
        return R.layout.activity_reply_list;
    }

    @Override
    protected void initView() {
        evaluationDataBean = getIntent().getParcelableExtra("evaluationDataBean");
        tvTitle.setText(evaluationDataBean.getEvaluation_count() + "条回复");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                endId = "0";
                if (mReplyDataBeans.size() > 0) {
                    startId = mReplyDataBeans.get(0).getId();
                } else {
                    startId = "0";
                }
                schoolModel.replyData(evaluationDataBean.getThread_id(), evaluationDataBean.getId(), startId, endId);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                startId = "0";
                if (mReplyDataBeans.size() > 0) {
                    endId = mReplyDataBeans.get(mReplyDataBeans.size() - 1).getId();
                } else {
                    endId = "0";
                }
                schoolModel.replyData(evaluationDataBean.getThread_id(), evaluationDataBean.getId(), startId, endId);
            }
        });


    }

    @Override
    protected void initData() {
        mReplyDataBeans = new ArrayList<>();
        replyListAdapter = new ReplyListAdapter(mReplyDataBeans, this);
        recyclerView.setAdapter(replyListAdapter);

        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);
        //回复列表
        schoolModel.getReplyDataBeansLiveData().observe(this, new Observer<List<ReplyDataBean>>() {
            @Override
            public void onChanged(List<ReplyDataBean> replyDataBeans) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                mReplyDataBeans.addAll(replyDataBeans);
                if (replyDataBeans != null && replyDataBeans.size() > 0) {
                    if ("0".equals(endId)) {
                        mReplyDataBeans.addAll(0, replyDataBeans);
                    } else {
                        mReplyDataBeans.addAll(replyDataBeans);
                    }
                    replyListAdapter.notifyDataSetChanged();
                }
            }
        });
        //点赞/取消点赞
        schoolModel.getGardenPraiseBeanLiveData().observe(this, new Observer<GardenPraiseBean>() {
            @Override
            public void onChanged(GardenPraiseBean gardenPraiseBean) {
                dismissLoading();
                if (gardenPraiseBean.getPost_info() != null) {

                    if ("1".equals(gardenPraiseBean.getPost_info().getIs_praise())) {//是否点赞1是，0否
                        ivDianzan.setImageResource(R.drawable.school_undianzan_icon);
                    } else {
                        ivDianzan.setImageResource(R.drawable.school_dianzan_icon);
                    }
                    if ("0".equals(gardenPraiseBean.getPost_info().getLike_num()))
                        tvDianzanNum.setText("");
                    else {
                        tvDianzanNum.setText(gardenPraiseBean.getPost_info().getLike_num());
                    }
                }

            }
        });
        //评论
        schoolModel.getInsertEvaluationLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shortToast(s);
                endId = "0";
                if (mReplyDataBeans.size() > 0) {
                    startId = mReplyDataBeans.get(0).getId();
                } else {
                    startId = "0";
                }
                schoolModel.replyData(evaluationDataBean.getThread_id(), evaluationDataBean.getId(), startId, endId);
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
        showLoading("");
        schoolModel.replyData(evaluationDataBean.getThread_id(), evaluationDataBean.getId(), startId, endId);

        setData();
    }

    private void setData() {
        if (evaluationDataBean != null) {
            Glide.with(this).load(evaluationDataBean.getUser_img()).into(civHead);
            tvName.setText(evaluationDataBean.getUser_name());
            tvTime.setText(evaluationDataBean.getCreated_at_str());
            tvContent.setText(evaluationDataBean.getContent());
            if ("1".equals(evaluationDataBean.getIs_praise())) {//是否点赞1是，0否
                ivDianzan.setImageResource(R.drawable.school_undianzan_icon);
            } else {
                ivDianzan.setImageResource(R.drawable.school_dianzan_icon);
            }
            if ("0".equals(evaluationDataBean.getLike_num()))
                tvDianzanNum.setText("");
            else {
                tvDianzanNum.setText(evaluationDataBean.getLike_num());
            }
        }
    }


    @OnClick({R.id.iv_back, R.id.iv_dianzan, R.id.tv_reply_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_dianzan:
                showLoading("");
                schoolModel.gardenPraise("3", evaluationDataBean.getThread_id(), "", evaluationDataBean.getId());
                break;
            case R.id.tv_reply_send:
                showLoading("");
                schoolModel.insertEvaluation(evaluationDataBean.getThread_id(), etReplyContent.getText().toString(), evaluationDataBean.getId(), evaluationDataBean.getUser_id());
                break;
        }
    }
}
