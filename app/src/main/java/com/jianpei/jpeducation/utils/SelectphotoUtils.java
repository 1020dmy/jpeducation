package com.jianpei.jpeducation.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SelectphotoUtils {


    private WeakReference<Activity> weakReference;


    public static final int REQUEST_TAKE_PHOTO = 0;// 拍照
    public static final int REQUEST_CROP = 1;// 裁剪
    public static final int SCAN_OPEN_PHONE = 2;// 相册


    //    public Uri imgUri; // 拍照时返回的uri
    public Uri mCutUri;// 图片裁剪时返回的uri
    public File imgFile;// 拍照保存的图片文件


    public SelectphotoUtils(Activity activity) {
        weakReference = new WeakReference<>(activity);

    }


    /**
     * 拍照
     **/
    public void takePhone() {
        // 要保存的文件名
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = "photo_" + time;
        // 创建一个文件夹
        String path = weakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/take_photo";

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 要保存的图片文件
        imgFile = new File(file, fileName + ".jpeg");
        // 将file转换成uri
        // 注意7.0及以上与之前获取的uri不一样了，返回的是provider路径
        Uri imgUri = getUri(imgFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 添加Uri读取权限
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        // 添加图片保存位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        weakReference.get().startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    /**
     * 裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent

        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);

        // 设置裁剪区域的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 设置裁剪区域的宽度和高度
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // 取消人脸识别
//        intent.putExtra("noFaceDetection", true);
        // 图片输出格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 指定裁剪完成以后的图片所保存的位置,pic info显示有延时
//        if (fromCapture) {
//            // 如果是使用拍照，那么原先的uri和最终目标的uri一致,注意这里的uri必须是Uri.fromFile生成的
//            mCutUri = Uri.fromFile(imgFile);
//        } else { // 从相册中选择，那么裁剪的图片保存在take_photo中
//            String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
//            String fileName = "photo_" + time;
//            File mCutFile = new File(weakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "", fileName + ".jpeg");
//
//            if (!mCutFile.getParentFile().exists()) {
//                mCutFile.getParentFile().mkdirs();
//            }
//            mCutUri = Uri.fromFile(mCutFile);
//        }
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = "photo_" + time;
        File mCutFile = new File(weakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "", fileName + ".jpeg");

        if (!mCutFile.getParentFile().exists()) {
            mCutFile.getParentFile().mkdirs();
        }
        mCutUri = Uri.fromFile(mCutFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        Toast.makeText(weakReference.get(), "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
//        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        intentBc.setData(uri);
//        weakReference.get().sendBroadcast(intentBc);

        weakReference.get().startActivityForResult(intent, REQUEST_CROP); //设置裁剪参数显示图片至ImageVie
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        weakReference.get().startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    public Uri getUri(File file) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            return Uri.fromFile(file);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            return FileProvider.getUriForFile(weakReference.get(), "com.jianpei.jpeducation.fileprovider", file);
        }
    }

    public void Release() {
        weakReference.clear();
    }

}
