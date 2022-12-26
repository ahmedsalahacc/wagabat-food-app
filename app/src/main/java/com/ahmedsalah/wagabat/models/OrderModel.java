package com.ahmedsalah.wagabat.models;

import android.os.Build;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.UUID;

public class OrderModel {

    public enum Status{
        CREATED,
        WAITING_FOR_APPROVAL,
        PREPARING,
        DELIVERING
    }

    private String resturantID;
    private String userID;
    private String id;
    private Status status;
    private ArrayList<Item> itemsList;
    private LocalDateTime datetime;

    public OrderModel(String resturantID, String userID){
        id = UUID.randomUUID().toString();
        status = Status.CREATED;
        this.userID = userID;
        this.resturantID = resturantID;
    }

    public void changeStatus(Status status){
        this.status = status;
    }

    public void confirm() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datetime = LocalDateTime.now();
        } else{
            throw new Exception("This datetime interface is not supported by "+Build.VERSION.SDK_INT);
        }
        changeStatus(Status.WAITING_FOR_APPROVAL);
    }

}
