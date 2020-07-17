package com.jianpei.jpeducation.bean.school;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/17
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ThreadInfoBean {


    /**
     * user_img : /Source/pc/images/shiimg/a15.png
     * id : 1
     * user_id : 1496
     * article_id : 0
     * status : 1
     * title : 标题
     * content : 内容内容内容内容内容内容内容内容内容内容
     * pic :
     * is_hot : 0
     * post_num : 0
     * like_num : 0
     * view_num : 0
     * updated_at : 0
     * created_at : 0
     * created_at_str : 2020-1-1
     * is_del : 0
     * remind_id : 1,8,16
     * is_post : 0
     * is_praise : 0
     * is_attention :
     * is_my_thread : 0
     */

    private String user_img;
    private String id;
    private String user_id;
    private String article_id;
    private String status;
    private String title;
    private String content;
    private String pic;
    private String is_hot;
    private String post_num;
    private String like_num;
    private String view_num;
    private String updated_at;
    private String created_at;
    private String created_at_str;
    private String is_del;
    private String remind_id;
    private String is_post;
    private String is_praise;
    private String is_attention;
    private String is_my_thread;

    private List<AttentionBean> users;
    private List<TopicBean> topics;

    public List<AttentionBean> getUsers() {
        return users;
    }

    public void setUsers(List<AttentionBean> users) {
        this.users = users;
    }

    public List<TopicBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicBean> topics) {
        this.topics = topics;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getLike_num() {
        return like_num;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at_str() {
        return created_at_str;
    }

    public void setCreated_at_str(String created_at_str) {
        this.created_at_str = created_at_str;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(String remind_id) {
        this.remind_id = remind_id;
    }

    public String getIs_post() {
        return is_post;
    }

    public void setIs_post(String is_post) {
        this.is_post = is_post;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(String is_attention) {
        this.is_attention = is_attention;
    }

    public String getIs_my_thread() {
        return is_my_thread;
    }

    public void setIs_my_thread(String is_my_thread) {
        this.is_my_thread = is_my_thread;
    }


}
