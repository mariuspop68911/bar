package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

public class DeleteProductBody {

    @SerializedName("Id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
