package com.jianpei.jpeducation.utils.down;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.jianpei.jpeducation.utils.L;

import java.io.File;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class AndroidDownloadManager {
    private DownloadManager downloadManager;
    private Context context;
    private long downloadId;
    private String url;
    private String name;

    private String path;

    private AndroidDownloadManagerListener listener;

    private TimerTask timerTask;

    public AndroidDownloadManager(Context context, String url) {
        this(context, url, getFileNameByUrl(url));
    }

    public AndroidDownloadManager(Context context, String url, String name) {
        this.context = context;
        this.url = url;
        this.name = name;
    }

    public AndroidDownloadManager setListener(AndroidDownloadManagerListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 开始下载
     */
    public void download(Handler handler) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(name);
        request.setDescription("文件正在下载中......");
        request.setVisibleInDownloadsUi(true);
        ///storage/emulated/0/Android/data/com.example.dell.mvvmdagger/files/Download/mobileqq_android.apk
        //设置下载的路径
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(file));
        path = file.getAbsolutePath();

        //获取DownloadManager
        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            if (listener != null) {
                listener.onPrepare();
            }
            downloadId = downloadManager.enqueue(request);
        }
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);


        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Cursor cursor = downloadManager.query(query);

                if (cursor != null && cursor.moveToFirst()) {
                    switch (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        //下载完成
                        case DownloadManager.STATUS_SUCCESSFUL:
                            Message msg = Message.obtain();
                            msg.arg1 = 100;
                            handler.sendMessage(msg);
                            cursor.close();
                            timerTask.cancel();
                            break;
                        //下载暂停
                        case DownloadManager.STATUS_PAUSED:
                            break;
                        //下载延迟
                        case DownloadManager.STATUS_PENDING:
                            L.e("========STATUS_PENDING========");
                            break;
                        //正在下载
                        case DownloadManager.STATUS_RUNNING:
                            long bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            long bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            int pro = (int) ((bytes_downloaded * 100) / bytes_total);

                            L.e("========STATUS_RUNNING========" + bytes_downloaded + "," + bytes_total);

                            L.e("下载进度：" + pro);
                            Message message = Message.obtain();
                            message.arg1 = pro;
                            handler.sendMessage(message);
                            break;
                        //下载失败
                        case DownloadManager.STATUS_FAILED:
                            L.e("========STATUS_FAILED========");
                            Message sss = Message.obtain();
                            sss.arg1 = -1;
                            handler.sendMessage(sss);
                            cursor.close();
                            timerTask.cancel();
                            break;
                    }
                }
                cursor.close();
            }
        };
        timer.schedule(timerTask, 0, 3000);
    }




//    private void checkStatus() {
//        boolean isRuning = true;
////        Intent intent = new Intent();
//        while (isRuning) {
//            DownloadManager.Query query = new DownloadManager.Query();
//            //通过下载的id查找
//            query.setFilterById(downloadId);
//            Cursor cursor = downloadManager.query(query);
//            int[] bytesAndStatus = new int[]{
//                    -1, -1, 0
//            };
//            if (cursor != null && cursor.moveToFirst()) {
//                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
//                switch (status) {
//                    //下载暂停
//                    case DownloadManager.STATUS_PAUSED:
//                        break;
//                    //下载延迟
//                    case DownloadManager.STATUS_PENDING:
//                        break;
//                    //正在下载
//                    case DownloadManager.STATUS_RUNNING:
//                        L.i("gdchent", "下载中");
//                        //已经下载文件大小
//                        bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                        //下载文件的总大小
//                        bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                        //下载状态
//                        bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
//                        L.i("gdchent", "下载文件的进度" + String.valueOf(bytesAndStatus[0]));
//
////                         intent.putExtra("data",bytesAndStatus[0]/bytesAndStatus[1]);
////                         intent.setAction("downloadAction");
////                         context.sendBroadcast(intent);
//                        double dProgress = deciMal(bytesAndStatus[0], bytesAndStatus[1]);
//                        if (listener != null) {
//                            listener.onDownLoading((int) (dProgress * 100));
//                        }
//                        break;
//                    //下载完成
//                    case DownloadManager.STATUS_SUCCESSFUL:
//                        if (listener != null) {
//                            listener.onSuccess(path);
//                        }
//                        cursor.close();
////                        context.unregisterReceiver(receiver);
//                        isRuning = false;
//                        break;
//                    //下载失败
//                    case DownloadManager.STATUS_FAILED:
//                        if (listener != null) {
//                            listener.onFailed(new Exception("下载失败"));
//                        }
//                        cursor.close();
//                        //context.unregisterReceiver(receiver);
//                        isRuning = false;
//                        break;
//                }
//            } else {
//                L.i("gdchent", "走这里");
//            }
//        }
//    }

    //广播监听下载的各个状态
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.i("gdchent","onReceiver");
//            int num=intent.getIntExtra("data",-1);
//            Log.i("gdchent","num:"+String.valueOf(num));
//        }
//    };

    public String getName() {
        return name;
    }

    /**
     * 通过URL获取文件名
     *
     * @param url
     * @return
     */
    private static final String getFileNameByUrl(String url) {
        String filename = url.substring(url.lastIndexOf("/") + 1);
        filename = filename.substring(0, filename.indexOf("?") == -1 ? filename.length() : filename.indexOf("?"));
        return filename;
    }

    private int deciMal(int top, int below) {
        double result = new BigDecimal((float) top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        L.e("返回的两位数", result + "'");
        return (int) result;
    }
}
