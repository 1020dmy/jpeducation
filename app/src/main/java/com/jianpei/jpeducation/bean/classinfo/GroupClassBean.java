package com.jianpei.jpeducation.bean.classinfo;

public class GroupClassBean {


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
}
