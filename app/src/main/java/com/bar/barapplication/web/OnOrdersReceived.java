package com.bar.barapplication.web;

import android.content.Context;

import com.bar.barapplication.models.Order;

import java.util.ArrayList;

public interface OnOrdersReceived {

    interface OnAllOrdersReceived {
        Context GetCallingContext();
        void onAllOrdersReceived(ArrayList<Order> orders);
    }

    interface OnOrdersStatusReceived {
        void onOrdersStatusReceived(ArrayList<Order> orders);
    }
}
