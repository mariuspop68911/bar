package com.bar.barapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bar.barapplication.adapters.EditIngredientsAdapter;
import com.bar.barapplication.adapters.EditProductsAdapter;
import com.bar.barapplication.adapters.IngredientsDialogAdapter;
import com.bar.barapplication.adapters.OrdersAdapter;
import com.bar.barapplication.models.Ingredient;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnIngredientsReceived;
import com.bar.barapplication.web.OnProductsReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;
import java.util.Calendar;

public class EditIngredienteActivity extends AppCompatActivity implements OnIngredientsReceived {

    private ListView listView;
    private FloatingActionButton add;
    private Context context;
    private EditIngredientsAdapter ingredientsAdapter;
    private OnIngredientsReceived onIngredientsReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_ingrediente_activity);
        context = this;
        listView = findViewById(R.id.ingrediente_list);
        add = findViewById(R.id.ingrediente_fab);
        onIngredientsReceived = this;

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.create_ingrediente_dialog);
                dialog.setTitle("Add ingredient");
                /*ListView ingredienteListView = dialog.findViewById(R.id.ingredients_list);
                Ingredient ing1 = new Ingredient();
                ing1.setNume("Varza");

                Ingredient ing2 = new Ingredient();
                ing2.setNume("Branza");

                Ingredient ing3 = new Ingredient();
                ing3.setNume("Banane");

                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ingredients.add(ing1);
                ingredients.add(ing2);
                ingredients.add(ing3);
                IngredientsDialogAdapter adapter = new IngredientsDialogAdapter(ingredients, context);
                ingredienteListView.setAdapter(adapter);*/
                Button adauga = dialog.findViewById(R.id.adaugaIngredient);
                final EditText ingredientEdit = dialog.findViewById(R.id.ingredientEdit);
                final EditText pretIngredient = dialog.findViewById(R.id.ingredientPretEdit);
                adauga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!ingredientEdit.getText().toString().isEmpty() && !pretIngredient.getText().toString().isEmpty()) {
                            Ingredient ingredient = new Ingredient(ingredientEdit.getText().toString(), Double.valueOf(pretIngredient.getText().toString()));
                            WebManager.addIngredient(ingredient);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    WebManager.requestIngredients(onIngredientsReceived);
                                }
                            }, 300);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        WebManager.requestIngredients(this);

    }

    @Override
    public void onAllIngredientsReceived(ArrayList<Ingredient> ingredients) {
        if(ingredients != null) {
            ingredientsAdapter = new EditIngredientsAdapter(ingredients, context);
            listView.setAdapter(ingredientsAdapter);
        }
    }
}