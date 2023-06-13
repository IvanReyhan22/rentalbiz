package com.bangkit.rentalbiz.ui.screen.manage

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.rentalbiz.R
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.remote.response.Product
import com.bangkit.rentalbiz.ui.common.UiState
import com.bangkit.rentalbiz.utils.Helper.uriToFile
import com.bangkit.rentalbiz.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InventoryForm(
    var name: String,
    var price: String,
    var stock: String,
    var category: String,
    var description: String,
    var requirenment: String,
)

@HiltViewModel
class ManageProductScreenViewModel @Inject constructor(
    private val context: Context,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Product>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<Product>> get() = _uiState

    private val _inventoryForm = mutableStateOf(InventoryForm("", "", "", "", "", ""))
    val inventoryForm: State<InventoryForm> get() = _inventoryForm

    private val _image = mutableStateOf(Uri.EMPTY)
    val image: State<Uri> get() = _image

    init {
        getImage()
    }

    fun getProduct(productId: String) {
        _uiState.value = UiState.Loading
        if (productId.isNotEmpty()) {
            viewModelScope.launch {
                productRepository.getProduct(productId = productId).collect { response ->
                    _uiState.value = when {
                        response?.item != null -> {
                            val product = response.item
                            _inventoryForm.value = InventoryForm(
                                name = product.nama.toString(),
                                price = product.harga.toString(),
                                stock = product.stok.toString(),
                                category = product.kategori.toString(),
                                description = product.deskripsi.toString(),
                                requirenment = product.persyaratan.toString()
                            )
                            UiState.Success(product)
                        }
                        response?.success == false -> {
                            UiState.Error(response.message ?: "Error Occurred")
                        }
                        else -> {
                            UiState.Error(context.getString(R.string.product_not_found))
                        }
                    }
                }
            }
        } else {
            _uiState.value = UiState.Error(context.getString(R.string.product_not_found))
        }
    }

    fun updateForm(
        name: String = _inventoryForm.value.name,
        price: String = _inventoryForm.value.price,
        stock: String = _inventoryForm.value.stock,
        category: String = _inventoryForm.value.category,
        description: String = _inventoryForm.value.description,
        requirement: String = _inventoryForm.value.requirenment,
    ) {
        _inventoryForm.value = InventoryForm(name, price, stock, category, description, requirement)
    }

    fun getImage() {
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            val data = userPreference.getImage()
            _image.value = data
        }
    }

    private fun deleteImage() {
        viewModelScope.launch {
            val userPreference = UserPreference(context)
            userPreference.deleteImage()
        }
    }

    fun addProduct(onSuccess: () -> Unit, onFailed: () -> Unit) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.createProduct(
                image = uriToFile(_image.value, context),
                name = _inventoryForm.value.name,
                description = _inventoryForm.value.description,
                price = _inventoryForm.value.price,
                category = _inventoryForm.value.category,
                requirement = _inventoryForm.value.requirenment,
                stock = _inventoryForm.value.stock
            ).collect { response ->
                when {
                    response?.message != null -> {
                        deleteImage()
                        onSuccess()
                    }
                    response?.error != null -> {
                        onFailed()
                        _uiState.value = UiState.Error(response.error)
                    }
                    else -> {
                        _uiState.value =
                            UiState.Error(context.getString(R.string.product_not_found))
                    }
                }
            }
        }
    }

    fun deleteProduct(productId: String, onSuccess: () -> Unit, onFailed: () -> Unit) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.deleteProduct(productId = productId).collect { response ->
                when {
                    response?.message != null -> {
                        deleteImage()
                        onSuccess()
                    }
                    response?.error != null -> {
                        onFailed()
                        _uiState.value = UiState.Error(response.error)
                    }
                    else -> {
                        _uiState.value =
                            UiState.Error(context.getString(R.string.product_not_found))
                    }
                }
            }
        }
    }

    fun updateProduct(onSuccess: () -> Unit, onFailed: () -> Unit){
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            productRepository.createProduct(
                image = uriToFile(_image.value, context),
                name = _inventoryForm.value.name,
                description = _inventoryForm.value.description,
                price = _inventoryForm.value.price,
                category = _inventoryForm.value.category,
                requirement = _inventoryForm.value.requirenment,
                stock = _inventoryForm.value.stock
            ).collect { response ->
                when {
                    response?.message != null -> {
                        deleteImage()
                        onSuccess()
                    }
                    response?.error != null -> {
                        onFailed()
                        _uiState.value = UiState.Error(response.error)
                    }
                    else -> {
                        _uiState.value =
                            UiState.Error(context.getString(R.string.product_not_found))
                    }
                }
            }
        }
    }

}