package com.jianpei.jpeducation.activitys.tiku.simulation;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.tiku.CurriculumDataBean;
import com.jianpei.jpeducation.fragment.tiku.SimulationFragment;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SimulationExerciseListActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;


    private AnswerModel answerModel;

    private String catId;

    private List<Fragment> fragments;

    private String paper_type;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_simulation_exercise_list;
    }

    @Override
    protected void initView() {
        paper_type = getIntent().getStringExtra("paper_type");
        if ("1".equals(paper_type)) {
            tvTitle.setText("历年真题");
        } else if ("2".equals(paper_type)) {
            tvTitle.setText("模拟练习");

        }
        catId = SpUtils.getValue(SpUtils.catId);
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);

        viewPage.setUserInputEnabled(false);


    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        answerModel.getCurriculumDataBeans().observe(this, new Observer<List<CurriculumDataBean>>() {
            @Override
            public void onChanged(List<CurriculumDataBean> curriculumDataBeans) {
                dismissLoading();
                if (curriculumDataBeans != null && curriculumDataBeans.size() > 0) {
                    for (CurriculumDataBean curriculumDataBean : curriculumDataBeans) {
                        fragments.add(new SimulationFragment(curriculumDataBean.getId(), paper_type));
                    }
                    viewPage.setOffscreenPageLimit(fragments.size());
                    viewPage.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
                    new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            tab.setText(curriculumDataBeans.get(position).getCur_name());
                        }
                    }).attach();
                }

            }
        });


        answerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        answerModel.curriculumData(catId, "0");

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (fragments != null)
            fragments.clear();
        fragments = null;
        super.onDestroy();
    }
}
