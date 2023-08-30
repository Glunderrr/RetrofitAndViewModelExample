package files.app.retrofitexample.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import files.app.retrofitexample.api.ApiManager
import files.app.retrofitexample.dataClases.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen() {
    var product by remember {
        mutableStateOf(Product())
    }
    CoroutineScope(Dispatchers.IO).launch {
        product = ApiManager.getInfoById(2)
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            items(product.images) {
                Box(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                    Image(
                        painter = rememberImagePainter(it),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .graphicsLayer {

                            }
                    ).also { _ ->
                        Log.d("MY_IMAGE", it)
                    }
                }
            }
        }
    )
}