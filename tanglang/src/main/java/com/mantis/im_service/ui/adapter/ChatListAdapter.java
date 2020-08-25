package com.mantis.im_service.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.mantis_im_sdk.bean.ReceiveEntity;
import com.google.gson.Gson;
import com.mantis.im_service.R;
import com.example.mantis_im_sdk.bean.ChatEntity;
import com.mantis.im_service.common.MantisCommon;
import com.example.mantis_im_sdk.face.EmojiUtil;
import com.example.mantis_im_sdk.utils.DateUtils;
import com.mantis.im_service.ui.views.FlowGroupView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private List<ChatEntity> mList = new ArrayList<>();
    private AdapterOnClickListener clickListener;

    @NonNull
    @Override
    public ChatListAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = null;
        if (viewType == MantisCommon.SEND) {
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_send_item_layout, parent, false);
        } else if (viewType == MantisCommon.RECEIVE) {
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_receive_item_layout, parent, false);
        } else {
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_system_item_layout, parent, false);
        }
        return new ChatViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatViewHolder holder, int position) {
        ChatEntity s = mList.get(position);
        holder.setData(s, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<ChatEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void upData(ChatEntity chatEntity) {
        for (int i = 0; i < mList.size(); i++) {
            if (chatEntity.getTimestamp() == mList.get(i).getTimestamp()) {
                mList.remove(i);
                mList.add(i, chatEntity);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void addData(ChatEntity chatEntity) {
        mList.add(chatEntity);
        notifyItemChanged(mList.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        ChatEntity chatEntity = mList.get(position);
        if (chatEntity.getStatus() == MantisCommon.SEND) {
            return MantisCommon.SEND;
        } else if (chatEntity.getStatus() == MantisCommon.RECEIVE) {
            return MantisCommon.RECEIVE;
        } else {
            return MantisCommon.SYSTEM;
        }
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private FlowGroupView item_chat_select_content;
        private TextView item_chat_content;
        private ImageView item_head_img;
        private TextView item_chat_time;
        private ImageView item_chat_img;
        private ImageView item_send_fil;
        private TextView item_send_stance;
        private TextView item_system_chat;
        private WebView item_chat_web;

        private ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            item_chat_select_content = itemView.findViewById(R.id.item_chat_select_content);
            item_head_img = itemView.findViewById(R.id.item_head_img);
            item_chat_content = itemView.findViewById(R.id.item_chat_content);
            item_chat_time = itemView.findViewById(R.id.item_chat_time);
            item_chat_img = itemView.findViewById(R.id.item_chat_img);
            item_send_fil = itemView.findViewById(R.id.item_send_fil);
            item_send_stance = itemView.findViewById(R.id.item_send_stance);
            item_system_chat = itemView.findViewById(R.id.item_system_chat);
            item_chat_web = itemView.findViewById(R.id.item_chat_web);
        }


        private void setData(ChatEntity chatEntity, int position) {
            if (chatEntity.getStatus() == MantisCommon.SYSTEM) {
                item_system_chat.setVisibility(View.VISIBLE);
                item_system_chat.setText(chatEntity.getShowContent());
                return;
            }
            //如果为接收的消息 并且有图像  展示
            if (TextUtils.isEmpty(chatEntity.getAgentImg())) {
                Glide.with(item_head_img.getContext())
                        .load(R.mipmap.default_counselor)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(item_head_img);
            } else {
                Glide.with(item_head_img.getContext())
                        .load(chatEntity.getAgentImg())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(item_head_img);
            }

            //如果两次聊天时间较远则也展示时间
            if (position != 0 && DateUtils.isCloseEnough(chatEntity.getTimestamp(), mList.get(position - 1).getTimestamp())) {
                item_chat_time.setVisibility(View.GONE);
            } else {
                if (chatEntity.getTimestamp() >= 0) {
                    item_chat_time.setText(DateUtils.formatDate(new Date(chatEntity.getTimestamp())));
                    item_chat_time.setVisibility(View.VISIBLE);
                }
            }
            if (chatEntity.getType().equals(MantisCommon.TEXT)) {
                //因接收消息类型不一致所以在这里做特殊处理  判断响应消息返回类型是否为JSON
                if (chatEntity.getStatus() == MantisCommon.RECEIVE && !TextUtils.isEmpty(chatEntity.getRcvjsonContent())) {
                    ReceiveEntity receiveEntity = new Gson().fromJson(chatEntity.getRcvjsonContent(), ReceiveEntity.class);

                    chatEntity.setShowContent(receiveEntity.getAdditionMsg());
                    chatEntity.setShowMsgBottom(setSelectContent(receiveEntity, chatEntity));
                }
                if (chatEntity.getStatus() == MantisCommon.RECEIVE && chatEntity.isWelcome()) {
                    //设置不让其跳转浏览器
                    item_chat_web.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    // 添加客户端支持
                    item_chat_web.setWebChromeClient(new WebChromeClient());
                    item_chat_web.setHorizontalScrollBarEnabled(false);//水平不显示
                    item_chat_web.setVerticalScrollBarEnabled(false); //垂直不显示
                    //不加这个图片显示不出来
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        item_chat_web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    }
                    item_chat_web.getSettings().setBlockNetworkImage(false);
                    item_chat_web.loadDataWithBaseURL(null, chatEntity.getShowContent(), "text/html", "utf-8", null);
                    item_chat_web.setVisibility(View.VISIBLE);
                } else {
                    if (chatEntity.getStatus() == MantisCommon.RECEIVE) {
                        item_chat_web.setVisibility(View.GONE);
                    }

                    EmojiUtil.handlerEmojiText(item_chat_content, chatEntity.getShowContent(), item_chat_content.getContext());

                    item_chat_img.setVisibility(View.GONE);
                    item_chat_content.setVisibility(View.VISIBLE);
                }
                if (chatEntity.isShowMsgBottom()) {
                    item_chat_select_content.setVisibility(View.VISIBLE);
                } else {
                    if (item_chat_select_content != null) {
                        item_chat_select_content.setVisibility(View.GONE);
                    }
                }
            } else if (chatEntity.getType().equals(MantisCommon.IMG)) {
                Glide.with(item_chat_img.getContext())
                        .load(chatEntity.getShowContent())
                        .into(item_chat_img);
                item_chat_content.setVisibility(View.GONE);
                item_chat_img.setVisibility(View.VISIBLE);
                item_chat_img.setOnClickListener((v) -> {
                    if (clickListener == null) {
                        return;
                    }
                    clickListener.bigPicOnClick(chatEntity.getShowContent());
                });
            }

            if (chatEntity.getStatus() != MantisCommon.SEND) {
                return;
            }
            if (chatEntity.getMsgStatus() == MantisCommon.MSG_STATUS_SEND_FAIL) {
                item_send_fil.setVisibility(View.VISIBLE);
                item_send_stance.setVisibility(View.GONE);
            } else {
                item_send_stance.setVisibility(View.VISIBLE);
                item_send_fil.setVisibility(View.GONE);
            }
            item_send_fil.setOnClickListener(v -> {
                if (clickListener == null) {
                    return;
                }
                item_send_stance.setVisibility(View.VISIBLE);
                item_send_fil.setVisibility(View.GONE);
                chatEntity.setMsgStatus(MantisCommon.MSG_STATUS_RESEND);
                clickListener.reSendMsg(chatEntity);
            });
        }

        private boolean setSelectContent(ReceiveEntity receiveEntity, ChatEntity chatEntity) {
            if (receiveEntity.getContent() != null && receiveEntity.getContent().size() > 0) {
                item_chat_select_content.removeAllViews();
                for (ReceiveEntity.ContentBean contentBean : receiveEntity.getContent()) {
                    addTextView(contentBean, chatEntity);
                }
                return true;
            }
            return false;
        }

        /**
         * 动态添加布局
         *
         * @param contentBean
         */
        private void addTextView(ReceiveEntity.ContentBean contentBean, ChatEntity chatEntity) {
            GradientDrawable drawable = (GradientDrawable) item_chat_select_content.getContext().getDrawable(R.drawable.chat_receive_gridview_bg_style);
            if (!TextUtils.isEmpty(contentBean.getBgColor())) {
                drawable.setColor(Color.parseColor(contentBean.getBgColor()));
            } else {
                drawable.setColor(Color.parseColor("#06CB94"));
            }
            TextView child = new TextView(item_chat_select_content.getContext());
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            child.setPadding(30, 5, 30, 5);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.chat_receive_gridview_bg_style);
            child.setText(contentBean.getLabel());
            child.setTextColor(Color.WHITE);
            child.setTextSize(13);
            initEvents(child, contentBean, chatEntity);//监听
            item_chat_select_content.addView(child);
        }

        /**
         * 为每个view 添加点击事件
         */
        private void initEvents(final TextView tv, ReceiveEntity.ContentBean contentBean, ChatEntity chatEntity) {
            if (clickListener == null) {
                return;
            }
            tv.setOnClickListener((v) -> {
                clickListener.recMsgOnClick(contentBean);
                item_chat_select_content.setVisibility(View.GONE);
                chatEntity.setShowMsgBottom(false);
                chatEntity.setRcvjsonContent("");
                upData(chatEntity);
            });
        }
    }

    public void setClickListener(AdapterOnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
