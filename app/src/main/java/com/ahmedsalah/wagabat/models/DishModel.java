package com.ahmedsalah.wagabat.models;

public class DishModel {
    private String name, imgAddress, description;
    private Float price;

    public DishModel(String name, String imgAddress, String description, Float price) {
        this.name = name;
        this.imgAddress = imgAddress;
        this.description = description;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }
}
