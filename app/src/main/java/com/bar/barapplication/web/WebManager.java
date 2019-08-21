package com.bar.barapplication.web;

import android.util.Log;

import com.bar.barapplication.Constants;
import com.bar.barapplication.models.AddIngredientBody;
import com.bar.barapplication.models.AddPizzaIngredientBody;
import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.Ingredient;
import com.bar.barapplication.models.IngredientsResponse;
import com.bar.barapplication.models.OrderBody;
import com.bar.barapplication.models.OrderResponse;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.models.ProductByIdResponse;
import com.bar.barapplication.models.ProductResponse;
import com.bar.barapplication.models.StatusBody;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebManager {

    public static void requestOrdersByStatus(final OnOrdersReceived.OnOrdersStatusReceived onOrdersStatusReceived, int status) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<OrderResponse> call = service.getCreatedOrders();
        if (status == Constants.ORDER_CREATED) {
            call = service.getCreatedOrders();
        } else if (status == Constants.ORDER_COOKING) {
            call = service.getCookingOrders();
        } else if (status == Constants.ORDER_READY) {
            call = service.getReadyOrders();
        }
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                onOrdersStatusReceived.onOrdersStatusReceived(response.body().getOrders());
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                onOrdersStatusReceived.onOrdersStatusReceived(null);
            }
        });
    }

    public static void requestOrders(final OnOrdersReceived.OnAllOrdersReceived onOrdersReceived) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<OrderResponse> call = service.getAllOrders();

        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.body() != null) {
                    onOrdersReceived.onAllOrdersReceived(response.body().getOrders());
                } else {
                    onOrdersReceived.onAllOrdersReceived(null);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                onOrdersReceived.onAllOrdersReceived(null);
            }
        });
    }

    public static void requestProducts(final OnProductsReceived onProductsReceived) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProductResponse> call = service.getAllProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                onProductsReceived.onAllProductsReceived(response.body().getProducts());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                onProductsReceived.onAllProductsReceived(null);
            }
        });
    }

    public static void createOrderPost(OrderBody orderBody) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        service.createOrder(orderBody).enqueue(new Callback<OrderBody>() {
            @Override
            public void onResponse(Call<OrderBody> call, Response<OrderBody> response) {

                if(response.isSuccessful()) {
                    Log.i("adfasda", "post submitted to API." + response.body().toString());
                }else {
                    Log.e("adfasda", "post submitt ederror to API.");
                }
            }

            @Override
            public void onFailure(Call<OrderBody> call, Throwable t) {
                Log.e("adfasda", "error");
            }
        });
    }

    public static void deleteProduct(DeleteProductBody deleteProductBody) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProductResponse> call = service.deleteProduct(deleteProductBody);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.i("adfasda", "post submitted to API." );
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.i("adfasda", "" );
            }
        });
    }

    public static void addProduct(Product product) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        service.addProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {

                if(response.isSuccessful()) {
                    Log.i("adfasda", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
            }
        });
    }

    public static void changeStatus(StatusBody statusBody) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        service.changeStatus(statusBody).enqueue(new Callback<StatusBody>() {
            @Override
            public void onResponse(Call<StatusBody> call, Response<StatusBody> response) {

                if(response.isSuccessful()) {
                    Log.i("adfasda", "post submitted to API.");
                }
            }

            @Override
            public void onFailure(Call<StatusBody> call, Throwable t) {
                Log.i("adfasda", "post error ");
            }
        });
    }

    public static void addIngredient(Ingredient ingredient) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        AddIngredientBody addIngredientBody = new AddIngredientBody(ingredient.getNume(), ingredient.getPrice());
        service.addIngredient(addIngredientBody).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()) {
                    Log.i("adfasda", "post submitted to API.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public static void addPizzaIngredient(AddPizzaIngredientBody addPizzaIngredientBody) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        service.addPizzaIngredient(addPizzaIngredientBody).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()) {
                    Log.i("adfasda", "post submitted to API.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    public static void requestIngredients(final OnIngredientsReceived onIngredientsReceived) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<IngredientsResponse> call = service.getAllIngredients();

        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if(response.body() != null) {
                    onIngredientsReceived.onAllIngredientsReceived(response.body().getIngredients());
                } else {
                    onIngredientsReceived.onAllIngredientsReceived(null);
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                onIngredientsReceived.onAllIngredientsReceived(null);
            }
        });
    }

    public static void requestIngredientsById(int id, final OnIdIngredientsReceived onIdIngredientsReceived) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<IngredientsResponse> call = service.getIngredientsById(id);

        call.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if(response.body() != null) {
                    onIdIngredientsReceived.onIdIngredientsReceived(response.body().getIngredients());
                } else {
                    onIdIngredientsReceived.onIdIngredientsReceived(null);
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                onIdIngredientsReceived.onIdIngredientsReceived(null);
            }
        });
    }

    public static void deleteIngredient(int id) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Void> call = service.deleteIngredient(id);

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("adfasda", "delete submitted to API.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public static void requestProductById(int id, final OnProductByIdReceived onProductByIdReceived) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ProductByIdResponse> call = service.getProductById(id);

        call.enqueue(new Callback<ProductByIdResponse>() {
            @Override
            public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                if(response.body() != null) {
                    onProductByIdReceived.onProductByIdReceived(response.body().getProduct());
                } else {
                    onProductByIdReceived.onProductByIdReceived(null);
                }
            }

            @Override
            public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                onProductByIdReceived.onProductByIdReceived(null);
            }
        });
    }

}
