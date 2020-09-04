package com.mantis.imview.common;

import android.Manifest;

public class MantisCommon {
    public static boolean isDebug;
    public static String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    public static final int REQ_CODE_PERMISSION = 0X1;
    public static String TAG = "Mantis_IM";

    //区分发送还是接收的消息
    public static final int SEND = 0X100;
    public static final int RECEIVE = 0X200;
    public static final int SYSTEM = 0X300;
    //网络状态
    public static final int WIFI = 0X400;
    public static final int CONNECT = 0X500;
    public static final int DISCONNECT = 0X600;

    //发送消息类型
    public static final String TEXT = "text";
    public static final String IMG = "IMG";
    /**
     * 消息状态
     *
     * 重新发送
     */
    public static final int MSG_STATUS_RESEND = 1;
    /**
     * 消息发送成功状态
     */
    public static final int MSG_STATUS_SEND_SUCCESS = 2;
    /**
     * 消息发送失败状态
     */
    public static final int MSG_STATUS_SEND_FAIL = 3;

    /**
     * title 状态
     *
     * 默认状态
     */
    public static final int TITLE_STATUS_DEFAULT = 4;
    /**
     * 输入中
     */
    public static final int TITLE_STATUS_INPUTTING = 5;
    /**
     * 转接中
     */
    public static final int TITLE_STATUS_GOTOTRANSFER = 6;
    /**
     * 转接
     */
    public static final int TITLE_STATUS_TRANSFER = 7;
}
