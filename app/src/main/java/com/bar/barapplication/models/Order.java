package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Id")
    private int orderId;
    @SerializedName("Number")
    private int orderNumber;
    @SerializedName("MenuItems")
    private Integer[] productIds;
    @SerializedName("ClientName")
    private String clientName;
    @SerializedName("Status")
    private int status;
    @SerializedName("Details")
    private OrderDetail[] details;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Integer[]productIds) {
        this.productIds = productIds;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderDetail[] getDetails() {
        return details;
    }

    public void setDetails(OrderDetail[] details) {
        this.details = details;
    }
}
