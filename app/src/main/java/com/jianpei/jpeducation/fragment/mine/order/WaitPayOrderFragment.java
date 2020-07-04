package com.jianpei.jpeducation.fragment.mine.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.ShoppingCartActivity;
import com.jianpei.jpeducation.activitys.order.OrderConfirmActivity;
import com.jianpei.jpeducation.adapter.order.MyOrderListItemListener;
import com.jianpei.jpeducation.adapter.order.WaitPayOrderAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderDataBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.dialog.CancelOrderDialog;
import com.jianpei.jpeducation.viewmodel.OrderListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaitPayOrderFragment extends BaseFragment implements MyOrderListItemListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private OrderListModel orderListModel;
    private List<OrderDataBean> mOrderDataBeans;

    private WaitPayOrderAdapter waitPayOrderAdapter;
    private CancelOrderDialog cancelOrderDialog;
    private int page = 1;


    @Override
    protected int initLayout() {
        return R.layout.fragment_all_order;
    }

    @Override
    protected void initView(View view) {
        orderListModel = new ViewModelProvider(this).get(OrderListModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                L.e("开始刷新");
                page = 1;
                orderListModel.orderData(2, page, 10);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                L.e("开始加载更多");
                page++;
                orderListModel.orderData(2, page, 10);
            }
        });
    }

    @Override
    protected void initData(Context mContext) {
        mOrderDataBeans = new ArrayList<>();
        waitPayOrderAdapter = new WaitPayOrderAdapter(mOrderDataBeans, getActivity());
        waitPayOrderAdapter.setMyOrderListItemListener(this);
        recyclerView.setAdapter(waitPayOrderAdapter);
        //请求订单列表结果
        orderListModel.getOrderDataBeansLiveData().observe(this, new Observer<OrderListBean>() {
            @Override
            public void onChanged(OrderListBean orderListBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    mOrderDataBeans.clear();
                }
                mOrderDataBeans.addAll(orderListBean.getData());
                waitPayOrderAdapter.notifyDataSetChanged();
                L.e("====size:" + mOrderDataBeans.size());
            }
        });
        //发起支付选择价格
        orderListModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<ClassGenerateOrderBean>() {
            @Override
            public void onChanged(ClassGenerateOrderBean classGenerateOrderBean) {
                dismissLoading();
                startActivity(new Intent(getActivity(), OrderConfirmActivity.class).putExtra("classGenerateOrderBean", classGenerateOrderBean));
            }
        });
        //错误返回
        orderListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        orderListModel.orderData(2, page, 10);
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (cancelOrderDialog == null) {
                    cancelOrderDialog = new CancelOrderDialog(getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelOrderDialog.dismiss();
                        }
                    });
                }
                cancelOrderDialog.show();

                break;
            case R.id.tv_pay:
                if (mOrderDataBeans.get(position).getGroup_list() == null || mOrderDataBeans.get(position).getGroup_list().size() == 0) {
                    showLoading("");
                    orderListModel.classGenerateOrder(mOrderDataBeans.get(position).getGoods_type(), mOrderDataBeans.get(position).getGroup_id(), mOrderDataBeans.get(position).getPid(), mOrderDataBeans.get(position).getId());
                } else {
                    startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                }
                break;
        }
    }
}
