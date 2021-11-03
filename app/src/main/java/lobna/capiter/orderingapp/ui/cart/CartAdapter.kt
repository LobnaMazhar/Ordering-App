package lobna.capiter.orderingapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.capiter.orderingapp.R
import lobna.capiter.orderingapp.databinding.ItemCartBinding
import lobna.capiter.orderingapp.model.CartModel
import lobna.capiter.orderingapp.repository.CartRepository

class CartAdapter(private val items: ArrayList<CartModel>, val cartInterface: CartInterface) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemCartBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemCartBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class CartViewHolder(private val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {

        fun bind(item: CartModel) {
            itemCartBinding.run {
                productName.text = item.name
                productPrice.text = root.context.getString(R.string.currency, item.price)
                Glide.with(productImage).load(item.imageurl).into(productImage)
                productQuantity.text = item.quantity.toString()
                deleteIcon.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val response = CartRepository.deleteItem(root.context, item.id)
                        withContext(Dispatchers.Main) { cartInterface.itemDeleted(item) }
                    }
                }
            }
        }
    }
}