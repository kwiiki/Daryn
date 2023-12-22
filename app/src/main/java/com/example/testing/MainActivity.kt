package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://dummyjson.com"

class MainActivity : AppCompatActivity() {
    lateinit var courseGRV: GridView
    lateinit var myAdapter: GridRVAdapter
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiInterface = retrofit.create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        courseGRV = findViewById(R.id.idGRV)

        getAllData()

    }
    private fun getAllData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val retroData = apiInterface.getData()

        retroData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val apiResponse = response.body()
                val data = apiResponse?.products ?: emptyList()

                myAdapter = GridRVAdapter(this@MainActivity, data)
                courseGRV.adapter = myAdapter

                // Set item click listener for the GridView
                courseGRV.setOnItemClickListener { _, _, position, _ ->
                    val selectedUser = data[position]
                    handleItemClick(selectedUser.id)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun handleItemClick(itemId: Int) {
        // Perform a network request using itemId
        // You can use Retrofit to make another request or use your preferred networking library
        // Example:
        val itemIdCall = apiInterface.getProductDetails(itemId)
        itemIdCall.enqueue(object : Callback<ProductDetails> {
            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                // Handle the response for item details
            }

            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                // Handle failure
            }
        })
    }

}

