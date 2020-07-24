package com.jianpei.jpeducation.fragment.tiku;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.tiku.TestPaperInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.tiku.TodayExerciseAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.bean.tiku.TestPaperBean;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.TodayExerciseListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimulationFragment extends BaseFragment implements MyItemOnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private CurriculumDataBean curriculumDataBean;

    private TodayExerciseListModel todayExerciseListModel;
    //
    private TodayExerciseAdapter todayExerciseAdapter;
    private List<TestPaperBean> mTestPaperBeans;
    //

    private int page = 1, pageSize = 10;
    private String catId, paperType = "2";

    public SimulationFragment(CurriculumDataBean curriculumDataBean) {
        this.curriculumDataBean = curriculumDataBean;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_simulation;
    }

    @Override
    protected void initView(View view) {
        todayExerciseListModel = new ViewModelProvider(this).get(TodayExerciseListModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    protected void initData(Context mContext) {

        catId = SpUtils.getValue(SpUtils.catId);
        mTestPaperBeans = new ArrayList<>();
        todayExerciseAdapter = new TodayExerciseAdapter(mTestPaperBeans);
        todayExerciseAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(todayExerciseAdapter);
        //试卷列表
        todayExerciseListModel.getTestPaperBeanLiveData().observe(this, new Observer<PaperDataBean>() {
            @Override
            public void onChanged(PaperDataBean paperDataBean) {
//                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
//                dismissLoading();
                if (page == 1) {
                    mTestPaperBeans.clear();
                }
                mTestPaperBeans.addAll(paperDataBean.getData());
                todayExerciseAdapter.notifyDataSetChanged();
            }
        });
        todayExerciseListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
//                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
//                dismissLoading();
                shortToast(o);
            }
        });
//        shortToast("");
        todayExerciseListModel.paperData(page, pageSize, catId, curriculumDataBean.getId(), "", paperType);

    }

    @Override
    public void onItemClick(int position, View view) {

        startActivity(new Intent(getActivity(), TestPaperInfoActivity.class).putExtra("testPaperBean",mTestPaperBeans.get(position)));
    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
