package com.aliyun.vodplayerview.activity;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.aliyun.vodplayer.R;
import com.aliyun.vodplayerview.listener.OnNotifyActivityListener;

import java.util.ArrayList;

/**
 * 设置界面, 主要是UrlPlayFragment和VidPlayFragment的寄生Activity
 * Created by Mulberry on 2018/4/4.
 */
public class AliyunPlayerSettingActivity extends FragmentActivity implements
        OnClickListener, OnNotifyActivityListener {

    private TextView tvVidplay;
    private TextView tvUrlplay;
    private ImageView ivVidplay;
    private ImageView ivUrlplay;
    private Button btnStartPlayer;

    private ArrayList<Fragment> fragmentArrayList;

    private static final int FRAGMENT_VID_PLAY = 0;
    private static final int FRAGMENT_URL_PLAY = 1;
    AliyunVidPlayFragment aliyunVidPlayFragment;
    AliyunUrlPlayFragment aliyunUrlPlayFragment;
    private ImageView ivBack;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setting_layout);
        tvVidplay = (TextView) findViewById(R.id.tv_vidplay);
        tvUrlplay = (TextView) findViewById(R.id.tv_urlplay);
        ivVidplay = (ImageView) findViewById(R.id.iv_vidplay);
        ivUrlplay = (ImageView) findViewById(R.id.iv_urlplay);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnStartPlayer = (Button) findViewById(R.id.btn_start_player);

        fragmentArrayList = new ArrayList<Fragment>();
        aliyunVidPlayFragment = new AliyunVidPlayFragment();
        aliyunUrlPlayFragment = new AliyunUrlPlayFragment();
        aliyunVidPlayFragment.setOnNotifyActivityListener(this);
        aliyunUrlPlayFragment.setOnNotifyActivityListener(this);
        fragmentArrayList.add(aliyunVidPlayFragment);
        fragmentArrayList.add(aliyunUrlPlayFragment);

        tvVidplay.setOnClickListener(this);
        tvUrlplay.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnStartPlayer.setOnClickListener(this);

        ivVidplay.setActivated(true);
        ivUrlplay.setActivated(false);

        changeFragment(FRAGMENT_VID_PLAY);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_vidplay) {

            changeFragment(FRAGMENT_VID_PLAY);
            ivVidplay.setActivated(true);
            ivUrlplay.setActivated(false);

        } else if (i == R.id.tv_urlplay) {

            changeFragment(FRAGMENT_URL_PLAY);
            ivUrlplay.setActivated(true);
            ivVidplay.setActivated(false);

        } else if (i == R.id.btn_start_player) {
            if (mCurrentFrgment instanceof AliyunVidPlayFragment) {
                aliyunVidPlayFragment.startToPlayerByVid();
            } else if (mCurrentFrgment instanceof AliyunUrlPlayFragment) {
                aliyunUrlPlayFragment.startPlayerByUrl();
            }
        } else if (i == R.id.iv_back) {
            finish();
        }
    }

    private Fragment mCurrentFrgment;

    /**
     * use index to change fragment
     *
     * @param index
     */
    private void changeFragment(int index) {
        if (findViewById(R.id.player_settings_content) != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (null != mCurrentFrgment) {
                ft.hide(mCurrentFrgment);
            }
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(index).getClass().getName());

            if (null == fragment) {
                fragment = fragmentArrayList.get(index);
            }
            mCurrentFrgment = fragment;

            ft.replace(R.id.player_settings_content, fragment, fragment.getClass().getName());
            ft.show(fragment);
            ft.commit();
        }
    }

    @Override
    public void onNotifyActivity() {
        setResult(AliyunPlayerSkinActivity.RESULT_OK);
        finish();
    }
}
