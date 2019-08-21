package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductByIdResponse {

    @SerializedName("Content")
    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
