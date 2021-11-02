package lobna.capiter.orderingapp.datasource

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import lobna.capiter.orderingapp.model.NetworkResponse
import lobna.capiter.orderingapp.model.ProductModel
import lobna.capiter.orderingapp.repository.ProductsRepository

class ProductsDataSource(private val context: Context) :
    PagingSource<Int, ProductModel>() {

    private val TAG = ProductsDataSource::class.java.simpleName

    override fun getRefreshKey(state: PagingState<Int, ProductModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductModel> {
        try {
            val page = params.key ?: 0

            when (val response = makeApiCall(page)) {
                is NetworkResponse.ErrorResponse ->
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                is NetworkResponse.ExceptionResponse ->
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                is NetworkResponse.DataResponse<*> -> {
                    (response.data as? List<ProductModel>)?.run {
                        val nextOffset = if (size != 0) page + 1 else null
                        return LoadResult.Page(this, null, nextOffset)
                    }
                }
            }
            return LoadResult.Page(emptyList(), null, null)
        } catch (exception: Exception) {
            Log.e(TAG, "Failed to fetch data!")
            return LoadResult.Error(exception)
        }
    }

    private suspend fun makeApiCall(page: Int): NetworkResponse {
        Log.d("ProductsList", "Getting page $page")
        return ProductsRepository.getProducts(page)
    }
}