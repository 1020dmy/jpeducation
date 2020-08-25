package com.jianpei.jpeducation.adapter.school;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.MineDynamicActivity;
import com.jianpei.jpeducation.activitys.school.TopicInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.bean.school.AttentionBean;
import com.jianpei.jpeducation.bean.school.ImagesBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.view.ninegridelayout.ItemImageClickListener;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageView;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageViewAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.shuyu.textutillib.RichTextView;
import com.shuyu.textutillib.listener.SpanAtUserCallBack;
import com.shuyu.textutillib.listener.SpanTopicCallBack;
import com.shuyu.textutillib.model.TopicModel;
import com.shuyu.textutillib.model.UserModel;

import java.util.ArrayList;
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
//            myHolder.tv_content.setText(threadDataBean.getContent());
            setTopAndUser(myHolder.tv_content, threadDataBean.getUsers(), threadDataBean.getTopics(), threadDataBean.getContent());


            if (1 == threadDataBean.getIs_attention()) {//是否关注1是，0否
                myHolder.btn_status.setVisibility(View.VISIBLE);
                myHolder.btn_status.setText("取消关注");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cA5A7B0));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
            } else if (0 == threadDataBean.getIs_attention()) {
                myHolder.btn_status.setVisibility(View.VISIBLE);
                myHolder.btn_status.setText("关注+");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cE73B30));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
            } else if (2 == threadDataBean.getIs_attention()) {
                myHolder.btn_status.setVisibility(View.GONE);
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

            setTopAndUser(myHolder.tv_content, threadDataBean.getUsers(), threadDataBean.getTopics(), threadDataBean.getContent());
//            myHolder.tv_content.setText(threadDataBean.getContent());
//            if ("1".equals(threadDataBean.getIs_my_thread())) {//是否我的帖子1是，0否
//                myHolder.btn_status.setVisibility(View.GONE);
//            }
            if (1 == threadDataBean.getIs_attention()) {//是否关注1是，0否
                myHolder.btn_status.setVisibility(View.VISIBLE);
                myHolder.btn_status.setText("取消关注");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cA5A7B0));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
            } else if (0 == threadDataBean.getIs_attention()) {
                myHolder.btn_status.setVisibility(View.VISIBLE);
                myHolder.btn_status.setText("关注+");
                myHolder.btn_status.setTextColor(mContext.getResources().getColor(R.color.cE73B30));
                myHolder.btn_status.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
            } else if (2 == threadDataBean.getIs_attention()) {//我的帖子
                myHolder.btn_status.setVisibility(View.GONE);
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


    }

    @Override
    public int getItemCount() {
        return threadDataBeans != null ? threadDataBeans.size() : 0;
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView civ_head;
        private TextView tv_name, tv_time;
        //        private TextView tv_content;
        private RichTextView tv_content;
        private ImageView iv_share, iv_dianzan;
        private TextView tv_message, tv_dianzan;
        private Button btn_status;

        private RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            civ_head = itemView.findViewById(R.id.civ_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            //
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_content.setAtColor(Color.RED);
            tv_content.setTopicColor(Color.BLUE);
            tv_content.setLinkColor(Color.YELLOW);
            tv_content.setNeedNumberShow(false);
            tv_content.setNeedUrlShow(false);
            tv_content.setSpanAtUserCallBackListener(new SpanAtUserCallBack() {
                @Override
                public void onClick(View view, UserModel userModel1) {
                    mContext.startActivity(new Intent(mContext, MineDynamicActivity.class).putExtra("userId", userModel1.getUser_id()));
                }
            });
            tv_content.setSpanTopicCallBackListener(new SpanTopicCallBack() {
                @Override
                public void onClick(View view, TopicModel topicModel) {
                    mContext.startActivity(new Intent(mContext, TopicInfoActivity.class)
                            .putExtra("topicId", topicModel.getTopicId())
                            .putExtra("topicTitle", topicModel.getTopicName()));

                }
            });
//            tv_content.setSpanUrlCallBackListener(spanUrlCallBack);
            //所有配置完成后才设置text
//            richTextView.setRichText(content, nameList, topicModels);
            //
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
            btn_status = itemView.findViewById(R.id.btn_status);

            relativeLayout = itemView.findViewById(R.id.relativeLayout);

            btn_status.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            tv_message.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
            //
            civ_head.setOnClickListener(this);


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
        private RichTextView tv_content;
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
            //
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_content.setAtColor(Color.RED);
            tv_content.setTopicColor(Color.BLUE);
            tv_content.setLinkColor(Color.YELLOW);
            tv_content.setNeedNumberShow(false);
            tv_content.setNeedUrlShow(false);
            tv_content.setSpanAtUserCallBackListener(new SpanAtUserCallBack() {
                @Override
                public void onClick(View view, UserModel userModel1) {
                    mContext.startActivity(new Intent(mContext, MineDynamicActivity.class).putExtra("userId", userModel1.getUser_id()));
                }
            });
            tv_content.setSpanTopicCallBackListener(new SpanTopicCallBack() {
                @Override
                public void onClick(View view, TopicModel topicModel) {
                    mContext.startActivity(new Intent(mContext, TopicInfoActivity.class)
                            .putExtra("topicId", topicModel.getTopicId())
                            .putExtra("topicTitle", topicModel.getTopicName())
                            .putExtra("viewNum",topicModel.getViewNums()));

                }
            });
            //
            iv_share = itemView.findViewById(R.id.iv_share);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dianzan = itemView.findViewById(R.id.tv_dianzan);
            iv_dianzan = itemView.findViewById(R.id.iv_dianzan);
            btn_status = itemView.findViewById(R.id.btn_status);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);


            btn_status.setOnClickListener(this);
            iv_share.setOnClickListener(this);
            tv_message.setOnClickListener(this);
            tv_dianzan.setOnClickListener(this);
            iv_dianzan.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);

            //
            civ_head.setOnClickListener(this);


            nineGridImageView = itemView.findViewById(R.id.nineGridImageView);
            nineGridImageView.setAdapter(adapter);
            nineGridImageView.setItemImageClickListener(new ItemImageClickListener<ImagesBean>() {
                @Override
                public void onItemImageClick(Context context, ImageView imageView, int index, List<ImagesBean> list) {
                    GPreviewBuilder.from((Activity) context)
                            .setData(computeBoundsBackward(list))
                            .setIsScale(true)
                            .setCurrentIndex(index)
                            .setType(GPreviewBuilder.IndicatorType.Dot)
                            .start();//启动
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (myItemOnClickListener != null)
                myItemOnClickListener.onItemClick(getLayoutPosition(), v);

        }

        /**
         * 查找信息
         *
         * @param list 图片集合
         */
        private List<ThumbViewInfo> computeBoundsBackward(List<ImagesBean> list) {
            List<ThumbViewInfo> thumbViewInfos = new ArrayList<>();
            for (int i = 0; i < nineGridImageView.getChildCount(); i++) {
                View itemView = nineGridImageView.getChildAt(i);
                Rect bounds = new Rect();
                if (itemView != null) {
                    ImageView thumbView = (ImageView) itemView;
                    thumbView.getGlobalVisibleRect(bounds);
                }
                thumbViewInfos.add(new ThumbViewInfo(list.get(i).getUrl(), bounds));

            }
            return thumbViewInfos;
        }
    }


    private void setTopAndUser(RichTextView richTextView, List<AttentionBean> users, List<TopicBean> topics, String text) {
        List<UserModel> userModels = new ArrayList<>();
        List<TopicModel> topicModels = new ArrayList<>();

        if (users != null) {
            for (AttentionBean attentionBean : users) {
                userModels.add(new UserModel(attentionBean.getUser_name(), attentionBean.getId()));
            }
        }
        if (topics != null) {
            for (TopicBean topicBean : topics) {
                topicModels.add(new TopicModel(topicBean.getTitle(), topicBean.getId(),topicBean.getView_num()));
            }
        }
        richTextView.setRichText(text, userModels, topicModels);
    }
}
