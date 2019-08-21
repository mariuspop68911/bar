package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String nume;
    @SerializedName("Price")
    private double price;
    private boolean selected;

    public Ingredient(String nume, double price) {
        this.nume = nume;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
