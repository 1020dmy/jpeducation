package com.jianpei.jpeducation.activitys.launcher;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager2.widget.ViewPager2;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.SelectDisciplineActivity;
import com.jianpei.jpeducation.adapter.GuideAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideActivity extends BaseNoStatusActivity {

    @BindView(R.id.viewPage)
    ViewPager2 viewPage;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    @BindView(R.id.btn_up)
    Button btnUp;
    @BindView(R.id.button)
    Button button;
    private GuideAdapter guideAdapter;
    private List<String> imageUrls;
    List<ImageView> points;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {


        imageUrls = getIntent().getStringArrayListExtra("images");

        points = new ArrayList<>();
        guideAdapter = new GuideAdapter(imageUrls, this);
        viewPage.setAdapter(guideAdapter);
        viewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == imageUrls.size() - 1) {
                    btnUp.setVisibility(View.GONE);
                    llPoint.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                } else {
                    btnUp.setVisibility(View.VISIBLE);
                    llPoint.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
                for (ImageView imageView : points) {
                    imageView.setImageResource(R.drawable.yuandian_gray);
                }
                points.get(position).setImageResource(R.drawable.yuandian_red);

            }
        });


    }

    @Override
    protected void initData() {

        addView();

    }


    @OnClick({R.id.btn_up, R.id.button})
    public void onViewClicked() {
        SpUtils.put(SpUtils.ISFirst, 1);
        startActivity(new Intent(this, SelectDisciplineActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        points.clear();
        points = null;
        imageUrls.clear();
        imageUrls = null;
    }


    protected void addView() {
        if (imageUrls == null)
            return;

        for (String url : imageUrls) {
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.yuandian_gray);
            point.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtil.dp2px(25), DisplayUtil.dp2px(25)));
            points.add(point);
            llPoint.addView(point);
        }
        guideAdapter.notifyDataSetChanged();
    }
}
