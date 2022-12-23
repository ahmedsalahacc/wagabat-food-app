package com.ahmedsalah.wagabat.models;

public class DishModel {
    private String name, imgAddress, description, id;
    private Float price;

    public DishModel(String id, String name, String imgAddress, String description, Float price) {
        this.id = id;
        this.name = name;
        this.imgAddress = imgAddress;
        this.description = description;
        this.price = price;
    }
    public void setID(String id){this.id = id;
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

    public String getID(){return this.id;}

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
