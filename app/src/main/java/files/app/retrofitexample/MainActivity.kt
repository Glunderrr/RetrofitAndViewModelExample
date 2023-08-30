package files.app.retrofitexample

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import files.app.retrofitexample.dataClases.User
import files.app.retrofitexample.screens.AuthScreen
import files.app.retrofitexample.screens.UserPageScreen
import files.app.retrofitexample.viewModels.AuthScreenViewModel
import files.app.retrofitexample.viewModels.UserPageViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationController()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NavigationController() {
    val navController = rememberNavController()
    val user = remember { mutableStateOf(User()) }
    NavHost(
        navController = navController,
        startDestination = "authScreen"
    ) {
        composable("authScreen") {
            AuthScreen(AuthScreenViewModel(), user) {
                navController.navigate("userPageScreen") {
                    popUpTo(0)
                }
            }
        }
        composable(route = "userPageScreen") {
            val userPageViewModel = UserPageViewModel(user)
            UserPageScreen(userPageViewModel)
        }
    }
}