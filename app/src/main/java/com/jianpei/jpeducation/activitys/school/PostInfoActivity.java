package com.jianpei.jpeducation.activitys.school;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStructure;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.school.CommentAdapter;
import com.jianpei.jpeducation.adapter.school.ImageListAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.school.AttentionResultBean;
import com.jianpei.jpeducation.bean.school.EvaluationDataBean;
import com.jianpei.jpeducation.bean.school.GardenPraiseBean;
import com.jianpei.jpeducation.bean.school.ImagesBean;
import com.jianpei.jpeducation.bean.school.ThreadDataBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.MyLayoutManager;
import com.jianpei.jpeducation.utils.keyboard.OnSoftKeyBoardChangeListener;
import com.jianpei.jpeducation.utils.keyboard.SoftKeyBoardListener;
import com.jianpei.jpeducation.view.ninegridelayout.ItemImageClickListener;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageView;
import com.jianpei.jpeducation.view.ninegridelayout.NineGridImageViewAdapter;
import com.jianpei.jpeducation.viewmodel.SchoolModel;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostInfoActivity extends BaseActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.btn_status)
    Button btnStatus;
    @BindView(R.id.tv_content)
    TextView tvContent;
    //    @BindView(R.id.rv_images)
//    RecyclerView rvImages;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.tv_dianzan_num)
    TextView tvDianzanNum;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    //
    @BindView(R.id.tv_reply_name)
    TextView tvReplyName;
    @BindView(R.id.et_reply_content)
    EditText etReplyContent;
    @BindView(R.id.tv_reply_send)
    TextView tvReplySend;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    //
    @BindView(R.id.nineGridImageView)
    NineGridImageView<ImagesBean> nineGridImageView;


    private SchoolModel schoolModel;
//    private ImageListAdapter imageListAdapter;
//    private List<ImagesBean> imagesBeans;

    //评论
    private CommentAdapter commentAdapter;
    private List<EvaluationDataBean> mEvaluationDataBeans;
    private String startId = "0", endId = "0";

    //点赞参数
    private String type = "2";
    //
    private ThreadDataBean mThreadDataBean;
    private String thread_id, tuserId;
    //
    private int commentIndex;

    private int status;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_post_info;
    }

    @Override
    protected void initView() {
        tvTitle.setText("帖子正文");
//        threadDataBean = getIntent().getParcelableExtra("threadDataBean");
        thread_id = getIntent().getStringExtra("thread_id");
        tuserId = getIntent().getStringExtra("userId");

        schoolModel = new ViewModelProvider(this).get(SchoolModel.class);
        //
        MyLayoutManager myLayoutManager = new MyLayoutManager(this);
        myLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(myLayoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        //
        //软键盘显示/隐藏
        SoftKeyBoardListener.setListener(this, new OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow() {
                //显示
                tvSend.setVisibility(View.VISIBLE);
                ivDianzan.setVisibility(View.GONE);
                tvDianzanNum.setVisibility(View.GONE);
                ivShare.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide() {
                //隐藏
                llComment.setVisibility(View.VISIBLE);
                tvSend.setVisibility(View.GONE);
                ivDianzan.setVisibility(View.VISIBLE);
                tvDianzanNum.setVisibility(View.VISIBLE);
                ivShare.setVisibility(View.VISIBLE);
                llReply.setVisibility(View.GONE);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                startId = "0";
                if (mEvaluationDataBeans.size() > 0) {
                    endId = mEvaluationDataBeans.get(mEvaluationDataBeans.size() - 1).getId();
                } else {
                    endId = "0";
                }
                schoolModel.evaluationData(thread_id, startId, endId);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                endId = "0";
                if (mEvaluationDataBeans.size() > 0) {
                    startId = mEvaluationDataBeans.get(0).getId();
                } else {
                    startId = "0";
                }
                schoolModel.evaluationData(thread_id, startId, endId);
            }
        });

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

    @Override
    protected void initData() {

        //评论
        mEvaluationDataBeans = new ArrayList<>();
        commentAdapter = new CommentAdapter(mEvaluationDataBeans, this);
        commentAdapter.setMyItemOnClickListener(this);

        recyclerView.setAdapter(commentAdapter);

        //评论列表
        schoolModel.getEvaluationDataBeansLiveData().observe(this, new Observer<List<EvaluationDataBean>>() {
            @Override
            public void onChanged(List<EvaluationDataBean> evaluationDataBeans) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (evaluationDataBeans != null && evaluationDataBeans.size() > 0) {
                    if ("0".equals(endId)) {
                        mEvaluationDataBeans.addAll(0, evaluationDataBeans);
                    } else {
                        mEvaluationDataBeans.addAll(evaluationDataBeans);
                    }
                    commentAdapter.notifyDataSetChanged();
                }

            }
        });
        //评论
        schoolModel.getInsertEvaluationLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                dismissLoading();
                shortToast(s);
                endId = "0";
                if (mEvaluationDataBeans.size() > 0) {
                    startId = mEvaluationDataBeans.get(0).getId();
                } else {
                    startId = "0";
                }
                schoolModel.evaluationData(thread_id, startId, endId);

            }
        });

        //关注/取消关注
        schoolModel.getAttentionLiveData().observe(this, new Observer<AttentionResultBean>() {
            @Override
            public void onChanged(AttentionResultBean s) {
                dismissLoading();
                changeStatus(s.getIs_attention());


            }
        });

        //点赞/取消点赞
        schoolModel.getGardenPraiseBeanLiveData().observe(this, new Observer<GardenPraiseBean>() {
            @Override
            public void onChanged(GardenPraiseBean gardenPraiseBean) {
                dismissLoading();
                if (gardenPraiseBean.getThread_info() != null) {
                    setData(gardenPraiseBean.getThread_info());
                } else if (gardenPraiseBean.getPost_info() != null) {
                    mEvaluationDataBeans.get(commentIndex).setIs_praise(gardenPraiseBean.getPost_info().getIs_praise());
                    mEvaluationDataBeans.get(commentIndex).setLike_num(gardenPraiseBean.getPost_info().getLike_num());
                    commentAdapter.notifyItemChanged(commentIndex);
                }

            }
        });
        //帖子详情
        schoolModel.getThreadInfoBeanLiveData().observe(this, new Observer<ThreadDataBean>() {
            @Override
            public void onChanged(ThreadDataBean threadDataBean) {
                dismissLoading();
                setData(threadDataBean);
            }
        });

        schoolModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (TextUtils.isEmpty(o)){

                }else{
                    shortToast(o);
                }
            }
        });
        showLoading("");
        schoolModel.threadInfo(thread_id);
        schoolModel.evaluationData(thread_id, startId, endId);


    }


    private void setData(ThreadDataBean threadInfoBean) {
        if (threadInfoBean == null)
            return;
        mThreadDataBean = threadInfoBean;
        tuserId=mThreadDataBean.getUser_id();
        Glide.with(this).load(threadInfoBean.getUser_img()).placeholder(R.drawable.head_icon).into(civHead);
        tvName.setText(threadInfoBean.getUser_name());
        tvTime.setText(threadInfoBean.getCreated_at_str());
        //是否关注
        changeStatus(threadInfoBean.getIs_attention());
        //
        if ("1".equals(threadInfoBean.getIs_praise())) {//是否点赞1是，0否
            ivDianzan.setImageResource(R.drawable.school_undianzan_icon);
        } else {
            ivDianzan.setImageResource(R.drawable.school_dianzan_icon);
        }
        if ("0".equals(threadInfoBean.getLike_num()))
            tvDianzanNum.setText("");
        else {
            tvDianzanNum.setText(threadInfoBean.getLike_num());
        }
        tvContent.setText(threadInfoBean.getContent());
        if (threadInfoBean.getImages() != null && threadInfoBean.getImages().size() != 0) {//判断有没有图片
            NineGridImageViewAdapter<ImagesBean> adapter = new NineGridImageViewAdapter<ImagesBean>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, ImagesBean imagesBean) {
                    Glide.with(PostInfoActivity.this).load(imagesBean.getUrl()).placeholder(R.drawable.feedback_add_pict).into(imageView);
                }
            };
            nineGridImageView.setAdapter(adapter);
            nineGridImageView.setImagesData(threadInfoBean.getImages());
        } else {
            nineGridImageView.setVisibility(View.GONE);
        }
    }

    private void changeStatus(int status) {
        this.status = status;
        if (0 == status) {
            btnStatus.setText("关注+");
            btnStatus.setTextColor(getResources().getColor(R.color.cE73B30));
            btnStatus.setBackgroundResource(R.drawable.shape_selectzhuanye_itemt);
        } else if (1 == status) {
            btnStatus.setText("取消关注");
            btnStatus.setTextColor(getResources().getColor(R.color.cA5A7B0));
            btnStatus.setBackgroundResource(R.drawable.shape_selectzhuanye_item);
        } else if (2 == status) {
            btnStatus.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.iv_back, R.id.btn_status, R.id.iv_dianzan, R.id.iv_share, R.id.tv_send, R.id.tv_reply_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(status, getIntent().putExtra("mThreadDataBean", mThreadDataBean));
                finish();
                break;
            case R.id.btn_status://关注/取消关注
                showLoading("");
//                viewType = 0;
                schoolModel.attention(tuserId, "", thread_id, null);
                break;
            case R.id.iv_dianzan://点赞/取消点赞
                showLoading("");
//                viewType = 1;
                schoolModel.gardenPraise(type, thread_id, "", "");
                break;
            case R.id.iv_share://分享
                if (mShareAction == null)
                    initShare();
                mShareAction.open();
                break;
            case R.id.tv_send://评论
                showLoading("");
                schoolModel.insertEvaluation(thread_id, etComment.getText().toString(), "", "");
                break;
            case R.id.tv_reply_send://评论回复
                showLoading("");
                schoolModel.insertEvaluation(thread_id, etReplyContent.getText().toString(), evaluationId, userId);
                break;
        }
    }

    private String evaluationId;
    private String userId;

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.iv_dianzan:
                showLoading("");
                commentIndex = position;
                schoolModel.gardenPraise("3", thread_id, "", mEvaluationDataBeans.get(position).getId());
                break;
            case R.id.ll_reply:
                llComment.setVisibility(View.GONE);
                evaluationId = mEvaluationDataBeans.get(position).getId();
                userId = mEvaluationDataBeans.get(position).getUser_id();
                llReply.setVisibility(View.VISIBLE);
                tvReplyName.setText("@" + mEvaluationDataBeans.get(position).getUser_name() + ":");
                showInput(etReplyContent);
                break;
            case R.id.linearLayout:
                startActivity(new Intent(this, ReplyListActivity.class).putExtra("evaluationDataBean", mEvaluationDataBeans.get(position)));
                break;
        }
    }

    public void showInput(EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(status, getIntent().putExtra("mThreadDataBean", mThreadDataBean));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }
}
