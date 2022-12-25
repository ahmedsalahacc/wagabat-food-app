package com.ahmedsalah.wagabat.models;

public class ResturantModel {
    private String name, categoryClass, imgAddress, id;
    private Float deliveryPrice, rating;
    private int deliveryTime;

    public ResturantModel(String id, String name, String categoryClass, Float rating, String imgAddress, Float deliveryPrice, int deliveryTime) {
        this.id = id;
        this.name = name;
        this.categoryClass = categoryClass;
        this.rating = rating;
        this.imgAddress = imgAddress;
        this.deliveryPrice = deliveryPrice;
        this.deliveryTime = deliveryTime;
    }
    public void setId(String id){this.id=id;}
    public String getId(){return this.id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryClass(String categoryClass) {
        this.categoryClass = categoryClass;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public void setDeliveryPrice(Float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getName() {
        return name;
    }

    public String getCategoryClass() {
        return categoryClass;
    }

    public Float getRating() {
        return rating;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public Float getDeliveryPrice() {
        return deliveryPrice;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
