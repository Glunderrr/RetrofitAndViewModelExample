package files.app.retrofitexample.api

import android.service.controls.Control
import files.app.retrofitexample.dataClases.AuthRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private val mainApi by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(MainApi::class.java)
    }

    suspend fun getInfoById(id: Int) = mainApi.getProductById(id)

    suspend fun getUserData(authRequest: AuthRequest) = mainApi.auth(authRequest)

    suspend fun getListOfProducts() = mainApi.getAllProducts().products

    suspend fun getProductByName(searchName: String) = mainApi.getProductByName(searchName).products
}