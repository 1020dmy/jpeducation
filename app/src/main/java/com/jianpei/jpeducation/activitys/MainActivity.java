package com.jianpei.jpeducation.activitys;

import android.Manifest;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.PermissionBaseActivity;
import com.jianpei.jpeducation.fragment.school.SchoolFragment;
import com.jianpei.jpeducation.fragment.home.HomeFragment;
import com.jianpei.jpeducation.fragment.mine.MineFragment;
import com.jianpei.jpeducation.fragment.elective.ElectiveFragment;
import com.jianpei.jpeducation.fragment.tiku.TikuFragment;
import com.jianpei.jpeducation.viewmodel.MainModel;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends PermissionBaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    HomeFragment homeFragment;
    SchoolFragment schoolFragment;
    ElectiveFragment electiveFragment;
    TikuFragment tikuFragment;
    MineFragment mineFragment;
    @BindView(R.id.btn_title)
    Button btnTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    private Fragment[] fragments;
    private int lastfragment = 0;

    private MainModel mainModel;
    private String[] mPermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };


    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        schoolFragment = new SchoolFragment();
        electiveFragment = new ElectiveFragment();
        mineFragment = new MineFragment();
        tikuFragment = new TikuFragment();
        fragments = new Fragment[]{homeFragment, schoolFragment, electiveFragment, tikuFragment, mineFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).show(homeFragment).commit();
        navView.setOnNavigationItemSelectedListener(this);

        setPermissions(mPermissions, new InterfacePermission() {
            @Override
            public void onAllow() {
                shortToast("已经获取全部权限");
            }
        });

    }


    @Override
    protected void initData() {
        mainModel = ViewModelProviders.of(this).get(MainModel.class);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (lastfragment != 0) {
                    switchFragment(lastfragment, 0);
                    lastfragment = 0;
                }
                return true;
            case R.id.navigation_school:
                if (lastfragment != 1) {
                    switchFragment(lastfragment, 1);
                    lastfragment = 1;
                }
                return true;
            case R.id.navigation_elective:
                if (lastfragment != 2) {
                    switchFragment(lastfragment, 2);
                    lastfragment = 2;
                }
                return true;
            case R.id.navigation_tiku:
                if (lastfragment != 3) {
                    switchFragment(lastfragment, 3);
                    lastfragment = 3;
                }
                return true;
            case R.id.navigation_mine:
                if (lastfragment != 4) {
                    switchFragment(lastfragment, 4);
                    lastfragment = 4;
                }
                return true;
            default:
                break;
        }
        return false;
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

        if (index == 3) {
            rlTitle.setVisibility(View.GONE);
        } else {
            rlTitle.setVisibility(View.VISIBLE);

        }
    }


    @OnClick({R.id.imageButton, R.id.btn_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                mainModel.upData(btnTitle.getText().toString());
                break;
            case R.id.btn_title:
                startActivity(new Intent(this, SelectDisciplineActivity.class));
                overridePendingTransition(R.anim.activity_close_in, R.anim.activity_close_out);
//                inActivity();
                break;

        }

    }
}
