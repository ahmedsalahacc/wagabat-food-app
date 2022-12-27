package com.ahmedsalah.wagabat.models;

public class CartModel {
    private float itemPrice;
    private String resturantName, itemName, img;
    private int qty;

    public CartModel(String itemName, String restaurantName, String img, int qty, float price) {
        this.itemPrice = price;
        this.resturantName = restaurantName;
        this.itemName = itemName;
        this.qty = qty;
        this.img = img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public String getResturantName() {
        return resturantName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
