package com.bar.barapplication.web;

import android.content.Context;

import com.bar.barapplication.models.Product;

import java.util.ArrayList;

public interface OnProductsReceived {
    Context GetCallingContext();
    void onAllProductsReceived(ArrayList<Product> products);
}
