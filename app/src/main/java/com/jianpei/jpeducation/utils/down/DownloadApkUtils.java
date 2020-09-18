package com.jianpei.jpeducation.utils.down;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.utils.L;

import java.io.File;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DownloadApkUtils {

    private static File saveFile;
    private static long downloadId = 0;

    public static void startDownload(Context mContext, String url, String describeStr) {
        initFile(mContext);
        if (downloadId != 0) {  //根据任务ID判断是否存在相同的下载任务，如果有则清除
            clearCurrentTask(mContext, downloadId);
        }
        downloadId = downLoadApk(mContext, url, describeStr);
    }

    private static void initFile(Context mContext) {
        if (saveFile == null) {
//            saveFile = new File(Environment.getExternalStorageDirectory()+"/download/", "jianpei.apk");
            saveFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "jianpei.apk");
        }
        if (saveFile.exists()) {
            saveFile.delete();
        }

    }

    public static long downLoadApk(Context context, String url, String describeStr) {
        // 得到系统的下载管理
        DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        // 以下两行代码可以让下载的apk文件被直接安装而不用使用Fileprovider,系统7.0或者以上才启动。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder localBuilder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(localBuilder.build());
        }
        DownloadManager.Request requestApk = new DownloadManager.Request(uri);
        // 设置在什么网络下下载
        requestApk.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 下载中和下载完后都显示通知栏
        requestApk.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (saveFile.exists()) {    //判断文件是否存在，存在的话先删除
            saveFile.delete();
        }
        requestApk.setDestinationUri(Uri.fromFile(saveFile));
        // 表示允许MediaScanner扫描到这个文件，默认不允许。
//        requestApk.allowScanningByMediaScanner();
        // 设置下载中通知栏的提示消息
        requestApk.setTitle("建培教育更新下载");
        // 设置设置下载中通知栏提示的介绍
        requestApk.setDescription(describeStr);

        // 7.0以上的系统适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestApk.setRequiresDeviceIdle(false);
            requestApk.setRequiresCharging(false);
        }
        // 启动下载,该方法返回系统为当前下载请求分配的一个唯一的ID
        long downLoadId = manager.enqueue(requestApk);
        return downLoadId;
    }

    /**
     * 下载前先移除前一个任务，防止重复下载
     *
     * @param downloadId
     */
    public static void clearCurrentTask(Context mContext, long downloadId) {
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.remove(downloadId);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

//    public static void installApk(Context context) {
//        downloadId = 0;
////        try {
////            String[] command = {"chmod", "777", saveFile.getAbsolutePath()};
////            ProcessBuilder builder = new ProcessBuilder(command);
////            builder.start();
////        } catch (Exception ignored) {
////            ignored.printStackTrace();
////        }
//
//        if (!saveFile.exists()) {
//            return;
//        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, "com.jianpei.jpeducation.fileprovider", saveFile);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
//        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        MyApplication.getInstance().startActivity(intent);
//    }


    public static void installApk(Context context) {
        if (saveFile==null || !saveFile.exists()) {
            return;
        }
        //判读版本是否在7.0以上 todo 这里是7.0安装是会出现解析包的错误
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            // todo 在AndroidManifest中的android:authorities值  当前应用的包名：cn.xu.test+FileProvider（数据共享）
            Uri apkUri = FileProvider.getUriForFile(context,
                    "com.jianpei.jpeducation.fileprovider", saveFile);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);

        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }

    }
}
