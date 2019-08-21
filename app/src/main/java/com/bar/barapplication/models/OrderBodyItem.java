package com.bar.barapplication.models;

import java.util.ArrayList;

public class OrderBodyItem {

    private int Id;
    private ArrayList<Integer> IngredientsIds;
    private String ExtraOptiuni;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getExtraOptiuni() {
        return ExtraOptiuni;
    }

    public void setExtraOptiuni(String extraOptiuni) {
        ExtraOptiuni = extraOptiuni;
    }

    public ArrayList<Integer> getIngredientsIds() {
        return IngredientsIds;
    }

    public void setIngredientsIds(ArrayList<Integer> ingredientsIds) {
        IngredientsIds = ingredientsIds;
    }
}
