package com.jianpei.jpeducation.utils.pop;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        setHeight(ScreenUtils.getHeight(context) / 5 * 3);
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


    class MyAdapter extends RecyclerView.Adapter {


        @Override
        public int getItemViewType(int position) {
            if ("1".equals(mGroupCouponBeans.get(position).getType())) {
                return 1;
            } else if ("2".equals(mGroupCouponBeans.get(position).getType())) {
                return 2;

            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false);
//            return new MyHolder(view);
            RecyclerView.ViewHolder viewHolder = null;
            if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_used_mj, parent, false);
                viewHolder = new MJHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon_used_zk, parent, false);
                viewHolder = new ZKHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            GroupCouponBean groupCouponBean = mGroupCouponBeans.get(position);
            if (holder instanceof MJHolder) {
                MJHolder mjHolder = (MJHolder) holder;
                mjHolder.tvPrice.setText(groupCouponBean.getDescribe());
                mjHolder.tvTitle.setText(groupCouponBean.getTitle());
                mjHolder.tvTime.setText("有效期至：" + groupCouponBean.getEnd_time_str());


            } else {
                ZKHolder zkHolder = (ZKHolder) holder;

                if (!TextUtils.isEmpty(groupCouponBean.getSon_describe())) {
                    String aaa = groupCouponBean.getDescribe() + "." + groupCouponBean.getSon_describe();
                    SpannableString spanString = new SpannableString(aaa);
                    AbsoluteSizeSpan span = new AbsoluteSizeSpan(20, true);//这里设置要改变的字的大小
                    spanString.setSpan(span, groupCouponBean.getDescribe().length(), aaa.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    zkHolder.tvNum.setText(spanString);

                } else {
                    zkHolder.tvNum.setText(groupCouponBean.getDescribe());

                }

                zkHolder.tvTitle.setText(groupCouponBean.getTitle());
                zkHolder.tvTime.setText("有效期至：" + groupCouponBean.getEnd_time_str());
            }
        }


        @Override
        public int getItemCount() {
            return mGroupCouponBeans != null ? mGroupCouponBeans.size() : 0;
        }

        class MJHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvPrice, tvTitle, tvTime;
            private LinearLayout llStatus;
            private TextView tvSubmit;

            public MJHolder(@NonNull View itemView) {
                super(itemView);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvTime = itemView.findViewById(R.id.tv_time);

                llStatus = itemView.findViewById(R.id.ll_status);
                tvSubmit = itemView.findViewById(R.id.tv_submit);
                tvSubmit.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.GONE);
                tvSubmit.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {

            }
        }

        class ZKHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvNum, tvTitle, tvTime;
            private LinearLayout llStatus;
            private TextView tvSubmit;


            public ZKHolder(@NonNull View itemView) {
                super(itemView);
                tvNum = itemView.findViewById(R.id.tv_num);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvTime = itemView.findViewById(R.id.tv_time);
                llStatus = itemView.findViewById(R.id.ll_status);
                tvSubmit = itemView.findViewById(R.id.tv_submit);
                tvSubmit.setVisibility(View.VISIBLE);
                tvSubmit.setOnClickListener(this);
                llStatus.setVisibility(View.GONE);



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
