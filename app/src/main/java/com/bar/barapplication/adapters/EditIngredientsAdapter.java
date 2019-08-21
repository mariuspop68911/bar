package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.barapplication.EditProductsView;
import com.bar.barapplication.R;
import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.Ingredient;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.WebManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class EditIngredientsAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener {

    private List<Ingredient> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView name;
        TextView price;
    }

    public EditIngredientsAdapter(List<Ingredient> data, Context context) {
        super(context, R.layout.edit_ingredient_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ingredient ingredient = getItem(position);
        ViewHolder viewHolder;

        final View result;

        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.edit_ingredient_item, parent, false);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.price = convertView.findViewById(R.id.price);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.delete_dialog);
                dialog.setTitle("Delete ingredient");

                Button yes = dialog.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        WebManager.deleteIngredient(ingredient.getId());
                        dataSet.remove(ingredient);
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

        convertView.setTag(viewHolder);

        viewHolder.name.setText(ingredient.getNume());
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(ingredient.getPrice()));
        viewHolder.price.setText(aaa);
        return convertView;
    }
}