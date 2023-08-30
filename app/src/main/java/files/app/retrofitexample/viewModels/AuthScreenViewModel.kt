package files.app.retrofitexample.viewModels

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import files.app.retrofitexample.api.ApiManager
import files.app.retrofitexample.dataClases.AuthRequest
import files.app.retrofitexample.dataClases.User

class AuthScreenViewModel : ViewModel() {
    val passwordFiledViewModel = PasswordFiledController()
    val userName = mutableStateOf("")
    val snackBarHostState = SnackbarHostState()

    suspend fun userRequest(navigationFun: () -> Unit, user: MutableState<User>) {
        try {
            user.value = ApiManager.getUserData(
                AuthRequest(
                    "dpettegre6", "YVmhktgYVS"
                    /*                        userName.value, passwordFiledViewModel.password.value*/
                )
            )
            navigationFun.invoke()
        } catch (e: Exception) {
            snackBarHostState.showSnackbar(
                message = "Username or password is incorrect",
                duration = SnackbarDuration.Short
            )
            Log.e("userRequest", "error:$e")
        }
    }
}