package com.bar.barapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bar.barapplication.adapters.ProductsAdapter;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnProductsReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;

public class AddOrderActivity extends AppCompatActivity implements OnProductsReceived, AddOrderView {

    private ListView listView;
    private ListView orderedProductsList;
    private Button orderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_activity);

        listView = findViewById(R.id.product_list);
        orderedProductsList = findViewById(R.id.ordered_list);
        orderButton = findViewById(R.id.order_button);

        WebManager.requestProducts(this);

    }

    @Override
    public void onAllProductsReceived(ArrayList<Product> products) {
        if(products!= null && !products.isEmpty()) {
            ProductsAdapter productsAdapter = new ProductsAdapter(products, this, this);
            listView.setAdapter(productsAdapter);
        }
    }

    @Override
    public ListView getOrderedProductsList() {
        return orderedProductsList;
    }

    @Override
    public Button getOrderButton() {
        return orderButton;
    }

    @Override
    public void destroy() {
        finish();
    }
}