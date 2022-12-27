package com.ahmedsalah.wagabat.models;

import java.util.UUID;

public class Item{
    private String id;
    private String dishId;
    private String resturantID;
    private String userID;
    String specialRequest;
    private int count;

    public Item(String dishId,
                int count,
                String resturantID,
                String userID,
                String specialRequest){
        id = UUID.randomUUID().toString();
        this.dishId = dishId;
        this.count = count;
        this.resturantID = resturantID;
        this.userID = userID;
        this.specialRequest = specialRequest;
    }

    @Override
    public String toString(){
        return "Item["+this.id+"]";
    }

    public String getId() {
        return id;
    }

    public String getDishId() {
        return dishId;
    }

    public String getResturantID() {
        return resturantID;
    }

    public String getUserID() {
        return userID;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public int getCount() {
        return count;
    }
}