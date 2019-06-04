package com.bar.barapplication.web;

import android.util.Log;

import com.bar.barapplication.Constants;
import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.OrderBody;
import com.bar.barapplication.models.OrderResponse;
import com.bar.barapplication.models.Product;
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
                onOrdersReceived.onAllOrdersReceived(response.body().getOrders());
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
                }
            }

            @Override
            public void onFailure(Call<OrderBody> call, Throwable t) {
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

}
