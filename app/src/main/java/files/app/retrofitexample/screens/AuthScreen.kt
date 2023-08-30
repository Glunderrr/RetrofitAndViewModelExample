package files.app.retrofitexample.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import files.app.retrofitexample.R
import files.app.retrofitexample.dataClases.User
import files.app.retrofitexample.ui.theme.Blue
import files.app.retrofitexample.ui.theme.DarkBlue
import files.app.retrofitexample.ui.theme.PurpleGrey40
import files.app.retrofitexample.viewModels.AuthScreenViewModel
import files.app.retrofitexample.viewModels.PasswordFiledController
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AuthScreen(AuthScreenViewModel(), remember { mutableStateOf(User()) }) {}
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel,
    userData: MutableState<User>,
    navigationFun: () -> Unit,
) {
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = viewModel.snackBarHostState) {
            Snackbar(
                snackbarData = it,
                modifier = Modifier.padding(20.dp),
                containerColor = PurpleGrey40,
                shape = RoundedCornerShape(20.dp),
                contentColor = Color.LightGray
            )
        }
    }) {
        val spaceValue = 12.dp
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue),
            contentAlignment = Alignment.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(spaceValue)) {
                Image(
                    painter = painterResource(id = R.drawable.aut),
                    contentDescription = "enterImage",
                    modifier = Modifier
                        .size(300.dp)
                        .shadow(elevation = 30.dp, clip = true, shape = CircleShape)
                        .clip(shape = CircleShape)
                )
                Spacer(modifier = Modifier.height(spaceValue))
                Text(text = "Welcome user", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                LoginField(viewModel.userName)
                PasswordField(viewModel.passwordFiledViewModel)
                Row(modifier = Modifier.width(300.dp), horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.userRequest(navigationFun, userData)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                    ) {
                        Text(text = "Sign in", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    login: MutableState<String>,
) {
    TextField(
        value = login.value,
        onValueChange = { login.value = it },
        modifier = Modifier
            .clip(shape = CircleShape)
            .border(border = BorderStroke(2.dp, Color.Gray), shape = CircleShape)
            .width(300.dp),
        placeholder = { Text(text = "User Name") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    viewModel: PasswordFiledController,
) {
    TextField(
        value = viewModel.password.value,
        onValueChange = { viewModel.password.value = it },
        modifier = Modifier
            .clip(CircleShape)
            .border(border = BorderStroke(2.dp, Color.Gray), shape = CircleShape)
            .width(300.dp),
        placeholder = { Text(text = "Password") },
        visualTransformation = viewModel.getVisualState(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        trailingIcon = {
            IconButton(onClick = { viewModel.flipShow() }) {
                Icon(
                    painter = painterResource(id = viewModel.getVisibleImage()),
                    contentDescription = "visibility",
                )
            }
        }
    )
}