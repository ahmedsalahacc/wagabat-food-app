package com.ahmedsalah.wagabat.models;

public class OrderHistoryItem {
    String orderID, orderDateTime;
    OrderModel.Status orderStatus;
    private String address;

    public OrderHistoryItem(String orderId, String orderDateTime, String address, OrderModel.Status orderStatus){
        this.orderID = orderId;
        this.orderDateTime = orderDateTime;
        this.orderStatus= orderStatus;
        this.address = address;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
