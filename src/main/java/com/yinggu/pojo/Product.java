package com.yinggu.pojo;

public class Product {
    private int id;
    private String userName;
    private String productName;
    private String productPrice;
    private String productDetails;
    private String date;
    private String place;
    private String saveImgName1;
    private String saveImgName2;
    private String saveImgName3;
    private String savePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSaveImgName1() {
        return saveImgName1;
    }

    public void setSaveImgName1(String saveImgName1) {
        this.saveImgName1 = saveImgName1;
    }

    public String getSaveImgName2() {
        return saveImgName2;
    }

    public void setSaveImgName2(String saveImgName2) {
        this.saveImgName2 = saveImgName2;
    }

    public String getSaveImgName3() {
        return saveImgName3;
    }

    public void setSaveImgName3(String saveImgName3) {
        this.saveImgName3 = saveImgName3;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDetails='" + productDetails + '\'' +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", saveImgName1='" + saveImgName1 + '\'' +
                ", saveImgName2='" + saveImgName2 + '\'' +
                ", saveImgName3='" + saveImgName3 + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
