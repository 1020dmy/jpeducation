package com.jianpei.jpeducation.utils;


import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class FileUtils {


//    public static String headPath = "";


    public static File creatHeadPath(String fileName) throws Exception {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.delete();
        }
        file.createNewFile();
//        headPath = file.getAbsolutePath();
        return file;
    }


    /**
     * 创建文件
     *
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName + fileName);
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     *
     * @param fileName
     * @return
     */
    public static boolean deleteDirectory(String fileName) {
        boolean status;
        SecurityManager checker = new SecurityManager();

        if (!fileName.equals("")) {

            File path = Environment.getExternalStorageDirectory();
            File newPath = new File(path.toString() + fileName);
            checker.checkDelete(newPath.toString());
            if (newPath.isDirectory()) {
                String[] listfile = newPath.list();
                // delete all files within the specified directory and then
                // delete the directory
                try {
                    for (int i = 0; i < listfile.length; i++) {
                        File deletedFile = new File(newPath.toString() + "/"
                                + listfile[i].toString());
                        deletedFile.delete();
                    }
                    newPath.delete();
                    Log.i("deleteDirectory", fileName);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;
                }

            } else
                status = false;
        } else
            status = false;
        return status;
    }

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }


}
