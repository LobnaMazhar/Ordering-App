package lobna.capiter.orderingapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.capiter.orderingapp.R
import lobna.capiter.orderingapp.databinding.ItemProductBinding
import lobna.capiter.orderingapp.diffutil.ProductsDiffUtil
import lobna.capiter.orderingapp.model.CartModel
import lobna.capiter.orderingapp.model.ProductModel
import lobna.capiter.orderingapp.repository.CartRepository

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
                var quantity = 0
                CoroutineScope(Dispatchers.IO).launch {
                    val response = CartRepository.getQuantity(root.context, item.id)
                    withContext(Dispatchers.Main) {
                        quantity = if (response.isNullOrEmpty()) 0 else response[0]
                        productQuantity.text = quantity.toString()
                    }
                }
                addToCartButton.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (quantity != 0)
                            CartRepository.updateItem(root.context, item.id, quantity + 1)
                        else CartRepository.insertItem(root.context, CartModel(item))
                        withContext(Dispatchers.Main) {
                            productQuantity.text = (++quantity).toString()
                        }
                    }
                }
            }
        }
    }
}