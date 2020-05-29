package com.jianpei.jpeducation.activitys;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.SelectAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.utils.MyItemOnClickListener;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.SelectDisciplineModel;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectDisciplineActivity extends BaseActivity implements MyItemOnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.tv_status)
//    TextView tvStatus;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private SelectAdapter selectAdapter;

    protected SelectDisciplineModel selectDisciplineModel;
    private ArrayList<DisciplinesBean> disciplinesBeans;
    private String catId, catName;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_select_discipline;
    }

    @Override
    protected void initView() {
//        setTitleViewPadding(tvStatus);
        selectDisciplineModel = ViewModelProviders.of(this).get(SelectDisciplineModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {
        catId = SpUtils.getValue(SpUtils.catId);
        catName = SpUtils.getValue(SpUtils.catName);
        if (TextUtils.isEmpty(catName)) {
            tvTitle.setText("请选择专业");
        } else {
            tvTitle.setText(catName);
        }
        disciplinesBeans = new ArrayList<>();

        selectDisciplineModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);

            }
        });
        selectDisciplineModel.getScuucessData().observe(this, new Observer<ArrayList<DisciplinesBean>>() {
            @Override
            public void onChanged(ArrayList<DisciplinesBean> s) {
                dismissLoading();
                disciplinesBeans.addAll(s);
                selectAdapter.notifyDataSetChanged();

            }
        });

        selectDisciplineModel.getCourseData();
        selectAdapter = new SelectAdapter(this, this, disciplinesBeans, catId);
        recyclerView.setAdapter(selectAdapter);
    }

    @Override
    public void onClick(int position, String id, String catname) {
        if (!id.equals(catId)) {
            SpUtils.putString(SpUtils.catId, id);
            SpUtils.putString(SpUtils.catName, catname);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
