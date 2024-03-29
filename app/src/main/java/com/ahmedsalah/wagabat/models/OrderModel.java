package com.ahmedsalah.wagabat.models;

import android.os.Build;

import com.ahmedsalah.wagabat.activities.CartActivity;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.UUID;

public class OrderModel {

    public enum Status{
        CREATED,
        WAITING_FOR_APPROVAL,
        PREPARING,
        DELIVERED
    }

    private String resturantID;
    private String userID;
    private String id;
    private Status status;
    private ArrayList<Item> itemsList;
    private LocalDateTime datetime;
    CartActivity.DeliveryGate deliveryGate;
    CartActivity.DeliveryTime deliveryTime;

    public OrderModel(String resturantID, String userID){
        id = UUID.randomUUID().toString();
        status = Status.CREATED;
        this.userID = userID;
        this.resturantID = resturantID;
    }

    public void setDeliveryGate(CartActivity.DeliveryGate deliveryGate) {
        this.deliveryGate = deliveryGate;
    }

    public void setDeliveryTime(CartActivity.DeliveryTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public CartActivity.DeliveryGate getDeliveryGate() {
        return deliveryGate;
    }

    public CartActivity.DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setResturantID(String resturantID) {
        this.resturantID = resturantID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getResturantID() {
        return resturantID;
    }

    public String getUserID() {
        return userID;
    }

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public static String getStringForStatus(OrderModel.Status status){
        switch(status){
            case WAITING_FOR_APPROVAL:
                return "Waiting for approval";
            case PREPARING:
                return "Preparing";
            case DELIVERED:
                return "Delivered";
            default:
                return "created";
        }
    }

    public static Status mapNumberToStatusEnum(int num){
        switch (num){
            case 1:
                return Status.WAITING_FOR_APPROVAL;
            case 2:
                return Status.PREPARING;
            case 3:
                return Status.DELIVERED;
            default:
                return Status.CREATED;
        }
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
