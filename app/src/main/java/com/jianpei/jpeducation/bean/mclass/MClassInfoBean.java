package com.jianpei.jpeducation.bean.mclass;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MClassInfoBean {
    /**
     * viod_info : {"id":"1507","cid":"52","title":"冲刺预测01","url":"https://ke.jianpei.com.cn/64c715177d434ec7853e634d376259e1/93d643868a4f41088e049b27d338587a-e37c150e4b25f701b948d7b7d0baffcb-od-S00000001-100000.m3u8","video_id":"64c715177d434ec7853e634d376259e1","chapter_id":"236"}
     * teachers : [{"id":"49","title":" 王老师"}]
     * directorys : [{"id":"236","title":"冲刺预测班","viods":[{"id":"1507","title":"冲刺预测01","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1508","title":"冲刺预测02","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1509","title":"冲刺预测03","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1510","title":"冲刺预测04","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1511","title":"冲刺预测05","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1512","title":"冲刺预测06","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1513","title":"冲刺预测07","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1514","title":"冲刺预测08","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1515","title":"冲刺预测09","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1516","title":"冲刺预测10","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1517","title":"冲刺预测11","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1518","title":"冲刺预测12","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1519","title":"冲刺预测13","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1520","title":"冲刺预测14","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1521","title":"冲刺预测15","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1522","title":"冲刺预测16","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1523","title":"冲刺预测17","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1524","title":"冲刺预测18","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1525","title":"冲刺预测19","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"},{"id":"1526","title":"冲刺预测20","isfree":"0","chapter_id":"236","dqtime":null,"totaltime":null,"is_last_read":"0"}],"schedule":"0"},{"id":"235","title":"答题技巧班","viods":[],"schedule":"0"},{"id":"233","title":"习题解析班","viods":[],"schedule":"0"},{"id":"232","title":"基础精讲班","viods":[],"schedule":"0"},{"id":"277","title":"备考导学班","viods":[],"schedule":"0"},{"id":"496","title":"2019习题解析","viods":[],"schedule":"0"},{"id":"495","title":"2019冲刺预测","viods":[],"schedule":"0"},{"id":"494","title":"2019基础精讲","viods":[],"schedule":"0"},{"id":"493","title":"2019备考导学","viods":[],"schedule":"0"},{"id":"1465","title":"2020冲刺预测","viods":[],"schedule":"0"},{"id":"1464","title":"2020真题解析","viods":[],"schedule":"0"},{"id":"1463","title":"2020基础精讲","viods":[],"schedule":"0"},{"id":"1462","title":"2020备考导学","viods":[],"schedule":"0"}]
     */

    private ViodInfoBean viod_info;
    private List<TeachersBean> teachers;
    private List<DirectoryBean> directorys;

    public ViodInfoBean getViod_info() {
        return viod_info;
    }

    public void setViod_info(ViodInfoBean viod_info) {
        this.viod_info = viod_info;
    }

    public List<TeachersBean> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeachersBean> teachers) {
        this.teachers = teachers;
    }

    public List<DirectoryBean> getDirectorys() {
        return directorys;
    }

    public void setDirectorys(List<DirectoryBean> directorys) {
        this.directorys = directorys;
    }

    public static class ViodInfoBean {
        /**
         * id : 1507
         * cid : 52
         * title : 冲刺预测01
         * url : https://ke.jianpei.com.cn/64c715177d434ec7853e634d376259e1/93d643868a4f41088e049b27d338587a-e37c150e4b25f701b948d7b7d0baffcb-od-S00000001-100000.m3u8
         * video_id : 64c715177d434ec7853e634d376259e1
         * chapter_id : 236
         */

        private String id;
        private String cid;
        private String title;
        private String url;
        private String video_id;
        private String chapter_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }
    }

    public static class TeachersBean {
        /**
         * id : 49
         * title :  王老师
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
