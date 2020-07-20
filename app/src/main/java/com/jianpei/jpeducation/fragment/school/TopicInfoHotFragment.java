package com.jianpei.jpeducation.fragment.school;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.school.PostInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.school.SchoolAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.ThreadFromTopicDataBean;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicInfoHotFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private String topic_id, is_hot = "1";

    private SchoolModel schoolModel;
    private SchoolAdapter schoolAdapter;
    private List<ThreadDataBean> mThreadDataBeans;


    private String startId = "0", endId = "0", follow = "2";
    private int indexPosition;
    //点赞参数
    private String type = "2";

    public TopicInfoHotFragment(String topic_id) {
        this.topic_id = topic_id;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_topic_inof_hot;
    }

    @Override
    protected void initView(View view) {
        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                endId = "0";
                if (mThreadDataBeans.size() > 0) {
                    startId = mThreadDataBeans.get(0).getId();
                } else {
                    startId = "0";
                }
                schoolModel.threadFromTopicData(startId, endId, follow, topic_id, is_hot);
                ;

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                startId = "0";
                if (mThreadDataBeans.size() > 0) {
                    endId = mThreadDataBeans.get(mThreadDataBeans.size() - 1).getId();
                } else {
                    endId = "0";
                }
                schoolModel.threadFromTopicData(startId, endId, follow, topic_id, is_hot);
                ;
            }
        });
    }

    @Override
    protected void initData(Context mContext) {
        mThreadDataBeans = new ArrayList<>();
        schoolAdapter = new SchoolAdapter(mThreadDataBeans, getActivity());
        schoolAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(schoolAdapter);
        //帖子列表
        schoolModel.getThreadFromTopicDataBeanLiveData().observe(this, new Observer<ThreadFromTopicDataBean>() {
            @Override
            public void onChanged(ThreadFromTopicDataBean threadFromTopicDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (threadFromTopicDataBean != null) {
                    if ("0".equals(endId)) {
                        mThreadDataBeans.addAll(0, threadFromTopicDataBean.getData());
                    } else {
                        mThreadDataBeans.addAll(threadFromTopicDataBean.getData());
                    }
                    schoolAdapter.notifyDataSetChanged();
                }
            }
        });
        //关注/取消关注
        schoolModel.getThreadDataBeanLiveData().observe(this, new Observer<ThreadDataBean>() {
            @Override
            public void onChanged(ThreadDataBean threadDataBean) {
                dismissLoading();
                schoolAdapter.notifyDataSetChanged();

            }
        });
        //点赞/取消点赞
        schoolModel.getGardenPraiseBeanLiveData().observe(this, new Observer<GardenPraiseBean>() {
            @Override
            public void onChanged(GardenPraiseBean gardenPraiseBean) {
                dismissLoading();
                mThreadDataBeans.get(indexPosition).setIs_praise(gardenPraiseBean.getThread_info().getIs_praise());
                mThreadDataBeans.get(indexPosition).setLike_num(gardenPraiseBean.getThread_info().getLike_num());
                schoolAdapter.notifyItemChanged(indexPosition);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading("");
        endId = "0";
        if (mThreadDataBeans.size() > 0) {
            startId = mThreadDataBeans.get(0).getId();
        } else {
            startId = "0";
        }
        schoolModel.threadFromTopicData(startId, endId, follow, topic_id, is_hot);
    }

    @Override
    public void onItemClick(int position, View view) {
        indexPosition = position;
        switch (view.getId()) {
            case R.id.btn_status://关注/取消关注
                showLoading("");
//                viewType = 0;
                schoolModel.attention(mThreadDataBeans.get(position).getUser_id(), "", mThreadDataBeans.get(position).getId(), mThreadDataBeans);
                break;
            case R.id.iv_share://分享
                break;
            case R.id.ll_dianzan://点赞/取消点赞
                showLoading("");
//                viewType = 1;
                schoolModel.gardenPraise(type, mThreadDataBeans.get(position).getId(), "", "");
                break;
            case R.id.tv_message://评论
                break;
            case R.id.linearLayout://详情
                startActivity(new Intent(getActivity(), PostInfoActivity.class).putExtra("threadDataBean", mThreadDataBeans.get(position)));
                break;
        }
    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
