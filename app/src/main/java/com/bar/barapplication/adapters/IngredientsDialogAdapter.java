package com.bar.barapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bar.barapplication.R;
import com.bar.barapplication.models.Ingredient;
import java.util.List;


public class IngredientsDialogAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView name;
        TextView price;
    }

    public IngredientsDialogAdapter(List<Ingredient> data, Context context) {
        super(context, R.layout.edit_ingredient_item, data);
        this.dataSet = data;
        this.mContext = context;
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

        convertView.setTag(viewHolder);

        viewHolder.name.setText(ingredient.getNume());
        viewHolder.price.setText(String.valueOf(ingredient.getPrice()));
        return convertView;
    }
}