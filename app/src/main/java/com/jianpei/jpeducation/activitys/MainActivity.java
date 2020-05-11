package com.jianpei.jpeducation.activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.dashboard.DashboardFragment;
import com.jianpei.jpeducation.fragment.home.HomeFragment;
import com.jianpei.jpeducation.fragment.mine.MineFragment;
import com.jianpei.jpeducation.fragment.notifications.NotificationsFragment;
import com.jianpei.jpeducation.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    HomeFragment homeFragment;
    DashboardFragment dashboardFragment;
    NotificationsFragment notificationsFragment;
    MineFragment mineFragment;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;

    private Fragment[] fragments;
    private int lastfragment = 0;

    private MainPresenter mainPresenter;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        mineFragment = new MineFragment();
        fragments = new Fragment[]{homeFragment, dashboardFragment, notificationsFragment, mineFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).show(homeFragment).commit();
        navView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    protected void initData() {
        mainPresenter = ViewModelProviders.of(this).get(MainPresenter.class);

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
            case R.id.navigation_dashboard:
                if (lastfragment != 1) {
                    switchFragment(lastfragment, 1);
                    lastfragment = 1;
                }
                return true;
            case R.id.navigation_notifications:
                if (lastfragment != 2) {
                    switchFragment(lastfragment, 2);
                    lastfragment = 2;
                }
                return true;
            case R.id.navigation_mine:
                if (lastfragment != 3) {
                    switchFragment(lastfragment, 3);
                    lastfragment = 3;
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
    }


    @OnClick(R.id.imageButton)
    public void onViewClicked() {
        mainPresenter.upData(tvTitle.getText().toString());
    }
}
