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

//    private String originPrice;
//    private String material;

    private ClassInfoBean classInfoBean;

    private List<String> classIds;
    private List<String> suitesIds;

    private double totalPrice;


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
//        this.originPrice = originPrice;
//        this.material = material;
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
            llData.setVisibility(View.GONE);
        } else {
            tvData.setText(classInfoBean.getMaterial_des());
        }

//        if (mGroupClassBeans.size() != 0) {
//            mGroupClassBeans.get(0).setSelect(true);
//            tvNowPrice.setText("￥" + mGroupClassBeans.get(0).getPrice());
//            if (mGroupClassBeans.get(0).getPrice() != null)
//                totalPrice = Double.parseDouble(mGroupClassBeans.get(0).getPrice());
//            mtvTitle.setText(mGroupClassBeans.get(0).getTitle());
//        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

//        private int selectPoint = 0;

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            GroupClassBean groupClassBean = mGroupClassBeans.get(position);
//            holder.llTitle.setEnabled(!groupClassBean.isSelect());
//            holder.tvTitle.setEnabled(!groupClassBean.isSelect());

            if (groupClassBean.isSelect()) {
                addIds(groupClassBean);
                holder.llTitle.setBackgroundResource(R.drawable.shape_subject_select_bg);
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                removeIds(groupClassBean);
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
                if (groupClassBean.getPrice() == null)
                    return;


                if (groupClassBean.isSelect()) {
//                    if (groupClassBean.getType() == 1) {
//                        classIds.add(groupClassBean.getId());
//                    } else {
//                        suitesIds.add(groupClassBean.getId());
//                    }
                    totalPrice += Double.parseDouble(groupClassBean.getPrice());
                } else {
//                    if (groupClassBean.getType() == 1) {
//                        classIds.remove(groupClassBean.getId());
//                    } else {
//                        suitesIds.remove(groupClassBean.getId());
//                    }
                    totalPrice -= Double.parseDouble(groupClassBean.getPrice());
                }
                tvNowPrice.setText("￥" + totalPrice);

//                mGroupClassBeans.get(selectPoint).setSelect(false);
//                notifyItemChanged(selectPoint);
//                mGroupClassBeans.get(getLayoutPosition()).setSelect(true);
//                selectPoint = getLayoutPosition();
//                tvNowPrice.setText("￥" + mGroupClassBeans.get(selectPoint).getPrice());
//                mtvTitle.setText(mGroupClassBeans.get(selectPoint).getTitle());
//
//                if (mGroupClassBeans.get(selectPoint).getIs_material() == 1) {
//                    llData.setVisibility(View.VISIBLE);
//                } else {
//                    llData.setVisibility(View.GONE);
//                }
//                notifyItemChanged(selectPoint);


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
        super.dismiss();
    }
}
