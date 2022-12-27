package com.ahmedsalah.wagabat.utils;

import com.ahmedsalah.wagabat.models.Item;

import java.util.ArrayList;

/**
 * responsible for managing the cart activities by
 * following a singleton design
 * */
public class OrdersCart {
    private static OrdersCart instance;
    private ArrayList<Item> orderItems;
    private String currentResturantId;

    private OrdersCart(){
        orderItems = new ArrayList<>();
    }

    /**
     * gets a shared static of the OrdersCart class
     * @return instance of the OrdersCart class
     * */
    synchronized public static OrdersCart getInstance(){
        if (instance==null)
            instance = new OrdersCart();
        return instance;
    }

    /**
     * gets the orders saved in the cart
     * @return orders saved in the cart
     * */
    synchronized public ArrayList<Item> getOrders(){
        return this.orderItems;
    }

    /**
     *  add orderItem to the cart
     * @param orderItem orderItem to add
     */
    synchronized public void addOrder(Item orderItem){
        this.orderItems.add(orderItem);
    }

    synchronized public void setCurrentResturantId(String id){this.currentResturantId = id;}
    synchronized  public String getCurrentResturantId(){return this.currentResturantId;}

    synchronized public void reset(){
        this.orderItems.clear();
    }
}
