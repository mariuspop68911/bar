package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("ItemId")
    private int itemId;
    @SerializedName("Count")
    private int count;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int orderNumber) {
        this.count = count;
    }
}