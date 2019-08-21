package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order {

    @SerializedName("Id")
    private int orderId;
    @SerializedName("Number")
    private int orderNumber;
    @SerializedName("ClientName")
    private String clientName;
    @SerializedName("Status")
    private int status;
    @SerializedName("Details")
    private ArrayList<OrderDetail> details;
    @SerializedName("Price")
    private double price;

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
        ArrayList<Integer> productIds = new ArrayList<>();
        for (OrderDetail detail : details) {
            productIds.add(detail.getItemId());
        }
        Integer[] ret = new Integer[productIds.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = productIds.get(i);
        }

        return ret;
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

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetail> details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
