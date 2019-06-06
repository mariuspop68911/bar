package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bar.barapplication.Constants;
import com.bar.barapplication.R;
import com.bar.barapplication.models.Order;
import com.bar.barapplication.models.OrderDetail;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.models.StatusBody;
import com.bar.barapplication.web.WebManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class OrdersAdapter extends ArrayAdapter<Order> implements View.OnClickListener {

    private List<Order> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView clientName;
        TextView orderStatus;
        TextView orderNumber;
        TextView orderPrice;
    }

    public OrdersAdapter(List<Order> data, Context context) {
        super(context, R.layout.order_item, data);
        this.dataSet = data;
        this.mContext = context;
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
            convertView = inflater.inflate(R.layout.order_item, parent, false);
            viewHolder.clientName = convertView.findViewById(R.id.client_name);
            viewHolder.orderStatus = convertView.findViewById(R.id.order_status);
            viewHolder.orderNumber = convertView.findViewById(R.id.order_number);
            viewHolder.orderPrice = convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);

            if (order.getStatus() == Constants.ORDER_READY) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_green));
            } else if (order.getStatus() == Constants.ORDER_CREATED) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_red));
            } else {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_yellow));
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.mark_order_dialog);

                    Button delivered = dialog.findViewById(R.id.delivered);
                    delivered.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order.setStatus(Constants.ORDER_DELIVERED);
                            WebManager.changeStatus(new StatusBody(Constants.ORDER_DELIVERED, order.getOrderId()));
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    Button canceled = dialog.findViewById(R.id.canceled);
                    canceled.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            order.setStatus(Constants.ORDER_CANCELED);
                            WebManager.changeStatus(new StatusBody(Constants.ORDER_CANCELED, order.getOrderId()));
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
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(order.getPrice()));
        viewHolder.orderPrice.setText(aaa) ;
        viewHolder.clientName.setText(order.getClientName());
        String status = "Not ready";
        if (order.getStatus() == Constants.ORDER_CREATED) {
            status = "Nepreluata";
        } else if (order.getStatus() == Constants.ORDER_COOKING) {
            status = "In Pregatire";
        } else if (order.getStatus() == Constants.ORDER_READY) {
            status = "Pregatita";
        } else if (order.getStatus() == Constants.ORDER_DELIVERED) {
            status = "Livrata";
        } else if (order.getStatus() == Constants.ORDER_CANCELED) {
            status = "Anulata";
        }
        viewHolder.orderStatus.setText(status);
        viewHolder.orderNumber.setText(String.valueOf(order.getOrderNumber()));
        return convertView;
    }
}