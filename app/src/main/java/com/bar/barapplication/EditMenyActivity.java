package com.bar.barapplication;

import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bar.barapplication.adapters.EditProductsAdapter;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnProductsReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;
import java.util.Calendar;

public class EditMenyActivity extends AppCompatActivity implements OnProductsReceived, EditProductsView {

    private ListView listView;
    private FloatingActionButton add;
    private Context context;
    private EditProductsAdapter productsAdapter;
    private OnProductsReceived onProductsReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_menu_activity);
        context = this;
        onProductsReceived = this;
        listView = findViewById(R.id.product_list);
        add = findViewById(R.id.fab);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_product_dialog);
                dialog.setTitle("Add product");

                final EditText edit = dialog.findViewById(R.id.edit);
                final EditText price = dialog.findViewById(R.id.price);
                Button add = dialog.findViewById(R.id.add_dialog_button);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                        if (edit.getText() != null && !edit.getText().toString().isEmpty()) {
                            Product product = new Product();
                            product.setId((int) Calendar.getInstance().getTimeInMillis());
                            product.setName(edit.getText().toString());
                            product.setPrice( Double.parseDouble(price.getText().toString()));
                            WebManager.addProduct(product);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    WebManager.requestProducts(onProductsReceived);
                                }
                            }, 300);

                            dialog.dismiss();
                        }}
                        catch (Exception ex){

                        }

                    }
                });
                dialog.show();
            }
        });

        WebManager.requestProducts(this);

    }

    @Override
    public Context GetCallingContext() {
        return this;
    }

    @Override
    public void onAllProductsReceived(ArrayList<Product> products) {
        if(products!=null && !products.isEmpty()) {
            productsAdapter = new EditProductsAdapter(products, context, this);
            listView.setAdapter(productsAdapter);
        }
    }

    @Override
    public ListView getOrderedProductsList() {
        return null;
    }

    @Override
    public Button getOrderButton() {
        return null;
    }

    @Override
    public void destroy() {

    }
}