package com.ahmedsalah.wagabat.models;

import com.ahmedsalah.wagabat.activities.CartActivity;

public class OrderHistoryItem {
    String orderID;
    CartActivity.DeliveryTime deliveryTime;
    CartActivity.DeliveryGate deliveryLocation;
    String datetime;
    OrderModel.Status orderStatus;

    public OrderHistoryItem(String orderId,
                            CartActivity.DeliveryTime deliveryTime,
                            CartActivity.DeliveryGate deliveryLocation,
                            OrderModel.Status orderStatus, String datetime){
        this.orderID = orderId;
        this.deliveryTime = deliveryTime;
        this.deliveryLocation = deliveryLocation;
        this.orderStatus= orderStatus;
        this.datetime = datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDeliveryTime(CartActivity.DeliveryTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryLocation(CartActivity.DeliveryGate deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public CartActivity.DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    public CartActivity.DeliveryGate getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public void setOrderStatus(OrderModel.Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }


    public OrderModel.Status getOrderStatus() {
        return orderStatus;
    }


}
