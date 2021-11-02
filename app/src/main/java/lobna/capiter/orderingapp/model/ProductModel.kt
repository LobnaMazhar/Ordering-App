package lobna.capiter.orderingapp.model

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("_id") val id: String,
    val name: String,
    val price: Int,
    @SerializedName("image-url") val imageurl: String,
    val page: Int
)