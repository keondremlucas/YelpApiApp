package com.example.yelpclone

import com.google.gson.annotations.SerializedName

data class YelpData(
    @SerializedName("businesses") val restaurants: List<Restaurant>
)

data class Restaurant(
    val name: String,
    val rating: Float,
    val price: String,
    val review_count: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("distance") val distanceinmeters: Double,
    val categories: List<Categories>,
    val location: Location

) {
    fun milesdistance():String {
        val miles2meter = 0.000621371
        val distance2miles = "%.2f".format(distanceinmeters*miles2meter)
        return "$distance2miles mi"
    }
}

data class Location(
    @SerializedName("address1") val address: String
)

data class Categories(
    val title: String
)

