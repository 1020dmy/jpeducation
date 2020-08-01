package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.CommentAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.CommentBean;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.utils.keyboard.OnSoftKeyBoardChangeListener;
import com.jianpei.jpeducation.utils.keyboard.SoftKeyBoardListener;
import com.jianpei.jpeducation.utils.pop.CommentPopup;
import com.jianpei.jpeducation.viewmodel.CommentModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerCommentFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.button)
    Button button;
    private String classId;

    private CommentModel commentModel;

    private CommentAdapter commentAdapter;
    private List<CommentBean> mCommentBeans;

    private int page = 1, pageSize = 10;

    private CommentPopup commentPopup;


    public PlayerCommentFragment(String classId) {
        this.classId = classId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_player_comment;
    }

    @Override
    protected void initView(View view) {
        commentModel = new ViewModelProvider(this).get(CommentModel.class);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                showLoading("");
                commentModel.commentList("2", "", classId, page, pageSize);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                showLoading("");
                commentModel.commentList("2", "", classId, page, pageSize);

            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        mCommentBeans = new ArrayList<>();
        commentAdapter = new CommentAdapter(mCommentBeans, getActivity());
        recyclerView.setAdapter(commentAdapter);

        commentModel.getCommentListBeanLiveData().observe(getActivity(), new Observer<CommentListBean>() {
            @Override
            public void onChanged(CommentListBean commentListBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    mCommentBeans.clear();
                }
                mCommentBeans.addAll(commentListBean.getData());
                commentAdapter.notifyDataSetChanged();
            }
        });
        commentModel.getCommentSuccessLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
            }
        });
        commentModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        commentModel.commentList("2", "", classId, page, pageSize);

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        if (commentPopup == null) {
            commentPopup = new CommentPopup(getActivity());
            commentPopup.setMyOnClickListener(new CommentPopup.MyOnClickListener() {
                @Override
                public void OnClick(String message) {
                    commentPopup.dismiss();
                    showLoading("");
                    commentModel.insertComment(classId, message);
                }
            });
        }
        commentPopup.showPop();

    }


}
