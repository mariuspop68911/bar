package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Product {

    @SerializedName("Id")
    private int id;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private double price;
    private int quantity;
    @SerializedName("Ingredients")
    private ArrayList<Ingredient> ingredients;
    private String mesaj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }
}
