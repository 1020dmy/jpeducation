package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.text.TextUtils;
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
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;
import com.jianpei.jpeducation.viewmodel.CommentModel;

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

    private CommentModel commentModel;//评价

    private ClassInfoModel classInfoModel;

    private int page = 1, pageSize = 10;

    private String groupId;

    public CommentFragment(String groupId) {
        this.groupId = groupId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        commentModel = new ViewModelProvider(getActivity()).get(CommentModel.class);
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);


    }

    @Override
    protected void initData(Context mContext) {
        mCommentBeans = new ArrayList<>();
        commentAdapter = new CommentAdapter(mCommentBeans, getActivity());
        recyclerView.setAdapter(commentAdapter);
        commentModel.getCommentListBeanLiveData().observe(getActivity(), new Observer<CommentListBean>() {
            @Override
            public void onChanged(CommentListBean commentListBean) {
                dismissLoading();
                mCommentBeans.addAll(commentListBean.getData());
                commentAdapter.notifyDataSetChanged();
            }
        });
        commentModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o)) {
                    shortToast(o);

                }
            }
        });

        classInfoModel.getUpDataLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showLoading("");
                mCommentBeans.clear();
                commentModel.commentList("1", groupId, "", page, pageSize);
            }
        });
//        classInfoModel.getGroupInfoBeanMutableLiveData().observe(this, new Observer<GroupInfoBean>() {
//            @Override
//            public void onChanged(GroupInfoBean groupInfoBean) {
//                showLoading("");
//                commentModel.commentList("1", groupInfoBean.getId(), "", page, pageSize);
//            }
//        });
//        classInfoModel.getRegimentInfoBeanMutableLiveData().observe(this, new Observer<RegimentInfoBean>() {
//            @Override
//            public void onChanged(RegimentInfoBean regimentInfoBean) {
//                showLoading("");
//                commentModel.commentList("1", regimentInfoBean.getPoint_id(), "", page, pageSize);
//            }
//        });

        showLoading("");
        commentModel.commentList("1", groupId, "", page, pageSize);


    }
}
