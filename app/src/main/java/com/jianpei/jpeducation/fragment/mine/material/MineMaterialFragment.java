package com.jianpei.jpeducation.fragment.mine.material;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.pdf.PdfReaderActivity;
import com.jianpei.jpeducation.adapter.material.MaterialAdapter;
import com.jianpei.jpeducation.adapter.material.MaterialInfoProvider;
import com.jianpei.jpeducation.adapter.material.MaterialTitleProvider;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineMaterialFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MaterialModel materialModel;

    private MaterialAdapter materialAdapter;
    //
    private MaterialInfoProvider materialInfoProvider;
    private int page = 1, pagSize = 10;

    @Override
    protected int initLayout() {
        return R.layout.fragment_material_download;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);

    }

    @Override
    protected void initData(Context mContext) {

        L.e("========initData=========");
        materialAdapter = new MaterialAdapter();

        materialInfoProvider = new MaterialInfoProvider();
        materialInfoProvider.addChildClickViewIds(R.id.tv_down);
        materialInfoProvider.setMaterialInfoOnClickListener(new MaterialInfoProvider.MaterialInfoOnClickListener() {//点击下载按钮
            @Override
            public void materialInfoOnClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
                MaterialInfoBean materialInfoBean = (MaterialInfoBean) data;
                startActivity(new Intent(getActivity(), PdfReaderActivity.class).putExtra("materialInfoBean", materialInfoBean));

            }
        });
        materialAdapter.addNodeProvider(new MaterialTitleProvider());
        materialAdapter.addNodeProvider(materialInfoProvider);


        recyclerView.setAdapter(materialAdapter);
        materialModel.getMaterialDataBean().observe(this, new Observer<MaterialDataBean>() {
            @Override
            public void onChanged(MaterialDataBean materialDataBean) {
                materialModel.getDownloadCompleteMaterial(materialDataBean.getData());
            }
        });
        materialModel.getLocalMaterialLiveData().observe(this, new Observer<List<MaterialTitle>>() {
            @Override
            public void onChanged(List<MaterialTitle> materialTitles) {
                dismissLoading();
                materialAdapter.setList(materialTitles);
            }
        });
        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        materialModel.myMaterialData(page, pagSize);

    }
}
