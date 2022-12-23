package com.ahmedsalah.wagabat.utils;

import com.ahmedsalah.wagabat.models.OrderModel;
import java.util.ArrayList;

/**
 * responsible for managing the cart activities by
 * following a singleton design
 * */
public class OrdersCart {
    private static OrdersCart instance;
    private ArrayList<OrderModel> orders;

    private OrdersCart(){
        orders = new ArrayList<>();
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
    synchronized public ArrayList<OrderModel> getOrders(){
        return this.orders;
    }

    /**
     * adds an order to the cart
     * @param order order to add
     * */
    synchronized public void addOrder(OrderModel order){
        this.orders.add(order);
    }
}
