package com.bar.barapplication.web;

import com.bar.barapplication.models.Ingredient;

import java.util.ArrayList;

public interface OnIngredientsReceived {

    void onAllIngredientsReceived(ArrayList<Ingredient> ingredients);
}
