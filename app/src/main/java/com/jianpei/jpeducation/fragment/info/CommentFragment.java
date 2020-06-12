package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.CommentAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.CommentBean;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.viewmodel.CICommentModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    private List<CommentBean> mCommentBeans;

    private CICommentModel ciCommentModel;
    private ClassInfoModel classInfoModel;


    @Override
    protected int initLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ciCommentModel = new ViewModelProvider(getActivity()).get(CICommentModel.class);
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);


    }

    @Override
    protected void initData(Context mContext) {
        mCommentBeans = new ArrayList<>();
        commentAdapter = new CommentAdapter(mCommentBeans, getActivity());
        recyclerView.setAdapter(commentAdapter);
        ciCommentModel.getCommentListBeanMutableLiveData().observe(getActivity(), new Observer<CommentListBean>() {
            @Override
            public void onChanged(CommentListBean commentListBean) {
                dismissLoading();
                mCommentBeans.addAll(commentListBean.getData());
                commentAdapter.notifyDataSetChanged();
            }
        });
        ciCommentModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        classInfoModel.getGroupInfoBeanMutableLiveData().observe(getActivity(), new Observer<GroupInfoBean>() {
            @Override
            public void onChanged(GroupInfoBean groupInfoBean) {
                showLoading("");
                ciCommentModel.commentList(groupInfoBean.getId(), 1, 10);
            }
        });


    }
}
