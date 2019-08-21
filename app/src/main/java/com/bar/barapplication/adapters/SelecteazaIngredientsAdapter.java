package com.bar.barapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bar.barapplication.R;
import com.bar.barapplication.models.Ingredient;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class SelecteazaIngredientsAdapter extends ArrayAdapter<Ingredient> implements View.OnClickListener {

    private List<Ingredient> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView name;
        TextView price;
        ImageView checked;
    }

    public List<Ingredient> getDataSet() {
        return dataSet;
    }

    public void setSelectedItems(ArrayList<Ingredient> items) {
        for (Ingredient ingredient : dataSet) {
            for (Ingredient item : items) {
                if(item.getId() == ingredient.getId()) {
                    ingredient.setSelected(true);
                }
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedDataSet() {
        ArrayList<Integer> selectedIngredients = new ArrayList<>();
        for(Ingredient ingredient : dataSet) {
            if (ingredient.isSelected()) {
                selectedIngredients.add(ingredient.getId());
            }
        }
        return selectedIngredients;
    }

    public ArrayList<Ingredient> getSelectedDataSetObject() {
        ArrayList<Ingredient> selectedIngredients = new ArrayList<>();
        for(Ingredient ingredient : dataSet) {
            if (ingredient.isSelected()) {
                selectedIngredients.add(ingredient);
            }
        }
        return selectedIngredients;
    }

    public ArrayList<Ingredient> getUnSelectedDataSetObject() {
        ArrayList<Ingredient> selectedIngredients = new ArrayList<>();
        for(Ingredient ingredient : dataSet) {
            if (!ingredient.isSelected()) {
                selectedIngredients.add(ingredient);
            }
        }
        return selectedIngredients;
    }

    public SelecteazaIngredientsAdapter(List<Ingredient> data, Context context) {
        super(context, R.layout.selectare_ingredient_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Ingredient ingredient = getItem(position);
        final ViewHolder viewHolder;

        final View result;

        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.selectare_ingredient_item, parent, false);
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.price = convertView.findViewById(R.id.price);
        viewHolder.checked = convertView.findViewById(R.id.checked);
        if(ingredient.isSelected()) {
            viewHolder.checked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checked.setVisibility(View.GONE);
        }
        convertView.setTag(position);
        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredient.isSelected()) {
                    viewHolder.checked.setVisibility(View.GONE);
                    dataSet.get(Integer.valueOf(finalConvertView.getTag().toString())).setSelected(false);
                } else {
                    viewHolder.checked.setVisibility(View.VISIBLE);
                    dataSet.get(Integer.valueOf(finalConvertView.getTag().toString())).setSelected(true);
                }
            }
        });

        viewHolder.name.setText(ingredient.getNume());
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(ingredient.getPrice()));
        viewHolder.price.setText(aaa);
        return convertView;
    }
}