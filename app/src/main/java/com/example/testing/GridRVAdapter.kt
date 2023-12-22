package com.example.testing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class GridRVAdapter(
    private val context: Context,
    private var productList: List<Product>
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return productList.size
    }

    fun updateData(newData: List<Product>) {
        productList = newData
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? {
        return if (position in this.productList.indices) productList[position] else null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        }

        // Initialize views only once
        val productImage: ImageView = convertView!!.findViewById(R.id.RV_image)
        val productTitle: TextView = convertView.findViewById(R.id.title_rv)
        val productPrice: TextView = convertView.findViewById(R.id.price_rv)

        // Load image using Glide
        Glide.with(context).load(productList[position].thumbnail).into(productImage)

        // Set text values
        productTitle.text = productList[position].title
        productPrice.text = productList[position].price.toString()

        // Set click listener for the whole item
        convertView.setOnClickListener {
            // Navigate to another page (Activity) when the item is clicked
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", productList[position].id.toString())
            context.startActivity(intent)
        }

        return convertView
    }
}

