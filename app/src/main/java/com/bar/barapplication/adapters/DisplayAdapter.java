package com.bar.barapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bar.barapplication.Constants;
import com.bar.barapplication.R;
import com.bar.barapplication.models.Order;

import java.util.List;


public class DisplayAdapter extends ArrayAdapter<Order> implements View.OnClickListener {

    private List<Order> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView clientName;
        TextView orderStatus;
        TextView orderNumber;
    }

    public DisplayAdapter(List<Order> data, Context context) {
        super(context, R.layout.display_order_item, data);
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
            convertView = inflater.inflate(R.layout.display_order_item, parent, false);
            viewHolder.clientName = convertView.findViewById(R.id.client_name);
            viewHolder.orderStatus = convertView.findViewById(R.id.order_status);
            viewHolder.orderNumber = convertView.findViewById(R.id.order_number);
            convertView.setTag(viewHolder);
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_green));

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

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

        if (order.getStatus() == Constants.ORDER_COOKING) {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_yellow));}
        else if (order.getStatus() == Constants.ORDER_READY) {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_view_green));}
        viewHolder.orderStatus.setText(status);
        viewHolder.orderNumber.setText(String.valueOf(order.getOrderNumber()));
        return convertView;
    }
}