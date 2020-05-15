package com.jianpei.jpeducation.activitys;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.SelectAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.selectclass.ClassName;
import com.jianpei.jpeducation.bean.selectclass.ClassType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectDisciplineActivity extends BaseActivity {
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SelectAdapter selectAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_select_discipline;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));


    }

    @Override
    protected void initData() {

        selectAdapter = new SelectAdapter();

        recyclerView.setAdapter(selectAdapter);

        selectAdapter.setList(getEntity());

        selectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                shortToast("点击了" + position);

            }
        });


        selectAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                shortToast("点击了" + position);
            }
        });
    }


    public List<ClassType> getEntity() {
        List<ClassType> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            List<BaseNode> classNames = new ArrayList<>();

            for (int j = 0; j < 6; j++) {

                classNames.add(new ClassName("一级建筑工程师"));
            }


            list.add(new ClassType(classNames, "建筑工程"));

        }
        return list;
    }

    @OnClick(R.id.imageButton)
    public void onViewClicked() {
        finish();
        outActivity();
    }
}
