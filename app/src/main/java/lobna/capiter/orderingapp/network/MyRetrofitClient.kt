package lobna.capiter.orderingapp.network

import lobna.capiter.orderingapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofitClient {

    private const val BASE_URL = "https://capiter-a7b2.restdb.io/rest/"

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-apikey", BuildConfig.PRODUCTS_AUTH_KEY)
                .build()
            chain.proceed(request)
        };
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}