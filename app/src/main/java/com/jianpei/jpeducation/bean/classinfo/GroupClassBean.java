package com.jianpei.jpeducation.bean.classinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupClassBean implements Parcelable {


    /**
     * id : 98
     * title : 建筑工程全科
     * type : 2
     * is_material : 1
     * price : 1880.00
     */

    private String id;
    private String title;
    private int type;
    private int is_material;
    private String price;

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIs_material() {
        return is_material;
    }

    public void setIs_material(int is_material) {
        this.is_material = is_material;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.is_material);
        dest.writeString(this.price);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    public GroupClassBean() {
    }

    protected GroupClassBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.is_material = in.readInt();
        this.price = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GroupClassBean> CREATOR = new Parcelable.Creator<GroupClassBean>() {
        @Override
        public GroupClassBean createFromParcel(Parcel source) {
            return new GroupClassBean(source);
        }

        @Override
        public GroupClassBean[] newArray(int size) {
            return new GroupClassBean[size];
        }
    };
}
