package com.bar.barapplication.web;

import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.OrderBody;
import com.bar.barapplication.models.OrderResponse;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.models.ProductResponse;
import com.bar.barapplication.models.StatusBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {

    @GET("/api/orders/Get")
    Call<OrderResponse> getCreatedOrders();

    @GET("/api/orders/Get")
    Call<OrderResponse> getCookingOrders();

    @GET("/api/orders/GetDisplayOrders")
    Call<OrderResponse> getReadyOrders();

    @GET("/api/orders/get")
    Call<OrderResponse> getAllOrders();

    @GET("/api/menu/get")
    Call<ProductResponse> getAllProducts();

    @POST("/api/orders/NewOrder")
    Call<OrderBody> createOrder(@Body OrderBody orderBody);

    @POST("/api/menu/delete")
    Call<ProductResponse> deleteProduct(@Body DeleteProductBody deleteProductBody);

    @POST("/api/menu/add")
    Call<Product> addProduct(@Body Product product);

    @POST("/api/orders/ChangeStatus")
    Call<StatusBody> changeStatus(@Body StatusBody statusBody);
}
