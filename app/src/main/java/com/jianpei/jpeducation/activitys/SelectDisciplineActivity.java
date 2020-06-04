package com.jianpei.jpeducation.activitys;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.selectdiscipline.SelectAdapter;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.bean.DisciplinesBean;
import com.jianpei.jpeducation.utils.listener.MyItemOnClickListener;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.SelectDisciplineModel;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectDisciplineActivity extends BaseModelActivity<SelectDisciplineModel, ArrayList<DisciplinesBean>> implements MyItemOnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //    @BindView(R.id.tv_status)
//    TextView tvStatus;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private SelectAdapter selectAdapter;

    private ArrayList<DisciplinesBean> disciplinesBeans;
    private String catId, catName;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_select_discipline;
    }

    @Override
    protected void initView() {
//        setTitleViewPadding(tvStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initViewModel();


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
        selectAdapter = new SelectAdapter(this, this, disciplinesBeans, catId);
        recyclerView.setAdapter(selectAdapter);
        showLoading("");
        mViewModel.getCourseData();//获取列表数据
    }

    @Override
    protected void onError(String message) {
        shortToast(message);
    }

    @Override
    protected void onSuccess(ArrayList<DisciplinesBean> data) {
        disciplinesBeans.addAll(data);
        selectAdapter.notifyDataSetChanged();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
