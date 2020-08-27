package com.jianpei.jpeducation.activitys.tiku.wrong;


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
import com.jianpei.jpeducation.fragment.tiku.CollectListFragment;
import com.jianpei.jpeducation.fragment.tiku.RecordListFragment;
import com.jianpei.jpeducation.fragment.tiku.WrongListFragment;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.AnswerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WrongQuestionListActivity extends BaseActivity {

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

    private int type;//1收藏列表；2错题列表,4，做题记录


    @Override
    protected int setLayoutView() {
        return R.layout.activity_wrong_question_list;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (1 == type) {
            tvTitle.setText("收藏列表");
        } else if (2 == type) {
            tvTitle.setText("错题集");
        } else if (4 == type) {
            tvTitle.setText("做题记录");
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
                if (curriculumDataBeans == null || curriculumDataBeans.size() == 0)
                    return;
                addFragments(curriculumDataBeans);
                viewPage.setOffscreenPageLimit(fragments.size());
                viewPage.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
                new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(curriculumDataBeans.get(position).getCur_name());
                    }
                }).attach();

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


    private void addFragments(List<CurriculumDataBean> curriculumDataBeans) {
        if (type == 1) {//1收藏列表
            for (CurriculumDataBean curriculumDataBean : curriculumDataBeans) {
                fragments.add(new CollectListFragment(type, curriculumDataBean.getId()));
            }
        } else if (type == 2) {//2错题列表
            for (CurriculumDataBean curriculumDataBean : curriculumDataBeans) {
                fragments.add(new WrongListFragment(type, curriculumDataBean.getId()));
            }
        } else if (type == 4) {//4，做题记录
            for (CurriculumDataBean curriculumDataBean : curriculumDataBeans) {
                fragments.add(new RecordListFragment(type, curriculumDataBean.getId()));
            }
        }
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
