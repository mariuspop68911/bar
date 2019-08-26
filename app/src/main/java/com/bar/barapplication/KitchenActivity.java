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
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class KitchenActivity extends AppCompatActivity implements OnOrdersReceived.OnAllOrdersReceived, OnProductsReceived {

    private Context context;
    private ListView ordersList;
    private static OnOrdersReceived.OnAllOrdersReceived onOrdersReceived;
    private ArrayList<Product> products;
    private KitchenOrdersAdapter adapter;
    private TextView emptyElement;
    private ArrayList<Order> orders;
    ScheduledFuture<?> scheduledFuture;
    boolean productsReceived = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        AppUpdater appUpdater = new AppUpdater(this)
                .setUpdateJSON("https://www.nivasoft.ro/downloads/appUpdate.json")
                .setUpdateFrom(UpdateFrom.JSON);

        appUpdater.start();
        context = this;
        onOrdersReceived = this;
        ordersList = findViewById(R.id.kitchen_orders_list);
        emptyElement = findViewById(R.id.emptyElement);
        WebManager.requestProducts(this);
    }

    protected void onPause() {
        super.onPause();
        pool(false);
    }
    protected void onResume() {
        super.onResume();
        if (productsReceived)
            pool(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pool(false);
    }

    private void pool(boolean x) {
        if (x){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                WebManager.requestOrders(onOrdersReceived);
            }
        };
            scheduledFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, Constants.WEB_API_INTERVAL, TimeUnit.SECONDS);}
        else{
            scheduledFuture.cancel(true);
        }
    }

    @Override
    public void onAllProductsReceived(ArrayList<Product> products) {
        this.products = products;
        productsReceived = true;
        pool(true);
    }

    @Override
    public Context GetCallingContext() {
        return this;
    }

    @Override
    public void onAllOrdersReceived(ArrayList<Order> orders) {
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
}
