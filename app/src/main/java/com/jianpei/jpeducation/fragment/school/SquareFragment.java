package com.jianpei.jpeducation.fragment.school;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.school.PostInfoActivity;
import com.jianpei.jpeducation.activitys.school.TopicInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.school.HotTopicAdapter;
import com.jianpei.jpeducation.adapter.school.SchoolAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.bean.school.TopicDataBean;
import com.jianpei.jpeducation.utils.MyLayoutManager;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.jianpei.jpeducation.viewmodel.TopicListModel;
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
public class SquareFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_topic)
    RecyclerView rvTopic;

    private SchoolAdapter schoolAdapter;

    private SchoolModel schoolModel;


    private List<ThreadDataBean> mThreadDataBeans;

    private String startId = "0", endId = "0", follow = "0";

    ///
    private int indexPosition;
    //点赞参数
    private String type = "2";
    //热点话题
    private int page = 1, pageSize = 4;
    private HotTopicAdapter hotTopicAdapter;
    private List<TopicBean> topicBeans;
    private TopicListModel topicListModel;

    @Override
    protected int initLayout() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initView(View view) {
        rvTopic.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        MyLayoutManager myLayoutManager = new MyLayoutManager(getActivity());
        myLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(myLayoutManager);

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
                schoolModel.threadData(startId, endId, follow);

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
                schoolModel.threadData(startId, endId, follow);
            }
        });


    }

    @Override
    protected void initData(Context mContext) {
        mThreadDataBeans = new ArrayList<>();
        schoolAdapter = new SchoolAdapter(mThreadDataBeans, getActivity());
        schoolAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(schoolAdapter);
        //话题
        topicBeans = new ArrayList<>();
        hotTopicAdapter = new HotTopicAdapter(topicBeans);
        hotTopicAdapter.setMyItemOnClickListener(this);
        rvTopic.setAdapter(hotTopicAdapter);


        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);
        //帖子列表
        schoolModel.getThreadDataBeansLiveData().observe(this, new Observer<List<ThreadDataBean>>() {
            @Override
            public void onChanged(List<ThreadDataBean> threadDataBeans) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (threadDataBeans != null && threadDataBeans.size() > 0) {
                    if ("0".equals(endId)) {
                        mThreadDataBeans.addAll(0, threadDataBeans);
                    } else {
                        mThreadDataBeans.addAll(threadDataBeans);
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
        //话题
        topicListModel = new ViewModelProvider(this).get(TopicListModel.class);
        topicListModel.getTopicDataBeanLiveData().observe(this, new Observer<TopicDataBean>() {
            @Override
            public void onChanged(TopicDataBean topicDataBean) {
                topicBeans.addAll(topicDataBean.getBase_list());
                hotTopicAdapter.notifyDataSetChanged();

            }
        });
        topicListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                shortToast(o);
            }
        });
        topicListModel.topicData(page, pageSize);

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
        schoolModel.threadData(startId, endId, follow);
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
            case R.id.tv_dianzan:
            case R.id.iv_dianzan:
                showLoading("");
//                viewType = 1;
                schoolModel.gardenPraise(type, mThreadDataBeans.get(position).getId(), "", "");
                break;
            case R.id.tv_message://评论
            case R.id.relativeLayout://详情
                startActivity(new Intent(getActivity(), PostInfoActivity.class).putExtra("threadDataBean", mThreadDataBeans.get(position)));
                break;
            case R.id.ll_topic:
                startActivity(new Intent(getActivity(), TopicInfoActivity.class).putExtra("topicBean", topicBeans.get(position)));
                break;
            case R.id.civ_head://个人动态

                break;
        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
