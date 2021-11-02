package lobna.capiter.orderingapp.diffutil

import androidx.recyclerview.widget.DiffUtil
import lobna.capiter.orderingapp.model.ProductModel

object ProductsDiffUtil : DiffUtil.ItemCallback<ProductModel>() {

    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
        return oldItem == newItem
    }
}