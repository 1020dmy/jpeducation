package com.jianpei.jpeducation.activitys;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.activitys.mine.MineMessageActivity;
import com.jianpei.jpeducation.activitys.mine.SettingActivity;
import com.jianpei.jpeducation.activitys.mine.ShoppingCartActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.base.PermissionBaseActivity;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.fragment.elective.ElectiveFragment;
import com.jianpei.jpeducation.fragment.home.HomeFragment;
import com.jianpei.jpeducation.fragment.mine.MineFragment;
import com.jianpei.jpeducation.fragment.school.SchoolFragment;
import com.jianpei.jpeducation.fragment.tiku.TikuFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.dialog.UpVersionDialog;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassManager;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.jianpei.jpeducation.viewmodel.VersionDetectModel;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends PermissionBaseActivity implements RadioGroup.OnCheckedChangeListener {


    HomeFragment homeFragment;
    SchoolFragment schoolFragment;
    ElectiveFragment electiveFragment;
    TikuFragment tikuFragment;
    MineFragment mineFragment;
    @BindView(R.id.btn_title)
    Button btnTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    //    @BindView(R.id.include_title)
//    LinearLayout llTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ib_shopping)
    ImageButton ibShopping;


    private Fragment[] fragments;
    private int lastfragment = 0;

    private MainModel mainModel;
    private String[] mPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    private String catId, catName;
    private String ncatId;


    private UpVersionDialog upVersionDialog;

    private VersionDetectModel versionDetectModel;

    //


    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        versionDetectModel = new ViewModelProvider(this).get(VersionDetectModel.class);

        setTitleViewPadding(ivStatue);
        homeFragment = new HomeFragment();
        schoolFragment = new SchoolFragment();
        electiveFragment = new ElectiveFragment();
        mineFragment = new MineFragment();
        tikuFragment = new TikuFragment();
        fragments = new Fragment[]{homeFragment, schoolFragment, electiveFragment, tikuFragment, mineFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).show(homeFragment).commit();
        radioGroup.check(R.id.rb_home);
        radioGroup.setOnCheckedChangeListener(this);


        setPermissions(mPermissions, new InterfacePermission() {
            @Override
            public void onAllow() {
//                shortToast("已经获取全部权限");
            }
        });




    }


    @Override
    protected void initData() {
        mainModel = new ViewModelProvider(this).get(MainModel.class);

        mainModel.getChangeBottomLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 2) {
                    radioGroup.check(R.id.rb_xuanke);
//                    if (lastfragment != 2) {
//                        switchFragment(lastfragment, 2);
//                        lastfragment = 2;
//                    }
                }
            }
        });
        //未读消息数量
        mainModel.getMessageNumLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null && integer > 0) {
                    Drawable drawable = getResources().getDrawable(
                            R.drawable.mine_message);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tvMessage.setCompoundDrawables(null, drawable, null, null);
                } else {
                    Drawable drawable = getResources().getDrawable(
                            R.drawable.mine_unmessage);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                            drawable.getMinimumHeight());
                    tvMessage.setCompoundDrawables(null, drawable, null, null);
                }
            }
        });

        versionDetectModel.getVersionDetectLiveData().observe(this, new Observer<VersionDetectBean>() {
            @Override
            public void onChanged(VersionDetectBean versionDetectBean) {
                if (upVersionDialog == null)
                    upVersionDialog = new UpVersionDialog(MainActivity.this);
                upVersionDialog.setData(versionDetectBean);
                upVersionDialog.show();
            }
        });
        //检测更新
//        versionDetectModel.versionDetect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ncatId = SpUtils.getValue(SpUtils.catId);
        if (!ncatId.equals(catId)) {
            catId = ncatId;
            catName = SpUtils.getValue(SpUtils.catName);
            mainModel.upData(catId);
            btnTitle.setText(catName);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                if (lastfragment != 0) {
                    switchFragment(lastfragment, 0);
                    lastfragment = 0;
                }
                break;
            case R.id.rb_xueyuan:
                if (lastfragment != 1) {
                    switchFragment(lastfragment, 1);
                    lastfragment = 1;
                }
                break;
            case R.id.rb_xuanke:
                if (lastfragment != 2) {
                    switchFragment(lastfragment, 2);
                    lastfragment = 2;
                }
                break;
            case R.id.rb_tiku:
                if (lastfragment != 3) {
                    switchFragment(lastfragment, 3);
                    lastfragment = 3;
                }
                break;
            case R.id.rb_mine:
                if (lastfragment != 4) {
                    switchFragment(lastfragment, 4);
                    lastfragment = 4;
                }
                break;
        }

    }


    /**
     * 切换fragmeng
     *
     * @param lastfragment
     * @param index
     */
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.frameLayout, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();

        if (index == 4) {
            tvMessage.setVisibility(View.VISIBLE);
            tvSetting.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.GONE);
            btnTitle.setVisibility(View.GONE);
            tvSearch.setVisibility(View.GONE);
            ibShopping.setVisibility(View.GONE);


        } else if (index == 1) {

            tvMessage.setVisibility(View.GONE);
            tvSetting.setVisibility(View.GONE);
            btnTitle.setVisibility(View.GONE);
            imageButton.setVisibility(View.GONE);
//            tvSearch.setVisibility(View.VISIBLE);
            ibShopping.setVisibility(View.GONE);


        } else if (index == 2) {
            tvMessage.setVisibility(View.GONE);
            tvSetting.setVisibility(View.GONE);
            btnTitle.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.VISIBLE);
            ibShopping.setVisibility(View.VISIBLE);
            tvSearch.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.GONE);
            tvSetting.setVisibility(View.GONE);
            btnTitle.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.VISIBLE);
            tvSearch.setVisibility(View.GONE);
            ibShopping.setVisibility(View.GONE);


        }
    }


    @OnClick({R.id.imageButton, R.id.btn_title, R.id.tv_message, R.id.tv_setting, R.id.ib_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.btn_title:
                startActivity(new Intent(this, SelectDisciplineActivity.class));
                overridePendingTransition(R.anim.activity_close_in, R.anim.activity_close_out);
//                inActivity();
                break;
            case R.id.tv_message:
                if (isLogin())
                    startActivity(new Intent(this, MineMessageActivity.class));
                else
                    startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.tv_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.ib_shopping:
                if (isLogin())
                    startActivity(new Intent(this, ShoppingCartActivity.class));
                else
                    startActivity(new Intent(this, LoginActivity.class));
                break;

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        L.e("=====onNewIntent=====");
        if (intent != null && "IntegralActivity".equals(intent.getStringExtra("from"))) {
            radioGroup.check(R.id.rb_tiku);
        }
        super.onNewIntent(intent);
    }


    @Override
    protected void onDestroy() {
        DownloadClassManager.getInstance().stopAllDownloads();
        DownloadClassManager.getInstance().release();

        super.onDestroy();

    }


}
