package com.bar.barapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddPizzaIngredientBody {

    @SerializedName("ItemId")
    private int id;
    @SerializedName("IngredientIds")
    private ArrayList<Integer> ingredientIds;

    public AddPizzaIngredientBody() {
    }

    public AddPizzaIngredientBody(int id, ArrayList<Integer> ingredientIds) {
        this.id = id;
        this.ingredientIds = ingredientIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(ArrayList<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
