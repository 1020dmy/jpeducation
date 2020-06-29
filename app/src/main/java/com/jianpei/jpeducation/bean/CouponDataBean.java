package com.jianpei.jpeducation.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CouponDataBean implements Parcelable {
    /**
     * pageData : {"pageIndex":"1","pageSize":"10","totalPage":0}
     * data : [{"id":"1675","title":"折扣券","describe":"二折","oid":"0","end_time_str":"2020-08-23 09:56:58","user_time_str":"1970年01月01"},{"id":"1674","title":"满减券","describe":"1","oid":"0","end_time_str":"2020-07-31 06:23:38","user_time_str":"1970年01月01"},{"id":"1673","title":"折扣券","describe":"二折","oid":"0","end_time_str":"2020-08-23 09:56:58","user_time_str":"1970年01月01"}]
     */

    private PageDataBean pageData;
    private List<CouponData> data;

    public PageDataBean getPageData() {
        return pageData;
    }

    public void setPageData(PageDataBean pageData) {
        this.pageData = pageData;
    }

    public List<CouponData> getData() {
        return data;
    }

    public void setData(List<CouponData> data) {
        this.data = data;
    }

    public static class PageDataBean implements Parcelable {
        /**
         * pageIndex : 1
         * pageSize : 10
         * totalPage : 0
         */

        private String pageIndex;
        private String pageSize;
        private int totalPage;

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.pageIndex);
            dest.writeString(this.pageSize);
            dest.writeInt(this.totalPage);
        }

        public PageDataBean() {
        }

        protected PageDataBean(Parcel in) {
            this.pageIndex = in.readString();
            this.pageSize = in.readString();
            this.totalPage = in.readInt();
        }

        public static final Creator<PageDataBean> CREATOR = new Creator<PageDataBean>() {
            @Override
            public PageDataBean createFromParcel(Parcel source) {
                return new PageDataBean(source);
            }

            @Override
            public PageDataBean[] newArray(int size) {
                return new PageDataBean[size];
            }
        };
    }

    public static class CouponData implements Parcelable {
        /**
         * id : 1675
         * title : 折扣券
         * describe : 二折
         * oid : 0
         * end_time_str : 2020-08-23 09:56:58
         * user_time_str : 1970年01月01
         */

        private String id;
        private String title;
        private String describe;
        private String oid;
        private String end_time_str;
        private String user_time_str;

        private String coupon_type;

        private String type;

        public String getCoupon_type() {
            return coupon_type;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

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

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getEnd_time_str() {
            return end_time_str;
        }

        public void setEnd_time_str(String end_time_str) {
            this.end_time_str = end_time_str;
        }

        public String getUser_time_str() {
            return user_time_str;
        }

        public void setUser_time_str(String user_time_str) {
            this.user_time_str = user_time_str;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.describe);
            dest.writeString(this.oid);
            dest.writeString(this.end_time_str);
            dest.writeString(this.user_time_str);
            dest.writeString(this.coupon_type);
            dest.writeString(this.type);
        }

        public CouponData() {
        }

        protected CouponData(Parcel in) {
            this.id = in.readString();
            this.title = in.readString();
            this.describe = in.readString();
            this.oid = in.readString();
            this.end_time_str = in.readString();
            this.user_time_str = in.readString();
            this.coupon_type = in.readString();
            this.type = in.readString();
        }

        public static final Creator<CouponData> CREATOR = new Creator<CouponData>() {
            @Override
            public CouponData createFromParcel(Parcel source) {
                return new CouponData(source);
            }

            @Override
            public CouponData[] newArray(int size) {
                return new CouponData[size];
            }
        };
    }




    /**
     * id : 1612
     * user_id : 2367
     * intergral : 200
     * type : 0
     * add_time : 1578919242
     * ux_time : 1581511242
     * date : null
     * uid : null
     * cid : 0
     * condition : 500
     * cat_id : 0
     * add_time_str : 2020年01月13
     * ux_time_str : 2020年02月12
     */

//    private String id;
//    private String user_id;
//    private String intergral;
//    private String type;
//    private String add_time;
//    private String ux_time;
//    private String date;
//    private String uid;
//    private String cid;
//    private String condition;
//    private String cat_id;
//    private String add_time_str;
//    private String ux_time_str;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getIntergral() {
//        return intergral;
//    }
//
//    public void setIntergral(String intergral) {
//        this.intergral = intergral;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAdd_time() {
//        return add_time;
//    }
//
//    public void setAdd_time(String add_time) {
//        this.add_time = add_time;
//    }
//
//    public String getUx_time() {
//        return ux_time;
//    }
//
//    public void setUx_time(String ux_time) {
//        this.ux_time = ux_time;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//    public String getCid() {
//        return cid;
//    }
//
//    public void setCid(String cid) {
//        this.cid = cid;
//    }
//
//    public String getCondition() {
//        return condition;
//    }
//
//    public void setCondition(String condition) {
//        this.condition = condition;
//    }
//
//    public String getCat_id() {
//        return cat_id;
//    }
//
//    public void setCat_id(String cat_id) {
//        this.cat_id = cat_id;
//    }
//
//    public String getAdd_time_str() {
//        return add_time_str;
//    }
//
//    public void setAdd_time_str(String add_time_str) {
//        this.add_time_str = add_time_str;
//    }
//
//    public String getUx_time_str() {
//        return ux_time_str;
//    }
//
//    public void setUx_time_str(String ux_time_str) {
//        this.ux_time_str = ux_time_str;
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.id);
//        dest.writeString(this.user_id);
//        dest.writeString(this.intergral);
//        dest.writeString(this.type);
//        dest.writeString(this.add_time);
//        dest.writeString(this.ux_time);
//        dest.writeString(this.date);
//        dest.writeString(this.uid);
//        dest.writeString(this.cid);
//        dest.writeString(this.condition);
//        dest.writeString(this.cat_id);
//        dest.writeString(this.add_time_str);
//        dest.writeString(this.ux_time_str);
//    }
//
//    public CouponDataBean() {
//    }
//
//    protected CouponDataBean(Parcel in) {
//        this.id = in.readString();
//        this.user_id = in.readString();
//        this.intergral = in.readString();
//        this.type = in.readString();
//        this.add_time = in.readString();
//        this.ux_time = in.readString();
//        this.date = in.readParcelable(String.class.getClassLoader());
//        this.uid = in.readParcelable(String.class.getClassLoader());
//        this.cid = in.readString();
//        this.condition = in.readString();
//        this.cat_id = in.readString();
//        this.add_time_str = in.readString();
//        this.ux_time_str = in.readString();
//    }
//
//    public static final Creator<CouponDataBean> CREATOR = new Creator<CouponDataBean>() {
//        @Override
//        public CouponDataBean createFromParcel(Parcel source) {
//            return new CouponDataBean(source);
//        }
//
//        @Override
//        public CouponDataBean[] newArray(int size) {
//            return new CouponDataBean[size];
//        }
//    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.pageData, flags);
        dest.writeTypedList(this.data);
    }

    public CouponDataBean() {
    }

    protected CouponDataBean(Parcel in) {
        this.pageData = in.readParcelable(PageDataBean.class.getClassLoader());
        this.data = in.createTypedArrayList(CouponData.CREATOR);
    }

    public static final Creator<CouponDataBean> CREATOR = new Creator<CouponDataBean>() {
        @Override
        public CouponDataBean createFromParcel(Parcel source) {
            return new CouponDataBean(source);
        }

        @Override
        public CouponDataBean[] newArray(int size) {
            return new CouponDataBean[size];
        }
    };
}
