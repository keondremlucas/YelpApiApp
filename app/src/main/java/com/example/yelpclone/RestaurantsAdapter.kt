package com.example.yelpclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RestaurantsAdapter( val context: Context, val res: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant,parent, false))
    }
    override fun getItemCount() = res.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = res[position]
        holder.name.text = restaurant.name
        holder.ratingBar.rating = restaurant.rating
        holder.price.text = "Price: ${restaurant.price}"
        holder.address.text = restaurant.location.address
        holder.reviews.text = "${restaurant.review_count} Reviews"
        holder.category.text = restaurant.categories[0].title
        holder.distance.text = restaurant.milesdistance()
        Picasso.get().load(restaurant.imageUrl).into(holder.image)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.name)
            val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
            val address: TextView = itemView.findViewById(R.id.location)
            val reviews: TextView = itemView.findViewById(R.id.reviews)
            val price: TextView = itemView.findViewById(R.id.price)
            val category : TextView = itemView.findViewById(R.id.type)
            val distance : TextView = itemView.findViewById(R.id.Distance)
            val image : ImageView = itemView.findViewById(R.id.imageView)
        }

    }


