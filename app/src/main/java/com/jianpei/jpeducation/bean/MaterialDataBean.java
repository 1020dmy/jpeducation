package com.jianpei.jpeducation.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialDataBean {


    /**
     * pageData : {"page_index":1,"page_size":16,"total_page":1}
     * data : [{"title":"给水工程 冲刺班","id":"357"},{"title":"给水工程 真题班","id":"356"}]
     */

    private PageDataBean pageData;
    private List<MaterialTitle> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<MaterialTitle> getData() {
        return data;
    }

    public void setData(List<MaterialTitle> data) {
        this.data = data;
    }

    public static class PageDataBean {
        /**
         * page_index : 1
         * page_size : 16
         * total_page : 1
         */

        private int page_index;
        private int page_size;
        private int total_page;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }
    }

//    public static class MaterialTitle extends BaseExpandNode {
//
//        /**
//         * title : 给水工程 冲刺班
//         * id : 357
//         */
//
//
//        private ArrayList<BaseNode> materialInfoBeans;
//
//        private String title;
//        private String id;
//
//        public MaterialTitle() {
//            setExpanded(false);
//            if(materialInfoBeans==null)
//                materialInfoBeans=new ArrayList<>();
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        @Nullable
//        @Override
//        public List<BaseNode> getChildNode() {
//            return materialInfoBeans;
//        }
//
//    }

    public static class MaterialTitle {

        /**
         * title : 给水工程 冲刺班
         * id : 357
         */


        private ArrayList<MaterialInfoBean> materialInfoBeans;

        private String title;
        private String id;
        private boolean isUnfold = false;

        public boolean isUnfold() {
            return isUnfold;
        }

        public void setUnfold(boolean unfold) {
            isUnfold = unfold;
        }

        public ArrayList<MaterialInfoBean> getMaterialInfoBeans() {
            return materialInfoBeans;
        }

        public void setMaterialInfoBeans(ArrayList<MaterialInfoBean> materialInfoBeans) {
            this.materialInfoBeans = materialInfoBeans;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }
}
