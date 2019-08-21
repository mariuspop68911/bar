package com.bar.barapplication.web;

import com.bar.barapplication.models.AddIngredientBody;
import com.bar.barapplication.models.AddPizzaIngredientBody;
import com.bar.barapplication.models.DeleteProductBody;
import com.bar.barapplication.models.IngredientsResponse;
import com.bar.barapplication.models.OrderBody;
import com.bar.barapplication.models.OrderResponse;
import com.bar.barapplication.models.Product;
import com.bar.barapplication.models.ProductByIdResponse;
import com.bar.barapplication.models.ProductResponse;
import com.bar.barapplication.models.StatusBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("/api/ingredients/add")
    Call<Void> addIngredient(@Body AddIngredientBody addIngredientBody);

    @GET("/api/ingredients/get")
    Call<IngredientsResponse> getAllIngredients();

    @GET("/api/ingredients/get")
    Call<IngredientsResponse> getIngredientsById(@Query("Id") int id);

    @GET("/api/ingredients/Delete")
    Call<Void> deleteIngredient(@Query("Id") int id);

    @POST("/api/menu/Edit")
    Call<Void> addPizzaIngredient(@Body AddPizzaIngredientBody addPizzaIngredientBody);

    @GET("/api/menu/get")
    Call<ProductByIdResponse> getProductById(@Query("id") int id);
}
