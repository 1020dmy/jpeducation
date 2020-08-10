package com.jianpei.jpeducation.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
 * Created by sjl on 2020/7/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineDynamicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ThreadDataBean> threadDataBeans;
    private Context mContext;


    private MyItemOnClickListener myItemOnClickListener;


    public void setMyItemOnClickListener(MyItemOnClickListener myItemOnClickListener) {
        this.myItemOnClickListener = myItemOnClickListener;
    }

    public MineDynamicAdapter(List<ThreadDataBean> threadDataBeans, Context context) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_dynamic_noimage, parent, false);
            viewHolder = new MyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_dynamic, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return threadDataBeans != null ? threadDataBeans.size() : 0;
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civ_head;
        private TextView tv_name, tv_time;
        private TextView tv_content;
        private ImageView iv_share, iv_dianzan;
        private TextView tv_message, tv_dianzan;
        private ImageButton ib_delete;
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
            ib_delete = itemView.findViewById(R.id.ib_delete);
            //
            relativeLayout=itemView.findViewById(R.id.relativeLayout);


            ib_delete.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);


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
        private ImageButton ib_delete;
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
            ib_delete = itemView.findViewById(R.id.ib_delete);
            //
            relativeLayout=itemView.findViewById(R.id.relativeLayout);


            ib_delete.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);

            nineGridImageView = itemView.findViewById(R.id.nineGridImageView);
            nineGridImageView.setAdapter(adapter);
//            nineGridImageView.setItemImageClickListener(new ItemImageClickListener<ImagesBean>() {
//                @Override
//                public void onItemImageClick(Context context, ImageView imageView, int index, List<ImagesBean> list) {
//                    computeBoundsBackward(list);
//                    GPreviewBuilder.from((Activity) mContext)
//                            .setData(list)
//                            .setIsScale(true)
//                            .setCurrentIndex(index)
//                            .setType(GPreviewBuilder.IndicatorType.Dot)
//                            .start();//启动
//                }
//            });

        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }

        //        /**
//         * 查找信息
//         *
//         * @param list 图片集合
//         */
//        private void computeBoundsBackward(List<ImagesBean> list) {
//
//            for (int i = 0; i < nineGridImageView.getChildCount(); i++) {
//                View itemView = nineGridImageView.getChildAt(i);
//                Rect bounds = new Rect();
//                if (itemView != null) {
//                    ImageView thumbView = (ImageView) itemView;
//                    thumbView.getGlobalVisibleRect(bounds);
//                }
//                list.get(i).setmBounds(bounds);
//                list.get(i).setUrl(list.get(i).getUrl());
//            }
//
//        }
    }


}
