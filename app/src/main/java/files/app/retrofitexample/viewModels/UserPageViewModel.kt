package files.app.retrofitexample.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import files.app.retrofitexample.api.ApiManager
import files.app.retrofitexample.dataClases.Product
import files.app.retrofitexample.dataClases.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserPageViewModel(val user: MutableState<User>) : ViewModel() {

    val searchingName = mutableStateOf("")
    val expandedState = mutableStateOf(false)
    val searchList = mutableStateOf(emptyList<Product>())
    val productList = MutableStateFlow(emptyList<Product>())

    init {
        viewModelScope.launch { productList.value = ApiManager.getListOfProducts() }
    }

    fun updateProductList(product: Product) {
        productList.value = listOf(product) + productList.value
    }

    fun searchProductByName(typedText: String) {
        viewModelScope.launch {
            searchList.value = ApiManager.getProductByName(typedText)
        }
    }
}