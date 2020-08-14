package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.player.TryPlayerActivity;
import com.jianpei.jpeducation.adapter.classinfo.DirectoryAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryChapterBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.viewmodel.CIDirectoryModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoFModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_className)
    TextView tvClassName;
    @BindView(R.id.tv_changeClass)
    TextView tvChangeClass;

    private DirectoryAdapter directoryAdapter;

    private CIDirectoryModel ciDirectoryModel;
    private ClassInfoModel classInfoModel;

    DirectoryChapterBean directoryChapterBean;

    private ClassInfoFModel classInfoFModel;

    private String groupId;

    public DirectoryFragment(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_directory;
    }

    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ciDirectoryModel = new ViewModelProvider(getActivity()).get(CIDirectoryModel.class);
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);

        classInfoFModel = new ViewModelProvider(getActivity()).get(ClassInfoFModel.class);


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
        ciDirectoryModel.getViodListBeansLiveData().observe(getActivity(), new Observer<List<DirectorySectionBean>>() {
            @Override
            public void onChanged(List<DirectorySectionBean> viodListBeans) {
                dismissLoading();
                directoryAdapter.nodeAddData(directoryChapterBean, 0, viodListBeans);


            }
        });
        classInfoFModel.getClassInfoBean().observe(this, new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                tvClassName.setText(classInfoBean.getTitle());
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                for (String teacherNum : classInfoBean.getTeacher_names()) {
                    stringBuilder.append(teacherNum + " ");
                    ++i;
                    if (i == 4) {
                        break;
                    }
                }
                String teachers = stringBuilder.toString();
                stringBuilder.replace(0, stringBuilder.length(), "");
                stringBuilder.reverse();
                stringBuilder = null;
                tvTeacher.setText(teachers);
            }
        });

        //接收更新数据通知
        classInfoModel.getUpDataLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                ciDirectoryModel.classDirectory(groupId);
            }
        });


//        ///班级
//        classInfoModel.getGroupInfoBeanMutableLiveData().observe(getActivity(), new Observer<GroupInfoBean>() {
//            @Override
//            public void onChanged(GroupInfoBean groupInfoBean) {
////                showLoading("");
//                ciDirectoryModel.classDirectory(groupInfoBean.getId());
//
//
//            }
//        });
        ///团购
//        classInfoModel.getRegimentInfoBeanMutableLiveData().observe(getActivity(), new Observer<RegimentInfoBean>() {
//            @Override
//            public void onChanged(RegimentInfoBean regimentInfoBean) {
//                ciDirectoryModel.classDirectory(regimentInfoBean.getPoint_id());
//            }
//        });


        ciDirectoryModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o)) {
                    shortToast(o);

                }
            }
        });
        directoryAdapter.addChildClickViewIds(R.id.tv_try);
        directoryAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_try:
                        DirectorySectionBean directorySectionBean = (DirectorySectionBean) directoryAdapter.getData().get(position);
                        if ("1".equals(directorySectionBean.getIsfree())) {
                            startActivity(new Intent(getActivity(), TryPlayerActivity.class).putExtra("directorySectionBean", directorySectionBean));
                        } else {
                            shortToast("当前未完成购买无法播放");
                        }
                        break;
                }

            }
        });
        directoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_chapter:
                        directoryChapterBean = (DirectoryChapterBean) adapter.getData().get(position);
                        if (!directoryChapterBean.isExpanded() && directoryChapterBean.getBaseNodes().size() == 0) {
                            showLoading("");
                            DirectoryProfessionBean DirectoryProfessionBean = (DirectoryProfessionBean) adapter.getData().get(directoryAdapter.findParentNode(position));
                            ciDirectoryModel.viodList(DirectoryProfessionBean.getId(), directoryChapterBean.getId());
                        }
                        directoryAdapter.expandOrCollapse(position);
                        break;
                }
            }
        });

        showLoading("");
        ciDirectoryModel.classDirectory(groupId);


    }

    @OnClick(R.id.tv_changeClass)
    public void onViewClicked() {
    }
}
