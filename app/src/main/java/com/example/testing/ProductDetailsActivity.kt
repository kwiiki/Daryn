package com.example.testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val URL = "https://dummyjson.com"

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productTitleTextView: TextView
    private lateinit var productDescriptionTextView: TextView
    private lateinit var productPriceTextView: TextView
    private lateinit var productImage:ImageView
    private lateinit var productButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Assuming you have TextViews in your layout to display product details
        productTitleTextView = findViewById(R.id.productTitleTextView)
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView)
        productPriceTextView = findViewById(R.id.productPriceTextView)
        productImage = findViewById(R.id.pdImageView)
        productButton = findViewById(R.id.detailButton)

        val productId = intent.getStringExtra("productId")?.toIntOrNull()

        if (productId != null) {
            fetchProductDetails(productId)
        } else {
            // Handle case where productId is null or not a valid integer
            Log.e("ProductDetailsActivity", "Invalid or missing product ID")
        }
    }

    private fun fetchProductDetails(productId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val productDetailsCall = apiInterface.getProductDetails(productId)

        productDetailsCall.enqueue(object : Callback<ProductDetails> {
            override fun onResponse(call: Call<ProductDetails>, response: Response<ProductDetails>) {
                if (response.isSuccessful) {
                    val productDetails = response.body()
                    updateUI(productDetails)
                } else {
                    // Handle unsuccessful response
                    Log.e("ProductDetailsActivity", "Failed to fetch product details")
                }
            }

            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                // Handle network failure
                Log.e("ProductDetailsActivity", "Network request failed", t)
            }
        })
    }

    private fun updateUI(productDetails: ProductDetails?) {
        // Update UI with product details
        if (productDetails != null) {
            Glide.with(this).load(productDetails.thumbnail).into(productImage)
            productTitleTextView.text = productDetails.title
            productDescriptionTextView.text = productDetails.description
            productPriceTextView.text = productDetails.price.toString()

            // Add Buy button click listener
            productButton.setOnClickListener {
                // Call the addToBasket method in the BasketActivity with the productDetails
                val basketIntent = Intent(this, BasketActivity::class.java)
                BasketActivity.addToBasket(productDetails)
                startActivity(basketIntent)
            }

            Log.d("AAAAAAA", "Attempting to load image from: ${productDetails.thumbnail}")
        } else {
            // Handle case where productDetails is null
            Log.e("ProductDetailsActivity", "Product details are null")
        }
    }

}

