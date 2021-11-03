package lobna.capiter.orderingapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartModel(
    @PrimaryKey val id: String,
    val name: String,
    val imageurl: String,
    val price: Int,
    val quantity: Int
) {
    constructor(productModel: ProductModel) : this(
        productModel.id, productModel.name, productModel.imageurl, productModel.price, 1
    )
}