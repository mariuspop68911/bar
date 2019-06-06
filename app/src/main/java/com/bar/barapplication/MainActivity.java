package com.bar.barapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bar.barapplication.adapters.OrdersAdapter;
import com.bar.barapplication.models.Order;
import com.bar.barapplication.web.OnOrdersReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnOrdersReceived.OnAllOrdersReceived {

    private Context context;

    private ListView ordersList;
    private static OnOrdersReceived.OnAllOrdersReceived onAllOrdersReceived;
    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        onAllOrdersReceived = this;
        ordersList = findViewById(R.id.orders_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddOrderActivity.class);
                startActivity(intent);

            }
        });

        pool();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("NoComment Pizza");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditMenyActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAllOrdersReceived(ArrayList<Order> orders) {
        Utils utils = new Utils();
        if (orders != null && !utils.equalLists(orders, this.orders)) {
            this.orders = orders;
            Comparator<Order> comp = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return o2.getStatus() - o1.getStatus();
                }
            };

            Collections.sort(orders, comp);

            OrdersAdapter adapter = new OrdersAdapter(orders, context);
            ordersList.setAdapter(adapter);
            utils.playSound(context);
        }
    }

    private void pool() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                WebManager.requestOrders(onAllOrdersReceived);
            }
        };
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }
}
