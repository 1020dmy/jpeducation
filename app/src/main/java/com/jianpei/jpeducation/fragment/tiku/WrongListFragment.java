package com.jianpei.jpeducation.fragment.tiku;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.tiku.WrongListAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.tiku.QuestionBean;
import com.jianpei.jpeducation.bean.tiku.QuestionDataBean;
import com.jianpei.jpeducation.viewmodel.AnswerModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WrongListFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private AnswerModel answerModel;

    private String type;
    private String classId;
    private int page = 1, pageSize = 10;

    private List<QuestionBean> mQuestionBeans;
    private WrongListAdapter wrongAndCollectListAdapter;

    public WrongListFragment(String type, String classId) {
        this.type = type;
        this.classId = classId;
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
                showLoading("");
                answerModel.questionData(type, classId, page, pageSize);

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                showLoading("");
                answerModel.questionData(type, classId, page, pageSize);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        mQuestionBeans = new ArrayList<>();
        wrongAndCollectListAdapter = new WrongListAdapter(mQuestionBeans);
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

        answerModel.questionData(type, classId, page, pageSize);

    }
}
