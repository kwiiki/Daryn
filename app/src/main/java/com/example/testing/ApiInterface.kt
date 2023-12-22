package com.example.testing

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("products?skip=0&limit=100")
    fun getData(): Call<ApiResponse>
    @GET("products/{id}")
    fun getProductDetails(@Path("id") productId: Int): Call<ProductDetails>

}