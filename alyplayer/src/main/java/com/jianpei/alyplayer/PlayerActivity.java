package com.jianpei.alyplayer;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.source.UrlSource;

public class PlayerActivity extends AppCompatActivity {


    TextView tvBack;
    SurfaceView surfaceView;

    AliPlayer aliPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tvBack = findViewById(R.id.tv_back);
        surfaceView = findViewById(R.id.surfaceView);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化播放器
        aliPlayer = AliPlayerFactory.createAliPlayer(getApplicationContext());
        UrlSource urlSource = new UrlSource();
        urlSource.setUri("http://player.alicdn.com/video/aliyunmedia.mp4");
        aliPlayer.setDataSource(urlSource);
        aliPlayer.prepare();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                aliPlayer.setDisplay(holder);
                aliPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                aliPlayer.redraw();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                aliPlayer.setDisplay(null);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aliPlayer.release();
    }
}
