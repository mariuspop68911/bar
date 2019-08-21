package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class AddIngredientBody {

    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private double price;

    public AddIngredientBody(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
