package com.jianpei.jpeducation.utils.down;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/30
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public enum Status {

    /**
     * 空闲状态
     */
    Idle,
    /**
     * 准备状态
     */
    Prepare,
    /**
     * 等待状态
     */
    Wait,
    /**
     * 开始状态
     */
    Start,
    /**
     * 暂停状态
     */
    Stop,
    /**
     * 完成状态
     */
    Complete,
    /**
     * 错误状态
     */
    Error,
    /**
     * 删除状态
     */
    Delete,
    /**
     * 文件处理状态
     */
    File;

    private Status() {
    }
}
