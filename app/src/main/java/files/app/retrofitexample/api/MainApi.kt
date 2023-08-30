package files.app.retrofitexample.api

import files.app.retrofitexample.dataClases.AuthRequest
import files.app.retrofitexample.dataClases.Product
import files.app.retrofitexample.dataClases.Products
import files.app.retrofitexample.dataClases.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): User

    @GET("products")
    suspend fun getAllProducts(): Products

    @GET("products/search")
    suspend fun getProductByName(@Query("q") name: String): Products
}