package com.jianpei.jpeducation.activitys.material;


import android.content.Intent;
import android.graphics.Point;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.pdf.PdfReaderActivity;
import com.jianpei.jpeducation.adapter.material.MaterialInfoAdapter;
import com.jianpei.jpeducation.adapter.material.MaterialTitleAdapter;
import com.jianpei.jpeducation.adapter.material.MyItemChildClickListener;
import com.jianpei.jpeducation.adapter.material.MyItemClickListener;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.down.QueueListener;
import com.jianpei.jpeducation.viewmodel.MaterialListModel;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.liulishuo.okdownload.DownloadTask;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MaterialListActivity extends BaseNoStatusActivity {


    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    String catName;

//    private MaterialAdapter materialAdapter;

//    private ArrayList<BaseNode> baseNodes;

    private MaterialListModel materialListModel;

    private MaterialModel materialModel;


    private String cat_id;
    private int pageIndex = 1, pageSize = 16;

//    private MaterialDataBean.MaterialTitle materialTitle;

//    private MaterialInfoBean materialInfoBean;

    private MaterialTitleAdapter materialTitleAdapter;

    private ArrayList<MaterialDataBean.MaterialTitle> materialTitles;

    private int mPosition;//当前点击的title位置

    QueueListener queueListener;

    private MaterialInfoAdapter.MyHolder mMyHolder;
    private String name;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_material_list;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        //
        initShare();//初始化分享
        //
        tvTitle.setText("资料下载");
        catName = SpUtils.getValue(SpUtils.catName);
        tvClass.setText(catName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cat_id = SpUtils.getValue(SpUtils.catId);

        initResultListener();


    }


    protected void initResultListener() {
        //资料下载，支付相关
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);

        materialListModel = new ViewModelProvider(this).get(MaterialListModel.class);
        //获取资料标题
        materialListModel.getMaterialDataBean().observe(this, new Observer<MaterialDataBean>() {
            @Override
            public void onChanged(MaterialDataBean materialDataBean) {
                materialTitles.addAll(materialDataBean.getData());
                materialTitleAdapter.notifyDataSetChanged();
            }
        });
        //获取资料
        materialListModel.getMaterialInfoBeans().observe(this, new Observer<ArrayList<MaterialInfoBean>>() {
            @Override
            public void onChanged(ArrayList<MaterialInfoBean> materialInfoBeans) {
                dismissLoading();
                materialTitles.get(mPosition).setMaterialInfoBeans(materialInfoBeans);
                materialTitleAdapter.notifyItemChanged(mPosition, R.id.recyclerView);

            }
        });
        //获取资料下载的url
        materialModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                L.e("===============获取到下载的资料");
                dismissLoading();
                if ("1".equals(downloadBean.getIs_pay())) {//0不需要积分，直接下载
                    if (queueListener == null) {
                        queueListener = new QueueListener();
                    }

                    DownloadTask task = new DownloadTask.Builder(downloadBean.getDownloadUrl(), getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
                            .setFilename(name)
                            .setMinIntervalMillisCallbackProcess(30)
                            .setPassIfAlreadyCompleted(false)
                            .build();
                    queueListener.bind(task, mMyHolder);
                    task.enqueue(queueListener);

                } else {
                    //要积分，弹窗
                    if (alertDialog == null) {
                        myDialog(downloadBean.getIntergral_price(), downloadBean.getUser_intergral());
                    } else {
                        alertDialog.show();
                    }
                }

            }
        });
        //积分付款
        materialModel.getIntegrlPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //支付车成功开始下载

            }
        });

        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                L.e("===============获取到下载的资料失败");
                dismissLoading();
                shortToast(o);
            }
        });
    }


    @Override
    protected void initData() {
        materialTitles = new ArrayList<>();
        materialTitleAdapter = new MaterialTitleAdapter(materialTitles, this);
        materialTitleAdapter.setMyItemClickListener(new MyItemClickListener() {
            @Override
            public void onClick(int position) {
                mPosition = position;
                if (materialTitles.get(position).isUnfold() && materialTitles.get(position).getMaterialInfoBeans() == null) {
                    showLoading("");
                    materialListModel.subMaterialData(cat_id, materialTitles.get(position).getId());
                }
            }
        });

        materialTitleAdapter.setMyItemChildClickListener(new MyItemChildClickListener() {
            @Override
            public void onClick(MaterialInfoBean materialInfoBean, MaterialInfoAdapter.MyHolder myHolder) {
                if (materialInfoBean.getStatus().equals("2")) {

                    startActivity(new Intent(MaterialListActivity.this, PdfReaderActivity.class).putExtra("materialInfoBean", materialInfoBean));

                } else {
                    showLoading("");
                    mMyHolder = myHolder;
                    name = materialInfoBean.getTitle();
                    materialModel.getDownloadUrl(materialInfoBean.getId());
                }

            }
        });

        recyclerView.setAdapter(materialTitleAdapter);


        materialListModel.materialData(cat_id, pageIndex, pageSize);
    }


    @OnClick({R.id.iv_back, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageButton:
                if (mShareAction != null) {
                    mShareAction.open();
                }
                break;
        }
    }

    private AlertDialog alertDialog;

    protected void myDialog(String intergral_price, String user_intergral) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_download, null);
        TextView tvXjifen = view.findViewById(R.id.tv_xjifen);
        TextView textView = view.findViewById(R.id.tv_jifen);
        tvXjifen.setText(intergral_price);
        textView.setText("您现在有" + user_intergral + "积分");
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button btnDownload = view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //确定积分支付
                materialModel.integrlPay(intergral_price);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Translucent_NoTitle);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager m = window.getWindowManager();
        Point p = new Point();
        m.getDefaultDisplay().getSize(p);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = p.y; // 高度设置为屏幕的0.5
        params.width = p.x; // 宽度设置为屏幕的0.6
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);


    }


}
