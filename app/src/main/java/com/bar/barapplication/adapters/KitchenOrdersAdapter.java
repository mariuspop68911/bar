package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bar.barapplication.Constants;
import com.bar.barapplication.R;
import com.bar.barapplication.models.Order;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.models.StatusBody;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;
import java.util.List;


public class KitchenOrdersAdapter extends ArrayAdapter<Order> implements View.OnClickListener {

    private List<Order> dataSet;
    Context mContext;
    private ArrayList<Product> products;

    private static class ViewHolder {
        TextView clientName;
        TextView orderNumber;
        LinearLayout layout;
    }

    public KitchenOrdersAdapter(List<Order> data, Context context, ArrayList<Product> products) {
        super(context, R.layout.kitchen_order_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.products = products;
    }

    public void setDataSet(List<Order> dataSet) {
        this.dataSet = dataSet;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Order order = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.kitchen_order_item, parent, false);
            viewHolder.clientName = convertView.findViewById(R.id.client_name);
            viewHolder.orderNumber = convertView.findViewById(R.id.order_number);
            viewHolder.layout = convertView.findViewById(R.id.layout);

            for (Integer productId : order.getProductIds()) {
                for (Product product : products) {
                    if(product.getId() == productId) {
                        TextView textView = new TextView(mContext);
                        textView.setText(product.getName());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(20,0,0,0);
                        textView.setLayoutParams(params);
                        viewHolder.layout .addView(textView);
                    }
                }
            }

            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.mark_kitchen_order_dialog);

                    Button ready = dialog.findViewById(R.id.ready);
                    ready.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order.setStatus(Constants.ORDER_READY);
                            WebManager.changeStatus(new StatusBody(Constants.ORDER_READY, order.getOrderId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.clientName.setText(order.getClientName());
        viewHolder.orderNumber.setText(String.valueOf(order.getOrderNumber()));
        return convertView;
    }
}