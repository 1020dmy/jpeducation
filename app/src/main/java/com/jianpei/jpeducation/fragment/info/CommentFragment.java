package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.CommentAdapter;
import com.jianpei.jpeducation.base.BaseFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    protected void initData(Context mContext) {
        commentAdapter = new CommentAdapter();
        recyclerView.setAdapter(commentAdapter);

    }
}
