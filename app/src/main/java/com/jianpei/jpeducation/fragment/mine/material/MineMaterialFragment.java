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
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineMaterialFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MaterialModel materialModel;

    private MaterialAdapter materialAdapter;
    //

    private List<MaterialTitle> mMaterialTitles;

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
        mMaterialTitles = new ArrayList<>();
        materialAdapter = new MaterialAdapter(this);

        recyclerView.setAdapter(materialAdapter);
        //数据库查询资料标题返回结果
        materialModel.getMaterialTitlesLiveData().observe(this, new Observer<List<MaterialTitle>>() {
            @Override
            public void onChanged(List<MaterialTitle> materialTitles) {
                dismissLoading();
                mMaterialTitles.clear();
                mMaterialTitles.addAll(materialTitles);
                materialAdapter.setList(mMaterialTitles);

            }
        });
        //根据class_id查询资料
        materialModel.getInquireMaterialInfoBeansLiveData().observe(this, new Observer<List<MaterialInfoBean>>() {
            @Override
            public void onChanged(List<MaterialInfoBean> materialInfoBeans) {
                dismissLoading();
                materialAdapter.nodeAddData(mMaterialTitle, 0, materialInfoBeans);
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
        materialModel.getMaterialTitles();

    }

    @Override
    public void onItemClick(int position, View view) {

    }

    private MaterialTitle mMaterialTitle;
    private MaterialInfoBean mMaterialInfoBean;

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        switch (view.getId()) {
            case R.id.linearLayout:
                mMaterialTitle = (MaterialTitle) data;
                if (mMaterialTitle.isExpanded() && mMaterialTitle.getList().size() == 0) {
                    showLoading("");
                    materialModel.getMaterialInfoBeans(mMaterialTitle.getId());
                }
                break;
            case R.id.tv_down:
                mMaterialInfoBean = (MaterialInfoBean) data;
                startActivity(new Intent(getActivity(), PdfReaderActivity.class).putExtra("materialInfoBean", mMaterialInfoBean));
                break;
        }

    }
}
