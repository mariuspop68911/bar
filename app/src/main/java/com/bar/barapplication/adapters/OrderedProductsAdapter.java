package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.barapplication.AddOrderView;
import com.bar.barapplication.MainActivity;
import com.bar.barapplication.R;
import com.bar.barapplication.models.OrderBody;
import com.bar.barapplication.models.OrderBodyItem;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.WebManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class OrderedProductsAdapter extends ArrayAdapter<Product> implements View.OnClickListener {

    private List<Product> dataSet;
    Context mContext;
    private AddOrderView view;

    private static class ViewHolder {
        TextView name;
        TextView quantity;
        TextView mesaj;
        ImageView minus;

    }

    public void setDataSet(List<Product> dataSet) {
        this.dataSet = dataSet;
    }

    public OrderedProductsAdapter(final List<Product> data, final Context context, final AddOrderView view) {
        super(context, R.layout.ordered_product_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.view = view;
        view.getOrderButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataSet != null && !dataSet.isEmpty()) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.order_dialog);
                    dialog.setTitle("Client Name");
                    final EditText name = dialog.findViewById(R.id.edit);

                    Button button = dialog.findViewById(R.id.order_dialog_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order(name.getText().toString());
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private void order(String clientName) {
        OrderBody orderBody = new OrderBody();
        orderBody.setClientName(clientName);
        ArrayList<OrderBodyItem> items = new ArrayList<>();
        for (Product orderedProduct : dataSet) {
            for (int i = 1; i <= orderedProduct.getQuantity(); i++) {
                OrderBodyItem orderBodyItem = new OrderBodyItem();
                orderBodyItem.setIngredientsIds(new ArrayList<Integer>());
                orderBodyItem.setId(orderedProduct.getId());
                orderBodyItem.setExtraOptiuni(orderedProduct.getMesaj());
                items.add(orderBodyItem);
            }
        }
        orderBody.setOrderBodyItem(items);
        WebManager.createOrderPost(orderBody);
                /*Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);*/
        view.destroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = getItem(position);
        ViewHolder viewHolder;

        final View result;


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.ordered_product_item, parent, false);
        viewHolder.quantity = convertView.findViewById(R.id.quantity);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.mesaj = convertView.findViewById(R.id.mesaj);
        viewHolder.minus = convertView.findViewById(R.id.minus);

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Product orderedProduct : dataSet) {
                    if (product.getId() == orderedProduct.getId() && orderedProduct.getQuantity() > 1) {
                        orderedProduct.setQuantity(product.getQuantity() - 1);
                        notifyDataSetChanged();
                        return;
                    }
                }

                dataSet.remove(product);
                notifyDataSetChanged();
            }
        });

        convertView.setTag(viewHolder);


        //viewHolder.picture.setText(product.getImageUrl());
        viewHolder.name.setText(product.getName());
        viewHolder.quantity.setText("x" + product.getQuantity());
        viewHolder.mesaj.setText(product.getMesaj());

        return convertView;
    }
}