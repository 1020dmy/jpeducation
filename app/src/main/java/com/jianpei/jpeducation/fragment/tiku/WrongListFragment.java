package com.jianpei.jpeducation.fragment.tiku;

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
import com.jianpei.jpeducation.activitys.tiku.wrongandcollect.CheckWrongEnterActivity;
import com.jianpei.jpeducation.activitys.tiku.wrongandcollect.CheckWrongParsingActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.WrongListAdapter;
import com.jianpei.jpeducation.base.LazyLoadFragment;
import com.jianpei.jpeducation.bean.tiku.QuestionBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;
import com.jianpei.jpeducation.viewmodel.AnswerModel;
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
public class WrongListFragment extends LazyLoadFragment implements MyItemOnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private AnswerModel answerModel;

    private int type;//1收藏列表；2错题列表,4，做题记录
    private String classId;//课程id
    private String cur_name;//课程名称
    private int page = 1, pageSize = 10;
    private String source = "3";//1正常答题，2收藏，4本卷错题，3错题集,5全部解析，6本卷解答题

    private List<QuestionBean> mQuestionBeans;
    private WrongListAdapter wrongAndCollectListAdapter;

    public WrongListFragment(int type, String classId, String cur_name) {
        this.type = type;
        this.classId = classId;
        this.cur_name = cur_name;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_wrong_and_collect_list;
    }

    @Override
    protected void initView(View view) {
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                answerModel.questionData(type, classId, page, pageSize);

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                answerModel.questionData(type, classId, page, pageSize);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        mQuestionBeans = new ArrayList<>();
        wrongAndCollectListAdapter = new WrongListAdapter(mQuestionBeans);
        wrongAndCollectListAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(wrongAndCollectListAdapter);

        answerModel.getQuestionDataBeanLiveData().observe(this, new Observer<QuestionDataBean>() {
            @Override
            public void onChanged(QuestionDataBean questionDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (page == 1) {
                    mQuestionBeans.clear();
                }
                mQuestionBeans.addAll(questionDataBean.getData());
                wrongAndCollectListAdapter.notifyDataSetChanged();

            }
        });

        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);

            }
        });


    }

    /**
     * 加载数据
     */
    @Override
    protected void loadData() {
        answerModel.questionData(type, classId, page, pageSize);

    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.tv_enter://进入
                startActivity(new Intent(getActivity(), CheckWrongEnterActivity.class)
                        .putExtra("source", source)
                        .putExtra("questionBean", mQuestionBeans.get(position))
                        .putExtra("cur_name", cur_name));
                break;
            case R.id.tv_jiexi://解析
                startActivity(new Intent(getActivity(), CheckWrongParsingActivity.class)
                        .putExtra("source", source)
                        .putExtra("questionBean", mQuestionBeans.get(position))
                        .putExtra("cur_name", cur_name));
                break;
        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
