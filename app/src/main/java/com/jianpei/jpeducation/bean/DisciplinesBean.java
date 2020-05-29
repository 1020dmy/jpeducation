package com.jianpei.jpeducation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DisciplinesBean {


    /**
     * id : 197
     * catname : 勘察设计类
     * sublevel : [{"id":"198","catname":"给排水工程师","sublevel":[{"id":"142","catname":"给水排水专业基础"},{"id":"138","catname":"给水排水专业"}]},{"id":"199","catname":" 电气工程师","sublevel":[{"id":"140","catname":"电气专业基础"},{"id":"127","catname":"电气专业"}]},{"id":"200","catname":"结构工程师","sublevel":[{"id":"141","catname":"结构专业基础"},{"id":"137","catname":"结构专业"}]},{"id":"201","catname":"岩土工程师","sublevel":[{"id":"143","catname":"岩土专业基础"}]},{"id":"202","catname":"暖通工程师","sublevel":[{"id":"163","catname":"暖通专业"}]}]
     */

    private String id;
    private String catname;
    private ArrayList<SublevelBeanX> sublevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public ArrayList<SublevelBeanX> getSublevel() {
        return sublevel;
    }

    public void setSublevel(ArrayList<SublevelBeanX> sublevel) {
        this.sublevel = sublevel;
    }

    public static class SublevelBeanX {
        /**
         * id : 198
         * catname : 给排水工程师
         * sublevel : [{"id":"142","catname":"给水排水专业基础"},{"id":"138","catname":"给水排水专业"}]
         */

        private String id;
        private String catname;
        private List<SublevelBean> sublevel;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCatname() {
            return catname;
        }

        public void setCatname(String catname) {
            this.catname = catname;
        }

        public List<SublevelBean> getSublevel() {
            return sublevel;
        }

        public void setSublevel(List<SublevelBean> sublevel) {
            this.sublevel = sublevel;
        }

        public static class SublevelBean {
            /**
             * id : 142
             * catname : 给水排水专业基础
             */

            private String id;
            private String catname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCatname() {
                return catname;
            }

            public void setCatname(String catname) {
                this.catname = catname;
            }
        }
    }
}
