package com.jianpei.jpeducation.activitys.mine.mclass;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.dragswipe.DragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.offlineclass.OfflieClassAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassContentBean;
import com.jianpei.jpeducation.bean.offlineclass.OfflineClassTitleBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.OnClick;

public class OfflineClassActivity extends BaseNoStatusActivity {

    private String TAG = "OfflineClassActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_statue)
    ImageView ivStatue;

    private OfflieClassAdapter offlieClassAdapter;

    private List<OfflineClassTitleBean> offlineClassTitleBeans;

    private OfflineClassRoomModel offlineClassRoomModel;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_offline_class;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        tvTitle.setText("离线课程");
        imageButton.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {

        offlineClassRoomModel = new ViewModelProvider(this).get(OfflineClassRoomModel.class);

        offlineClassTitleBeans = new ArrayList<>();
        offlieClassAdapter = new OfflieClassAdapter();
        offlieClassAdapter.getDraggableModule().setSwipeEnabled(true);
        offlieClassAdapter.getDraggableModule().setOnItemSwipeListener(onItemSwipeListener);
        offlieClassAdapter.getDraggableModule().getItemTouchHelperCallback().setSwipeMoveFlags(ItemTouchHelper.LEFT);

        recyclerView.setAdapter(offlieClassAdapter);
        setData();

        offlieClassAdapter.addData(offlineClassTitleBeans);

        offlieClassAdapter.notifyDataSetChanged();


        offlineClassRoomModel.getCompleteDataLiveData().observe(this, new Observer<List<DownloadMediaInfo>>() {
            @Override
            public void onChanged(List<DownloadMediaInfo> downloadMediaInfos) {
                dismissLoading();
                if (downloadMediaInfos != null) {
                    L.e("======sieze:" + downloadMediaInfos.size());
                } else {
                    L.e("======sieze:null");
                }
            }
        });
        offlineClassRoomModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        offlineClassRoomModel.getCompleteData(5);


    }

    // 侧滑监听
    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "view swiped start: " + pos);
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "View reset: " + pos);
            BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "View Swiped: " + pos);
        }


        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(getResources().getColor(R.color.cFF0000));
        }
    };


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    private void setData() {

        for (int i = 0; i < 5; i++) {
            OfflineClassTitleBean offlineClassTitleBean = new OfflineClassTitleBean("测试title" + i, "");
            for (int j = 0; j < 10; j++) {
                OfflineClassContentBean offlineClassContentBean = new OfflineClassContentBean("测试content" + j, "");
                offlineClassTitleBean.getBaseNodes().add(offlineClassContentBean);
            }
            offlineClassTitleBeans.add(offlineClassTitleBean);

        }


    }

    ;


}
