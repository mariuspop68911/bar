package com.bar.barapplication;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bar.barapplication.adapters.KitchenOrdersAdapter;
import com.bar.barapplication.models.Order;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnOrdersReceived;
import com.bar.barapplication.web.OnProductsReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KitchenActivity extends AppCompatActivity implements OnOrdersReceived.OnOrdersStatusReceived, OnProductsReceived {

    private Context context;
    private ListView ordersList;
    private static OnOrdersReceived.OnOrdersStatusReceived onOrdersStatusReceived;
    private ArrayList<Product> products;
    private KitchenOrdersAdapter adapter;
    private TextView emptyElement;
    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        context = this;
        onOrdersStatusReceived = this;
        ordersList = findViewById(R.id.kitchen_orders_list);
        emptyElement = findViewById(R.id.emptyElement);
        WebManager.requestProducts(this);
    }

    private void pool() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                WebManager.requestOrdersByStatus(onOrdersStatusReceived, Constants.ORDER_CREATED);
            }
        };
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void onOrdersStatusReceived(ArrayList<Order> orders) {

        if (orders != null && !orders.isEmpty()) {
            Utils utils = new Utils();
            if (!new Utils().equalLists(orders, this.orders)) {
                this.orders = orders;
                adapter = new KitchenOrdersAdapter(orders, context, products);
                ordersList.setAdapter(adapter);
                ordersList.setVisibility(View.VISIBLE);
                emptyElement.setVisibility(View.GONE);
                utils.playSound(context);
            }
        } else {
            emptyElement.setVisibility(View.VISIBLE);
            ordersList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAllProductsReceived(ArrayList<Product> products) {
        this.products = products;
        pool();
    }
}
