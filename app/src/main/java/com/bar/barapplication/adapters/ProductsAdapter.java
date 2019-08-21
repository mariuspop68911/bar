package com.bar.barapplication.adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bar.barapplication.AddOrderView;
import com.bar.barapplication.Constants;
import com.bar.barapplication.R;
import com.bar.barapplication.models.Ingredient;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnIdIngredientsReceived;
import com.bar.barapplication.web.OnIngredientsReceived;
import com.bar.barapplication.web.OnProductByIdReceived;
import com.bar.barapplication.web.WebManager;

import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ProductsAdapter extends ArrayAdapter<Product> implements View.OnClickListener, OnIngredientsReceived, OnProductByIdReceived {

    private List<Product> dataSet;
    private Context mContext;
    private ArrayList<Product> orderedProducts;
    private AddOrderView view;
    private OrderedProductsAdapter orderedProductsAdapter;
    private OnIngredientsReceived onIngredientsReceived;
    private OnProductByIdReceived onIdIngredientsReceived;
    private ListView dialogListView;
    private SelecteazaIngredientsAdapter ingredientsAdapter;
    private ArrayList<Ingredient> idSelectedIngredients;

    @Override
    public void onAllIngredientsReceived(ArrayList<Ingredient> ingredients) {

        if (ingredients != null) {
            ingredientsAdapter = new SelecteazaIngredientsAdapter(ingredients, mContext);
            dialogListView.setAdapter(ingredientsAdapter);
        }
    }

    @Override
    public void onProductByIdReceived(Product product) {
        for (Ingredient idSelectedIngredient : product.getIngredients()) {
            idSelectedIngredient.setSelected(true);
        }
        idSelectedIngredients = product.getIngredients();
        if (idSelectedIngredients != null && ingredientsAdapter != null) {
            ingredientsAdapter.setSelectedItems(idSelectedIngredients);
        }
    }

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
        onIngredientsReceived = this;
        onIdIngredientsReceived = this;
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

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.slectare_ingrediente_activity);
                dialog.setTitle("Selecteaza ingredient");

                dialogListView = dialog.findViewById(R.id.ingrediente_list);

                WebManager.requestIngredients(onIngredientsReceived);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WebManager.requestProductById(product.getId(), onIdIngredientsReceived);
                    }
                }, 300);

                Button add = dialog.findViewById(R.id.salveazaIngredient);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cu = "";
                        String fara = "";
                        ArrayList<Integer> selectedIds = new ArrayList<>();
                        for (Ingredient idSelectedIngredient : idSelectedIngredients) {
                            selectedIds.add(idSelectedIngredient.getId());
                        }
                        for (Ingredient ingredient : ingredientsAdapter.getSelectedDataSetObject()) {
                            if (!selectedIds.contains(ingredient.getId())) {
                                cu = cu + ingredient.getNume() + ",";
                            }
                        }

                        for (Ingredient ingredient : ingredientsAdapter.getUnSelectedDataSetObject()) {
                            if (selectedIds.contains(ingredient.getId())) {
                                fara = fara + ingredient.getNume() + ",";
                            }
                        }

                        try {
                            for (Product orderedProduct : orderedProducts) {
                                String message = (cu.isEmpty() ? "" : " Adauga:" + cu) + (fara.isEmpty() ? "" : " Scoate:" + fara);
                                //orderedProduct.setName(orderedProduct.getName() + (cu.isEmpty()? "" :  " Adauga:" + cu) + (fara.isEmpty()? "" :  " Scoate:" + fara));
                                product.setMesaj(message);

                                if (product.getId() == orderedProduct.getId() && product.getMesaj().equals(orderedProduct.getMesaj())) {

                                    orderedProduct.setQuantity(orderedProduct.getQuantity() + 1);
                                    orderedProductsAdapter.setDataSet(orderedProducts);
                                    orderedProductsAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                    return;

                                }
                            }
                            Product newProduct = new Product();
                            newProduct.setId(product.getId());
                            newProduct.setImageUrl(product.getImageUrl());
                            newProduct.setIngredients(product.getIngredients());
                            newProduct.setPrice(product.getPrice());
                            newProduct.setQuantity(1);
                            String message = (cu.isEmpty() ? "" : " Adauga:" + cu) + (fara.isEmpty() ? "" : " Scoate:" + fara);
                            newProduct.setMesaj(message);
                            newProduct.setName(product.getName());
                            //newProduct.setName(product.getName() + (cu.isEmpty()? "" :  " Adauga:" + cu) + (fara.isEmpty()? "" :  " Scoate:" + fara));

                            orderedProducts.add(newProduct);
                            orderedProductsAdapter.setDataSet(orderedProducts);
                            orderedProductsAdapter.notifyDataSetChanged();

                            dialog.dismiss();
                        } catch (Exception ex) {

                        }

                    }
                });
                dialog.show();


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

        //viewHolder.picture.setText(product.getImageUrl());
        viewHolder.name.setText(product.getName());
        NumberFormat format = new DecimalFormat("0.#");
        String aaa = String.valueOf(format.format(product.getPrice()));
        viewHolder.price.setText(aaa);
        return convertView;
    }
}