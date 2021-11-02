package lobna.capiter.orderingapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lobna.capiter.orderingapp.R
import lobna.capiter.orderingapp.databinding.ItemProductBinding
import lobna.capiter.orderingapp.diffutil.ProductsDiffUtil
import lobna.capiter.orderingapp.model.ProductModel

class ProductsAdapter :
    PagingDataAdapter<ProductModel, ProductsAdapter.ProductsViewHolder>(ProductsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemProductBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(itemProductBinding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    inner class ProductsViewHolder(private val itemProductBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {

        fun bind(item: ProductModel) {
            itemProductBinding.run {
                productName.text = item.name
                productPrice.text = root.context.getString(R.string.currency, item.price)
                Glide.with(productImage).load(item.imageurl).into(productImage)
                productQuantity.text = "0" // TODO get count from cart
                addToCartButton.setOnClickListener {
                    //TODO add to cart click
                }
            }
        }
    }
}