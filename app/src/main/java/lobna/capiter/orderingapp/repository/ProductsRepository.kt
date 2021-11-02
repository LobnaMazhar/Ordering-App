package lobna.capiter.orderingapp.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import lobna.capiter.orderingapp.datasource.ProductsDataSource
import lobna.capiter.orderingapp.model.NetworkResponse
import lobna.capiter.orderingapp.model.ProductModel
import lobna.capiter.orderingapp.network.MyRetrofitClient
import lobna.capiter.orderingapp.network.ProductsApiInterface

object ProductsRepository {

    private val productsApi: ProductsApiInterface by lazy {
        MyRetrofitClient.createService(ProductsApiInterface::class.java)
    }

    fun getProducts(context: Context): Flow<PagingData<ProductModel>> {
        return Pager(PagingConfig(pageSize = 10)) { ProductsDataSource(context) }.flow
    }

    suspend fun getProducts(page: Int): NetworkResponse {
        return try {
            val response = productsApi.products(page)

            if (response.isSuccessful) {
                NetworkResponse.DataResponse(response.body())
            } else {
                NetworkResponse.ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) { e.printStackTrace() }
            NetworkResponse.ExceptionResponse(e.message)
        }
    }
}