package files.app.retrofitexample.dataClases


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("username")
    val username: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("firstName")
    val firstName: String = "",
    @SerialName("lastName")
    val lastName: String = "",
    @SerialName("gender")
    val gender: String = "",
    @SerialName("image")
    val image: String = "",
    @SerialName("token")
    val token: String = ""

)