package com.example.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private val basketItems: MutableList<ProductDetails> = mutableListOf()

class BasketActivity : AppCompatActivity() {

    private lateinit var basketRecyclerView: RecyclerView
    private lateinit var basketAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        // Initialize RecyclerView and Adapter
        basketRecyclerView = findViewById(R.id.basketRecyclerView)
        basketAdapter = ProductAdapter(this, basketItems)
        basketRecyclerView.adapter = basketAdapter
        basketRecyclerView.layoutManager = LinearLayoutManager(this)

        // Example: Add product to basket (you will do this when the "Buy" button is clicked in the store)
//        val productToAdd = ProductDetails(id = 1, title = "Example Product", price = 19.99)
//        addToBasket(productToAdd)
    }

    companion object {
        fun addToBasket(productDetails: ProductDetails) {
            // Add the product to the basketItems list
            // You can add additional logic if needed (e.g., check for duplicates)
            basketItems.add(productDetails)


        }
    }
}

