package com.bar.barapplication.models;

import java.util.ArrayList;

public class OrderBody {

    private ArrayList<Integer> ItemIds = new ArrayList<>();
    private String ClientName;

    public ArrayList<Integer> getItemIds() {
        return ItemIds;
    }

    public void setItemIds(ArrayList<Integer> itemIds) {
        ItemIds = itemIds;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
