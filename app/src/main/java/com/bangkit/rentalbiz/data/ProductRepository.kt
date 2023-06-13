package com.bangkit.rentalbiz.data

import android.util.Log
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem
import com.bangkit.rentalbiz.data.local.room.CartDao
import com.bangkit.rentalbiz.data.local.room.FavoriteDao
import com.bangkit.rentalbiz.data.remote.TransactionRequest
import com.bangkit.rentalbiz.data.remote.response.*
import com.bangkit.rentalbiz.data.remote.retrofit.ApiService
import com.bangkit.rentalbiz.utils.Helper.reduceFileImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProductRepository(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val cartDao: CartDao
) {

    suspend fun addCarts(products: List<CartItem>): Flow<Boolean> {
        return try {
            cartDao.insertCarts(products)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun addToCart(product: CartItem): Flow<Boolean> {
        return try {
            cartDao.insertCart(product)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun getAllCartItems(): Flow<List<CartItem>?> {
        return try {
            val result = cartDao.getAllCart()
            flowOf(result)
        } catch (e: Exception) {
            flowOf(null)
        }
    }

    suspend fun updateCartItem(product: CartItem): Flow<Boolean> {
        return try {
            cartDao.updateItem(product)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun deleteCartItem(id: String): Flow<Boolean> {
        return try {
            cartDao.deleteItem(id)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun getAllFavorite(): Flow<List<FavoriteItem>?> {
        return try {
            val result = favoriteDao.getAllFavorite()
            flowOf(result)
        } catch (e: Exception) {
            flowOf(null)
        }
    }

    suspend fun getFavoriteById(id: String): Flow<List<FavoriteItem>?> {
        return try {
            val result = favoriteDao.getFavoriteById(id)
            flowOf(result)
        } catch (e: Exception) {
            flowOf(null)
        }
    }

    suspend fun saveFavorite(product: FavoriteItem): Flow<Boolean> {
        return try {
            favoriteDao.insertFavorite(product)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun deleteFavorite(productId: String): Flow<Boolean> {
        return try {
            favoriteDao.deleteFavorite(productId)
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun deleteAllFavorite(): Flow<Boolean> {
        return try {
            favoriteDao.deleteAllFavorite()
            flowOf(true)
        } catch (e: Exception) {
            flowOf(false)
        }
    }

    suspend fun getAllProducts(): Flow<ProductsResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts().execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    flowOf(null)
                }
            } catch (e: Exception) {
                flowOf(null)
            }
        }

    suspend fun getProduct(productId: String): Flow<ProductResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProduct(productId).execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    flowOf(null)
                }
            } catch (e: Exception) {
                flowOf(null)
            }
        }

    suspend fun getProductByName(name: String): Flow<ProductsResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts(name = name).execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    flowOf(null)
                }
            } catch (e: Exception) {
                flowOf(null)
            }
        }

    suspend fun filterProduct(
        name: String,
        category: String,
        minPrice: String,
        maxPrice: String,
        city: String,
    ): Flow<ProductsResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProducts(
                name = name,
                category = category,
                minPrice = minPrice,
                maxPrice = maxPrice,
                city = city
            ).execute()
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                flowOf(null)
            }
        } catch (e: Exception) {
            flowOf(null)
        }
    }

    suspend fun createProduct(
        image: File,
        name: String,
        description: String,
        price: String,
        category: String,
        requirement: String,
        stock: String,
    ): Flow<ProductResponse?> = withContext(Dispatchers.IO) {
        try {
            if (image.exists()) {
                val reducedSize = if (image.length() > 1000000) reduceFileImage(image) else image
                val requestImage = reducedSize.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "image",
                    image.name,
                    requestImage
                )
                val nameBody = name.toRequestBody("text/plain".toMediaType())
                val descriptionBody = description.toRequestBody("text/plain".toMediaType())
                val priceBody = price.toRequestBody("text/plain".toMediaType())
                val categoryBody = category.toRequestBody("text/plain".toMediaType())
                val requirementBody = requirement.toRequestBody("text/plain".toMediaType())
                val stockBody = stock.toRequestBody("text/plain".toMediaType())

                val response = apiService.postProduct(
                    image = imageMultipart,
                    name = nameBody,
                    deskripsi = descriptionBody,
                    harga = priceBody,
                    kategori = categoryBody,
                    persyaratan = requirementBody,
                    stok = stockBody
                ).execute()

                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    flowOf(null)
                }
            } else {
                flowOf(null)
            }
        } catch (e: Exception) {
            Log.e("REPOSITORY", e.message.toString())
            flowOf(null)
        }
    }

    suspend fun deleteProduct(
        productId: String,
    ): Flow<ProductResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteProduct(productId).execute()
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                flowOf(null)
            }
        } catch (e: Exception) {
            Log.e("MODEL deleteProduct", e.message.toString())
            flowOf(null)
        }
    }

    suspend fun transaction(
        transactionRequest: TransactionRequest
    ): Flow<TransactionProcessResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.transaction(transactionRequest).execute()
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                Log.e("Product Repository::", response.body()?.error.toString())
                flowOf(null)
            }
        } catch (e: Exception) {
            Log.e("Product Repository::", e.message.toString())
            flowOf(null)
        }
    }

    suspend fun transactionById(id: String): Flow<TransactionByIdResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTransactionById(id).execute()
                if (response.isSuccessful) {
                    flowOf(response.body())
                } else {
                    flowOf(null)
                }
            } catch (e: Exception) {
                Log.e("Product Repository::", e.message.toString())
                flowOf(null)
            }
        }

    suspend fun transactionList(): Flow<TransactionListResponse?> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getTransactionList().execute()
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                flowOf(null)
            }
        } catch (e: Exception) {

            Log.e("Product Repository::", e.message.toString())
            flowOf(null)
        }
    }
}