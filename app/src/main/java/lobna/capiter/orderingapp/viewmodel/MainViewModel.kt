package lobna.capiter.orderingapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import lobna.capiter.orderingapp.model.ProductModel
import lobna.capiter.orderingapp.repository.ProductsRepository

class MainViewModel:ViewModel() {

    fun getProducts(context: Context): Flow<PagingData<ProductModel>> {
        return ProductsRepository.getProducts(context)
    }
}