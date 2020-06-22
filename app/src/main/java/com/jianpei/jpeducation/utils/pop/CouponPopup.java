package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;

import java.util.List;

public class CouponPopup extends PopupWindow {

    private Context mContext;
    private View contentView;

    private ImageButton rmbCancel;
    private RecyclerView recyclerView;
    private List<GroupCouponBean> mGroupCouponBeans;
    private MyCouponReceiveListener myCouponReceiveListener;


    public void setMyCouponReceiveListener(MyCouponReceiveListener myCouponReceiveListener) {
        this.myCouponReceiveListener = myCouponReceiveListener;
    }

    public CouponPopup(Context context, List<GroupCouponBean> groupCouponBeans) {
        this.mContext = context;
        this.mGroupCouponBeans = groupCouponBeans;
        setHeight(ScreenUtils.getHeight(context) / 5*3);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        contentView = LayoutInflater.from(context).inflate(R.layout.pop_infoclass_coupon,
                null, false);
        setContentView(contentView);

        rmbCancel = contentView.findViewById(R.id.rmb_cancel);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        rmbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setData();

    }

    public void showPop() {
        showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(14));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {


        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            GroupCouponBean groupCouponBean = mGroupCouponBeans.get(position);
            if ("2".equals(groupCouponBean.getType())) {
                holder.tvMark.setVisibility(View.GONE);
            }
            holder.tvPrice.setText(groupCouponBean.getDescribe());
            holder.tvTip.setText(groupCouponBean.getTitle());
            holder.tvTime.setText("有效期至：" + groupCouponBean.getEnd_time_str());

        }

        @Override
        public int getItemCount() {
            return mGroupCouponBeans != null ? mGroupCouponBeans.size() : 0;
        }

        class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvPrice, tvTip, tvTime, tvSubmit;

            private TextView tvMark;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvTip = itemView.findViewById(R.id.tv_tip);
                tvTime = itemView.findViewById(R.id.tv_time);
                tvSubmit = itemView.findViewById(R.id.tv_submit);
                tvMark = itemView.findViewById(R.id.tv_mark);

                tvSubmit.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if (myCouponReceiveListener != null) {
                    myCouponReceiveListener.onClickCouponReceive(mGroupCouponBeans.get(getLayoutPosition()).getId());
                }

            }
        }
    }

    public interface MyCouponReceiveListener {
        void onClickCouponReceive(String couponId);
    }


}
