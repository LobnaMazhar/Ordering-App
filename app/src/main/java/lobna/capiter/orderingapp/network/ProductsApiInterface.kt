package lobna.capiter.orderingapp.network

import lobna.capiter.orderingapp.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApiInterface {

    @GET("products")
    suspend fun products(@Query("page") page: Int): Response<List<ProductModel>>
}