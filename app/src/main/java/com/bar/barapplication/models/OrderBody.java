package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderBody {

    @SerializedName("Items")
    private ArrayList<OrderBodyItem> orderBodyItems;
    @SerializedName("ClientName")
    private String ClientName;

    public ArrayList<OrderBodyItem> getOrderBodyItem() {
        return orderBodyItems;
    }

    public void setOrderBodyItem(ArrayList<OrderBodyItem> orderBodyItems) {
        this.orderBodyItems = orderBodyItems;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

}
