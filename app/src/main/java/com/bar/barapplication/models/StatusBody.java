package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class StatusBody {

    @SerializedName("OrderStatus")
    private int orderStatus;
    @SerializedName("Id")
    private int id;

    public StatusBody(int orderStatus, int id) {
        this.orderStatus = orderStatus;
        this.id = id;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
