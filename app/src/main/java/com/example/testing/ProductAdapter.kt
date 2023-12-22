package com.example.testing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private val context: Context, private val basketItems: List<ProductDetails>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productDetails = basketItems[position]

        Glide.with(context).load(productDetails.thumbnail).into(holder.productImage)
        holder.productTitle.text = productDetails.title
        holder.productPrice.text = productDetails.price.toString()
    }

    override fun getItemCount(): Int {
        return basketItems.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.RV_image)
        val productTitle: TextView = view.findViewById(R.id.title_rv)
        val productPrice: TextView = view.findViewById(R.id.price_rv)
    }
}
