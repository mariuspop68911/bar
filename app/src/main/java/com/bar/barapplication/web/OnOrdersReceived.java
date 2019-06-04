package com.bar.barapplication.web;

import com.bar.barapplication.models.Order;

import java.util.ArrayList;

public interface OnOrdersReceived {

    interface OnAllOrdersReceived {
        void onAllOrdersReceived(ArrayList<Order> orders);
    }

    interface OnOrdersStatusReceived {
        void onOrdersStatusReceived(ArrayList<Order> orders);
    }
}
