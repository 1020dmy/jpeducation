package com.jianpei.jpeducation.adapter.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.ImagesBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageView;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/14
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SchoolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ThreadDataBean> threadDataBeans;
    private Context mContext;

    private MyItemOnClickListener myItemOnClickListener;


    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public SchoolAdapter(List<ThreadDataBean> threadDataBeans, Context context) {
        this.threadDataBeans = threadDataBeans;
        this.mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (threadDataBeans.get(position).getImages() == null || threadDataBeans.get(position).getImages().size() == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_noimage, parent, false);
            viewHolder = new MyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false);
            viewHolder = new MyHolder2(view);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ThreadDataBean threadDataBean = threadDataBeans.get(position);

        if (holder instanceof MyHolder) {//无图
            MyHolder myHolder = (MyHolder) holder;
            Glide.with(mContext).load(threadDataBean.getUser_img()).placeholder(R.drawable.head_icon).into(myHolder.civ_head);
            myHolder.tv_name.setText(threadDataBean.getUser_name());
            myHolder.tv_time.setText(threadDataBean.getCreated_at_str());
            myHolder.tv_content.setText(threadDataBean.getContent());

            if ("1".equals(threadDataBean.getIs_my_thread())) {//是否我的帖子1是，0否
                myHolder.btn_status.setVisibility(View.GONE);
            }
            if ("1".equals(threadDataBean.getIs_attention())) {//是否关注1是，0否
                myHolder.btn_status.setText("取消关注");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cA5A7B0));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
            } else {
                myHolder.btn_status.setText("关注+");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cE73B30));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
            }

            if ("0".equals(threadDataBean.getPost_num()))
                myHolder.tv_message.setText("");
            else {
                myHolder.tv_message.setText("+" + threadDataBean.getPost_num());
            }
            if ("0".equals(threadDataBean.getLike_num()))
                myHolder.tv_dianzan.setText("");
            else {
                myHolder.tv_dianzan.setText(threadDataBean.getLike_num());
            }
            if ("1".equals(threadDataBean.getIs_praise())) {//是否点赞1是，0否
                myHolder.iv_dianzan.setImageResource(R.drawable.school_undianzan_icon);
            } else {
                myHolder.iv_dianzan.setImageResource(R.drawable.school_dianzan_icon);
            }


        } else if (holder instanceof MyHolder2) {//有图
            MyHolder2 myHolder = (MyHolder2) holder;
            Glide.with(mContext).load(threadDataBean.getUser_img()).placeholder(R.drawable.head_icon).into(myHolder.civ_head);
            myHolder.tv_name.setText(threadDataBean.getUser_name());
            myHolder.tv_time.setText(threadDataBean.getCreated_at_str());
            myHolder.tv_content.setText(threadDataBean.getContent());
            if ("1".equals(threadDataBean.getIs_my_thread())) {//是否我的帖子1是，0否
                myHolder.btn_status.setVisibility(View.GONE);
            }
            if ("1".equals(threadDataBean.getIs_attention())) {//是否关注1是，0否
                myHolder.btn_status.setText("取消关注");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cA5A7B0));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
            } else {
                myHolder.btn_status.setText("关注+");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cE73B30));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
            }
            if ("0".equals(threadDataBean.getPost_num()))
                myHolder.tv_message.setText("");
            else {
                myHolder.tv_message.setText("+" + threadDataBean.getPost_num());
            }
            if ("0".equals(threadDataBean.getLike_num()))
                myHolder.tv_dianzan.setText("");
            else {
                myHolder.tv_dianzan.setText(threadDataBean.getLike_num());
            }
            if ("1".equals(threadDataBean.getIs_praise())) {//是否点赞1是，0否
                myHolder.iv_dianzan.setImageResource(R.drawable.school_undianzan_icon);
            } else {
                myHolder.iv_dianzan.setImageResource(R.drawable.school_dianzan_icon);
            }

            myHolder.nineGridImageView.setImagesData(threadDataBean.getImages());

        }

//        ThreadDataBean threadDataBean = threadDataBeans.get(position);
//        Glide.with(context).load(threadDataBean.getUser_img()).into(holder.civ_head);
//        holder.tv_name.setText(threadDataBean.getUser_name());
//        holder.tv_time.setText(threadDataBean.getCreated_at_str());
//        holder.tv_content.setText(threadDataBean.getContent());
//        if ("0".equals(threadDataBean.getPost_num()))
//            holder.tv_message.setText("");
//        else {
//            holder.tv_message.setText("+" + threadDataBean.getPost_num());
//        }
//        if ("0".equals(threadDataBean.getLike_num()))
//            holder.tv_dianzan.setText("");
//        else {
//            holder.tv_dianzan.setText(threadDataBean.getLike_num());
//        }
//        if ("1".equals(threadDataBean.getIs_my_thread())) {//是否我的帖子1是，0否
//            holder.btn_status.setVisibility(View.GONE);
//        }
//        if ("1".equals(threadDataBean.getIs_praise())) {//是否点赞1是，0否
//            holder.iv_dianzan.setImageResource(R.drawable.school_undianzan_icon);
//        } else {
//            holder.iv_dianzan.setImageResource(R.drawable.school_dianzan_icon);
//        }
//        if ("1".equals(threadDataBean.getIs_attention())) {//是否关注1是，0否
//            holder.btn_status.setText("取消关注");
//            holder.btn_status.setTextColor(context.getResources().getColor(R.color.cA5A7B0));
//            holder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
//        } else {
//            holder.btn_status.setText("关注+");
//            holder.btn_status.setTextColor(context.getResources().getColor(R.color.cE73B30));
//            holder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
//        }
//        if (threadDataBean.getImages() != null && threadDataBean.getImages().size() > 0) {
//            ImageListAdapter imageListAdapter = new ImageListAdapter(threadDataBean.getImages(), context);
//            holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
//            holder.recyclerView.setAdapter(imageListAdapter);
//        }
    }

    @Override
    public int getItemCount() {
        return threadDataBeans != null ? threadDataBeans.size() : 0;
    }

//    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private CircleImageView civ_head;
//        private TextView tv_name, tv_time;
//        private Button btn_status;
//        private TextView tv_content;
//        private ImageView iv_share;
//        private TextView tv_message, tv_dianzan;
//        private RecyclerView recyclerView;
//        private ImageView iv_dianzan;
//        private LinearLayout ll_dianzan;
//
//        private LinearLayout linearLayout;
//
//        public MyHolder(@NonNull View itemView) {
//            super(itemView);
//            civ_head = itemView.findViewById(R.id.civ_head);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_time = itemView.findViewById(R.id.tv_time);
//            btn_status = itemView.findViewById(R.id.btn_status);
//            tv_content = itemView.findViewById(R.id.tv_content);
//            iv_share = itemView.findViewById(R.id.iv_share);
//            tv_message = itemView.findViewById(R.id.tv_message);
//            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
//            recyclerView = itemView.findViewById(R.id.recyclerView);
//
//            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
//            ll_dianzan = itemView.findViewById(R.id.ll_dianzan);
//            linearLayout=itemView.findViewById(R.id.linearLayout);
//
//            btn_status.setOnClickListener(this);
//            iv_share.setOnClickListener(this);
//            ll_dianzan.setOnClickListener(this);
//            tv_message.setOnClickListener(this);
//            linearLayout.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (myItemOnClickListener != null) {
//                myItemOnClickListener.onItemClick(getLayoutPosition(), v);
//            }
//        }
//    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civ_head;
        private TextView tv_name, tv_time;
        private TextView tv_content;
        private ImageView iv_share, iv_dianzan;
        private TextView tv_message, tv_dianzan;
        private Button btn_status;

        private RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);

            tv_content = itemView.findViewById(R.id.tv_content);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
            btn_status = itemView.findViewById(R.id.btn_status);

            relativeLayout=itemView.findViewById(R.id.relativeLayout);

            btn_status.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            tv_message.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }
    }

    class MyHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civ_head;
        private TextView tv_name, tv_time;
        private TextView tv_content;
        private ImageView iv_share, iv_dianzan;
        private TextView tv_message, tv_dianzan;
        private Button btn_status;
        private RelativeLayout relativeLayout;


        private NineGridImageView<ImagesBean> nineGridImageView;

        private NineGridImageViewAdapter<ImagesBean> adapter = new NineGridImageViewAdapter<ImagesBean>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, ImagesBean imagesBean) {
                Glide.with(mContext).load(imagesBean.getUrl()).placeholder(R.drawable.feedback_add_pict).into(imageView);

            }
        };

        public MyHolder2(@NonNull View itemView) {
            super(itemView);

            civ_head = itemView.findViewById(R.id.civ_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);

            tv_content = itemView.findViewById(R.id.tv_content);
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
            btn_status = itemView.findViewById(R.id.btn_status);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);


            btn_status.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            tv_message.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);



            nineGridImageView = itemView.findViewById(R.id.nineGridImageView);
            nineGridImageView.setAdapter(adapter);

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }

    }
}
