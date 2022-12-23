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

    public class Item{
        public DishModel dish;
        public int count;

        public Item(DishModel dish, int count){
            this.dish = dish;
            this.count = count;
        }
    }

    public class DishesHolder{
        ArrayList<Item> dishItems;

        public DishesHolder(){
            dishItems = new ArrayList<>();
        }

        public void addDish(DishModel dish, int count){
            dishItems.add(new Item(dish, count));
        }

        public Item getDish(int idx){
            return dishItems.get(idx);
        }
    }
    private String resturantID;
    private String userID;
    private String id;
    private Status status;
    private DishesHolder dishItemsHolder;
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
