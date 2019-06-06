package com.bar.barapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.barapplication.AddOrderView;
import com.bar.barapplication.R;
import com.bar.barapplication.models.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class ProductsAdapter extends ArrayAdapter<Product> implements View.OnClickListener {

    private List<Product> dataSet;
    Context mContext;
    private ArrayList<Product> orderedProducts;
    private AddOrderView view;
    private OrderedProductsAdapter orderedProductsAdapter;

    private static class ViewHolder {
        ImageView picture;
        TextView name;
        TextView price;
        ImageButton add;
        ImageButton remove;
    }

    public ProductsAdapter(List<Product> data, Context context, AddOrderView view) {
        super(context, R.layout.product_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.view = view;
        orderedProducts = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Product product = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.product_item, parent, false);
            viewHolder.picture = convertView.findViewById(R.id.picture);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.add = convertView.findViewById(R.id.plus);
            viewHolder.remove = convertView.findViewById(R.id.minus);
            viewHolder.price = convertView.findViewById(R.id.price);

            orderedProductsAdapter = new OrderedProductsAdapter(orderedProducts, mContext, view);
            view.getOrderedProductsList().setAdapter(orderedProductsAdapter);

            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (Product orderedProduct : orderedProducts) {
                        if (product.getId() == orderedProduct.getId()) {
                            orderedProduct.setQuantity(product.getQuantity() + 1);
                            orderedProductsAdapter.setDataSet(orderedProducts);
                            orderedProductsAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                    product.setQuantity(1);
                    orderedProducts.add(product);
                    orderedProductsAdapter.setDataSet(orderedProducts);
                    orderedProductsAdapter.notifyDataSetChanged();
                }
            });

            viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (Product orderedProduct : orderedProducts) {
                        if (product.getId() == orderedProduct.getId() && orderedProduct.getQuantity() > 1) {
                            orderedProduct.setQuantity(product.getQuantity() - 1);
                            orderedProductsAdapter.setDataSet(orderedProducts);
                            orderedProductsAdapter.notifyDataSetChanged();
                            return;
                        }
                    }

                    orderedProducts.remove(product);
                    orderedProductsAdapter.setDataSet(orderedProducts);
                    orderedProductsAdapter.notifyDataSetChanged();
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //viewHolder.picture.setText(product.getImageUrl());
        viewHolder.name.setText(product.getName());
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(product.getPrice()));
        viewHolder.price.setText(aaa);
        return convertView;
    }
}