package com.bar.barapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.bar.barapplication.adapters.SelecteazaIngredientsAdapter;
import com.bar.barapplication.models.AddPizzaIngredientBody;
import com.bar.barapplication.models.Ingredient;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.web.OnIngredientsReceived;
import com.bar.barapplication.web.OnProductByIdReceived;
import com.bar.barapplication.web.WebManager;

import java.util.ArrayList;

public class SelectIngredienteActivity extends AppCompatActivity implements OnIngredientsReceived, OnProductByIdReceived {

    private ListView listView;
    private Context context;
    private SelecteazaIngredientsAdapter ingredientsAdapter;
    private OnProductByIdReceived onProductByIdReceived;
    private Button salveaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slectare_ingrediente_activity);
        context = this;
        listView = findViewById(R.id.ingrediente_list);
        salveaza = findViewById(R.id.salveazaIngredient);
        onProductByIdReceived = this;
        WebManager.requestIngredients(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                WebManager.requestProductById(getIntent().getIntExtra(Constants.PIZZA_ID, 0), onProductByIdReceived);
            }
        }, 300);

        salveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredientsAdapter != null) {
                    ArrayList<Integer> selectedIngredients = ingredientsAdapter.getSelectedDataSet();
                    AddPizzaIngredientBody addPizzaIngredientBody = new AddPizzaIngredientBody();
                    addPizzaIngredientBody.setId(getIntent().getIntExtra(Constants.PIZZA_ID, 0));
                    addPizzaIngredientBody.setIngredientIds(selectedIngredients);
                    WebManager.addPizzaIngredient(addPizzaIngredientBody);
                    finish();
                }
            }
        });

    }

    @Override
    public void onAllIngredientsReceived(ArrayList<Ingredient> ingredients) {
        if(ingredients != null) {
            ingredientsAdapter = new SelecteazaIngredientsAdapter(ingredients, context);
            listView.setAdapter(ingredientsAdapter);
        }
    }

    @Override
    public void onProductByIdReceived(Product product) {
        if(product != null) {
            ingredientsAdapter.setSelectedItems(product.getIngredients());
        }
    }
}