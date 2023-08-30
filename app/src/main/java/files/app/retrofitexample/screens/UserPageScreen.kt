package files.app.retrofitexample.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import files.app.retrofitexample.dataClases.User
import files.app.retrofitexample.ui.theme.Blue
import files.app.retrofitexample.viewModels.UserPageViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    val previewUser = remember { mutableStateOf(User()) }
    UserPageScreen(UserPageViewModel(previewUser))
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun UserPageScreen(viewModel: UserPageViewModel) {
    val productList = viewModel.productList.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Blue)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row {
                Image(
                    painter = rememberImagePainter(viewModel.user.value.image),
                    contentDescription = "User photo",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .height(150.dp), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = viewModel.user.value.firstName + " " + viewModel.user.value.lastName,
                        fontSize = 22.sp
                    )
                    Text(text = "Gender: ${viewModel.user.value.gender}", fontSize = 14.sp)
                    Text(text = "Email: ${viewModel.user.value.email}", fontSize = 12.sp)
                }
            }
            TextField(
                value = viewModel.searchingName.value,
                onValueChange = {
                    viewModel.searchingName.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(shape = CircleShape)
                    .border(border = BorderStroke(2.dp, Color.Gray), shape = CircleShape),
                placeholder = { Text(text = "Find new product!") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                    autoCorrect = true
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchProductByName(viewModel.searchingName.value)
                        viewModel.expandedState.value = true
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                },
                maxLines = 1,
            )
            DropdownMenu(
                expanded = viewModel.expandedState.value,
                onDismissRequest = { viewModel.expandedState.value = false },
            ) {
                viewModel.searchList.value.forEach {
                    DropdownMenuItem(text = { Text(text = it.title) },
                        onClick = {
                            viewModel.updateProductList(it)
                            viewModel.expandedState.value = false
                        })
                }
            }
            Text(
                text = "All your products:",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(
                content = {
                    items(productList.value) {
                        Log.d("Product list", "$it")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = it.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = it.description)
                        }
                    }
                },
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
        }
    }
}