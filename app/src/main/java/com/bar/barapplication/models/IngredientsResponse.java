package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IngredientsResponse {

    @SerializedName("Content")
    private ArrayList<Ingredient> ingredients;

    @SerializedName("ErrorCode")
    private int errorCode;

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
