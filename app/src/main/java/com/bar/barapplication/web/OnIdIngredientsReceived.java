package com.bar.barapplication.web;

import com.bar.barapplication.models.Ingredient;

import java.util.ArrayList;

public interface OnIdIngredientsReceived {

    void onIdIngredientsReceived(ArrayList<Ingredient> ingredients);
}
