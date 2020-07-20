package com.jianpei.jpeducation.fragment.tiku;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;
import com.jianpei.jpeducation.viewmodel.TikuModel;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TikuFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    @BindView(R.id.tv_eight)
    TextView tvEight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TikuModel tikuModel;


    @Override
    protected int initLayout() {
        return R.layout.fragment_tiku;
    }

    @Override
    protected void initView(View view) {
        tikuModel = new ViewModelProvider(this).get(TikuModel.class);


    }

    @Override
    protected void initData(Context mContext) {
        tikuModel.getPaperHomeBeanLiveData().observe(this, new Observer<PaperHomeBean>() {
            @Override
            public void onChanged(PaperHomeBean paperHomeBean) {
                dismissLoading();


            }
        });
        tikuModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

    }

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six, R.id.tv_seven, R.id.tv_eight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_one:
                break;
            case R.id.tv_two:
                break;
            case R.id.tv_three:
                break;
            case R.id.tv_four:
                break;
            case R.id.tv_five:
                break;
            case R.id.tv_six:
                break;
            case R.id.tv_seven:
                break;
            case R.id.tv_eight:
                break;
        }
    }
}
