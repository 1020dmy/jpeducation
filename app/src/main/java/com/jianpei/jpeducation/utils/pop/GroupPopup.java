package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.classinfo.GroupAdapter;
import com.jianpei.jpeducation.bean.classinfo.RegimentBean;

import java.util.List;


public class GroupPopup extends PopupWindow {

    private Context mContext;
    private View contentView;
    private ImageButton imbCancel;
    private RecyclerView recyclerView;

    private List<RegimentBean> regimentBeans;

    public GroupPopup(Context mContext, List<RegimentBean> regimentBeans) {
        this.mContext = mContext;
        this.regimentBeans=regimentBeans;
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_group,
                null, false);
        setContentView(contentView);
        imbCancel = contentView.findViewById(R.id.imb_cancel);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        imbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setData();

    }

    private void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        GroupAdapter groupAdapter = new GroupAdapter(regimentBeans, mContext);
        recyclerView.setAdapter(groupAdapter);
    }

    public void showPop() {
        showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

}
