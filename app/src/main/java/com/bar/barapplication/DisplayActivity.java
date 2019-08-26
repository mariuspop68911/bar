package com.bar.barapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.bar.barapplication.adapters.DisplayAdapter;
import com.bar.barapplication.models.Order;
import com.bar.barapplication.web.OnOrdersReceived;
import com.bar.barapplication.web.WebManager;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DisplayActivity extends AppCompatActivity implements OnOrdersReceived.OnOrdersStatusReceived {

    private Context context;
    ScheduledFuture<?> scheduledFuture;
    private ListView ordersList;
    private static OnOrdersReceived.OnOrdersStatusReceived onAllOrdersReceived;
    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        context = this;
        onAllOrdersReceived = this;
        ordersList = findViewById(R.id.display_orders_list);
        AppUpdater appUpdater = new AppUpdater(this)
                .setUpdateJSON("https://www.nivasoft.ro/downloads/appUpdate.json")
                .setUpdateFrom(UpdateFrom.JSON);

        appUpdater.start();
        //pool(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pool(false);
    }
    protected void onResume() {
        super.onResume();
        pool(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pool(false);
    }

    private void pool(boolean x) {
        if (x) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    WebManager.requestOrdersByStatus(onAllOrdersReceived, Constants.ORDER_READY);
                }
            };
            scheduledFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, Constants.WEB_API_INTERVAL, TimeUnit.SECONDS);
        }
        else{
            scheduledFuture.cancel(true);
        }
    }

    @Override
    public void onOrdersStatusReceived(ArrayList<Order> orders) {
        Utils utils = new Utils();
        if (orders != null && !utils.equalLists(orders, this.orders)) {
            this.orders = orders;

            Comparator<Order> comp = new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return o2.getOrderNumber() - o1.getOrderNumber();
                }
            };

            Collections.sort(orders, comp);

            DisplayAdapter adapter = new DisplayAdapter(orders, context);
            ordersList.setAdapter(adapter);
            utils.playSound(context);
        }
    }
}
