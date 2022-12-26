package com.ahmedsalah.wagabat.models;

import java.util.UUID;

public class Item{
    private String id;
    private String dishId;
    private String resturantID;
    private String userID;
    String specialRequest;
    private int count;

    public Item(String dishId, int count, String resturantID, String userID, String specialRequest){
        id = UUID.randomUUID().toString();
        this.dishId = dishId;
        this.count = count;
        this.resturantID = resturantID;
        this.userID = userID;
        this.specialRequest = specialRequest;
    }
}