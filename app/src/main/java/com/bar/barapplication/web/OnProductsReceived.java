package com.bar.barapplication.web;

import com.bar.barapplication.models.Product;

import java.util.ArrayList;

public interface OnProductsReceived {

    void onAllProductsReceived(ArrayList<Product> products);
}
