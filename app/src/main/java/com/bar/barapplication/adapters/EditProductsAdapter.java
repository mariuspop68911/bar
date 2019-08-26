package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.bar.barapplication.Constants;
import com.bar.barapplication.EditProductsView;
import com.bar.barapplication.R;
import com.bar.barapplication.SelectIngredienteActivity;
import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.WebManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class EditProductsAdapter extends ArrayAdapter<Product> implements View.OnClickListener {

    private List<Product> dataSet;
    Context mContext;
    private EditProductsView view;


    private static class ViewHolder {
        ImageView picture;
        TextView name;
        TextView price;
        ImageView ingredients;
        ImageView delete;
    }

    public EditProductsAdapter(List<Product> data, Context context, EditProductsView view) {
        super(context, R.layout.edit_product_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.view = view;
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
        convertView = inflater.inflate(R.layout.edit_product_item, parent, false);
        viewHolder.picture = convertView.findViewById(R.id.picture);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.price = convertView.findViewById(R.id.price);
        viewHolder.ingredients = convertView.findViewById(R.id.ingrediente_button);
        viewHolder.delete = convertView.findViewById(R.id.delete_button);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.delete_dialog);
                dialog.setTitle("Delete product");

                Button yes = dialog.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeleteProductBody deleteProductBody = new DeleteProductBody();
                        deleteProductBody.setId(product.getId());
                        WebManager.deleteProduct(deleteProductBody);
                        dataSet.remove(product);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                Button no = dialog.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        viewHolder.ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SelectIngredienteActivity.class);
                intent.putExtra(Constants.PIZZA_ID, product.getId());
                mContext.startActivity(intent);
            }
        });

        convertView.setTag(viewHolder);

        //viewHolder.picture.setText(product.getImageUrl());
        viewHolder.name.setText(product.getName());
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(product.getPrice()));
        viewHolder.price.setText(aaa);
        return convertView;
    }
}