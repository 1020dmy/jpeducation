package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.listener.MyItemOnClickListener;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SubjectPopup extends PopupWindow {

    private ImageView imageView;
    private TextView mtvTitle, tvNowPrice, tvPrice;

    private ImageButton imbCancel;

    private RecyclerView recyclerView;

    private LinearLayout llData;
    private TextView tvData;

    private Button button;
    private View contentView;

    private List<GroupClassBean> mGroupClassBeans;
    private Context mContext;


    private ClassInfoBean classInfoBean;

    private List<String> classIds;
    private List<String> suitesIds;

//    private double totalPrice;

    private MyItemClickListener myItemOnClickListener;

    public void setMyItemOnClickListener(MyItemClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public List<String> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<String> classIds) {
        this.classIds = classIds;
    }

    public List<String> getSuitesIds() {
        return suitesIds;
    }

    public void setSuitesIds(List<String> suitesIds) {
        this.suitesIds = suitesIds;
    }

    public void setOnClickListener(View.OnClickListener clickListener) {
        button.setOnClickListener(clickListener);
    }


    public SubjectPopup(Context context, List<GroupClassBean> groupClassBeans, ClassInfoBean classInfoBean) {
        mContext = context;
        mGroupClassBeans = groupClassBeans;
        this.classInfoBean = classInfoBean;

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.pop_infoclass_subject,
                null, false);
        setContentView(contentView);
        imageView = contentView.findViewById(R.id.imageView);
        mtvTitle = contentView.findViewById(R.id.tv_title);
        tvNowPrice = contentView.findViewById(R.id.tv_now_price);
        imbCancel = contentView.findViewById(R.id.imb_cancel);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        llData = contentView.findViewById(R.id.ll_data);
        tvData = contentView.findViewById(R.id.tv_data);
        button = contentView.findViewById(R.id.button);
        tvPrice = contentView.findViewById(R.id.tv_price);
        imbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setData();
    }

    private void setData() {
        tvPrice.setText(classInfoBean.getOriginal_price_info());
        tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(mContext).load(classInfoBean.getImg()).placeholder(R.drawable.home_icon_demo).into(imageView);
        mtvTitle.setText(classInfoBean.getTitle());

        if (classInfoBean.getMaterial_des() == null) {
            llData.setVisibility(View.INVISIBLE);
        } else {
            tvData.setText(classInfoBean.getMaterial_des());
        }

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {


        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            GroupClassBean groupClassBean = mGroupClassBeans.get(position);
            if (groupClassBean.isSelect()) {
//                addIds(groupClassBean);
                holder.llTitle.setBackgroundResource(R.drawable.shape_subject_select_bg);
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
//                removeIds(groupClassBean);
                holder.llTitle.setBackgroundResource(R.drawable.shape_subject_unselect_bg);
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.cA5A8B0));
            }
            holder.tvTitle.setText(groupClassBean.getTitle());


        }

        @Override
        public int getItemCount() {
            return mGroupClassBeans != null ? mGroupClassBeans.size() : 0;
        }

        class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvTitle;
            private LinearLayout llTitle;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                llTitle = itemView.findViewById(R.id.ll_title);

                llTitle.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                GroupClassBean groupClassBean = mGroupClassBeans.get(getLayoutPosition());
                groupClassBean.setSelect(!groupClassBean.isSelect());
                notifyItemChanged(getLayoutPosition());
                if (groupClassBean.isSelect()) {
                    addIds(groupClassBean);
                } else {
                    removeIds(groupClassBean);
                }

                if (myItemOnClickListener != null) {
                    myItemOnClickListener.onClicker();
                }
            }
        }
    }


    public void addIds(GroupClassBean groupClassBean) {
        if (classIds == null && suitesIds == null)
            return;
        if (groupClassBean.getType() == 1) {
            classIds.add(groupClassBean.getId());
        } else {
            suitesIds.add(groupClassBean.getId());
        }
//        dataListenter();
    }

    public void removeIds(GroupClassBean groupClassBean) {
        if (classIds == null && suitesIds == null)
            return;
        if (groupClassBean.getType() == 1) {
            classIds.remove(groupClassBean.getId());
        } else {
            suitesIds.remove(groupClassBean.getId());
        }
//        dataListenter();

    }

    public void upDataPrice(String total_price, String price, String is_material) {

        tvNowPrice.setText("￥" + price);
        tvPrice.setText("原价：" + total_price);
        if ("0".equals(is_material)) {
            llData.setVisibility(View.INVISIBLE);
        } else {
            llData.setVisibility(View.VISIBLE);
        }
    }


    public void dataListenter() {
        for (String classId : classIds) {
            L.e("classId:" + classId);
        }
        for (String suitesId : suitesIds) {
            L.e("suitesId:" + suitesId);
        }

        L.e("=========================");

    }

    @Override
    public void dismiss() {
        myItemOnClickListener = null;
        super.dismiss();
    }


    public interface MyItemClickListener {

        void onClicker();
    }
}
