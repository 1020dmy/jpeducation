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
import com.jianpei.jpeducation.activitys.tiku.daily.DailyAnswerActivity;
import com.jianpei.jpeducation.activitys.tiku.daily.DailyWrongAndParsingActivity;
import com.jianpei.jpeducation.activitys.tiku.result.AnswerResultActivity;
import com.jianpei.jpeducation.activitys.tiku.result.SimulationExerciseResultActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.SimulationExerciseActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.SimulationParsingActivity;
import com.jianpei.jpeducation.activitys.tiku.simulation.TestPaperInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.RecordListAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.base.LazyLoadFragment;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.TodayExerciseListModel;
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
public class RecordListFragment extends LazyLoadFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private String classId;//课id
    private String paper_type;//1历年真题，2模拟试题，3每日一练，4我的做题记录
    private String catId;//专业id
    private int page = 1, pageSize = 10;


    private TodayExerciseListModel todayExerciseListModel;
    private RecordListAdapter recordListAdapter;

    private List<TestPaperBean> testPaperBeans;


    public RecordListFragment(String classId, String paper_type) {
        this.classId = classId;
        this.paper_type = paper_type;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_record_list;
    }

    @Override
    protected void initView(View view) {

        todayExerciseListModel = new ViewModelProvider(this).get(TodayExerciseListModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                todayExerciseListModel.paperData(page, pageSize, catId, classId, "", paper_type);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                todayExerciseListModel.paperData(page, pageSize, catId, classId, "", paper_type);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        catId = SpUtils.getValue(SpUtils.catId);
        testPaperBeans = new ArrayList<>();
        recordListAdapter = new RecordListAdapter(testPaperBeans);
        recordListAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(recordListAdapter);


        //试卷列表
        todayExerciseListModel.getTestPaperBeanLiveData().observe(this, new Observer<PaperDataBean>() {
            @Override
            public void onChanged(PaperDataBean paperDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (page == 1) {
                    testPaperBeans.clear();
                }
                testPaperBeans.addAll(paperDataBean.getData());
                recordListAdapter.notifyDataSetChanged();
            }
        });
        todayExerciseListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                shortToast(o);
            }
        });

    }

    @Override
    protected void loadData() {
        refreshLayout.autoRefresh();
        todayExerciseListModel.paperData(page, pageSize, catId, classId, "", paper_type);
    }

    @Override
    public void onItemClick(int position, View view) {
        TestPaperBean testPaperBean = testPaperBeans.get(position);
        switch (view.getId()) {
            case R.id.tv_again://答题
                isFirstLoad = true;
                if ("3".equals(testPaperBean.getPaper_type())) {//每日一练
                    startActivity(new Intent(getActivity(), DailyAnswerActivity.class)
                            .putExtra("paperId", testPaperBean.getId())
                            .putExtra("recordId", testPaperBean.getUser_record_id())
                            .putExtra("restartType", testPaperBean.getUser_is_complete())
                            .putExtra("paperName", testPaperBean.getPaper_name()));
                } else if ("1".equals(testPaperBean.getPaper_type()) || "2".equals(testPaperBean.getPaper_type())) {//模拟，真题
                    startActivity(new Intent(getActivity(), TestPaperInfoActivity.class)
                            .putExtra("paperId", testPaperBean.getId())
                            .putExtra("recordId", testPaperBean.getUser_record_id()));
                }
                break;
            case R.id.tv_result://报告
                if ("3".equals(testPaperBean.getPaper_type())) {//每日一练
                    startActivity(new Intent(getActivity(), AnswerResultActivity.class)
                            .putExtra("recordId", testPaperBean.getUser_record_id())
                            .putExtra("paperId", testPaperBean.getId()));
                } else if ("1".equals(testPaperBean.getPaper_type()) || "2".equals(testPaperBean.getPaper_type())) {//模拟，真题
                    startActivity(new Intent(getActivity(), SimulationExerciseResultActivity.class)
                            .putExtra("recordId", testPaperBean.getUser_record_id())
                            .putExtra("paperId", testPaperBean.getId())
                            .putExtra("paperName", testPaperBean.getPaper_name()));
                }
                break;
            case R.id.tv_jiexi://解析
                if ("3".equals(testPaperBean.getPaper_type())) {//每日一练
                    startActivity(new Intent(getActivity(), DailyWrongAndParsingActivity.class)
                            .putExtra("source", "5")
                            .putExtra("recordId", testPaperBean.getUser_record_id())
                            .putExtra("paperId", testPaperBean.getId())
                            .putExtra("paperName", testPaperBean.getPaper_name()));
                } else if ("1".equals(testPaperBean.getPaper_type()) || "2".equals(testPaperBean.getPaper_type())) {//模拟，真题
                    startActivity(new Intent(getActivity(), SimulationParsingActivity.class)
                            .putExtra("paperId", testPaperBean.getId())
                            .putExtra("recordId", testPaperBean.getUser_record_id())
                            .putExtra("source", "5")
                            .putExtra("paperName", testPaperBean.getPaper_name()));
                }
                break;
        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data,
                            int position) {

    }
}
