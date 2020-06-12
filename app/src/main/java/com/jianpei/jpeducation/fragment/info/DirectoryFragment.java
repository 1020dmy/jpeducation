package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.DirectoryAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.viewmodel.CIDirectoryModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DirectoryAdapter directoryAdapter;

    private CIDirectoryModel ciDirectoryModel;
    private ClassInfoModel classInfoModel;


    @Override
    protected int initLayout() {
        return R.layout.fragment_directory;
    }

    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ciDirectoryModel = new ViewModelProvider(getActivity()).get(CIDirectoryModel.class);
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);

    }

    @Override
    protected void initData(Context mContext) {

        directoryAdapter = new DirectoryAdapter();
        recyclerView.setAdapter(directoryAdapter);
        ciDirectoryModel.getMutableLiveData().observe(getActivity(), new Observer<List<DirectoryProfessionBean>>() {
            @Override
            public void onChanged(List<DirectoryProfessionBean> directoryProfessionBeans) {
                dismissLoading();
                directoryAdapter.setList(directoryProfessionBeans);

            }
        });
        classInfoModel.getGroupInfoBeanMutableLiveData().observe(getActivity(), new Observer<GroupInfoBean>() {
            @Override
            public void onChanged(GroupInfoBean groupInfoBean) {
//                showLoading("");
//                ciDirectoryModel.classDirectory(groupInfoBean.getId());

            }
        });

        ciDirectoryModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

    }
}
