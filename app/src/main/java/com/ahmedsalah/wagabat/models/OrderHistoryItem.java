package com.ahmedsalah.wagabat.models;

public class OrderHistoryItem {
    String orderID, orderDateTime;
    OrderModel.Status orderStatus;

    public OrderHistoryItem(String orderId, String orderDateTime, OrderModel.Status orderStatus){
        this.orderID = orderId;
        this.orderDateTime = orderDateTime;
        this.orderStatus= orderStatus;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public void setOrderStatus(OrderModel.Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public OrderModel.Status getOrderStatus() {
        return orderStatus;
    }
}
