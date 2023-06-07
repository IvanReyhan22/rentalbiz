package com.bangkit.rentalbiz.data

import android.util.Log
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem
import com.bangkit.rentalbiz.data.local.room.CartDao
import com.bangkit.rentalbiz.data.local.room.FavoriteDao
import com.bangkit.rentalbiz.data.remote.response.ProductResponse
import com.bangkit.rentalbiz.data.remote.response.ProductsResponse
import com.bangkit.rentalbiz.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class ProductRepository(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val cartDao: CartDao
) {

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

    suspend fun deleteCartItem(id:String):Flow<Boolean>{
        return try {
            cartDao.deleteItem(id)
            flowOf(true)
        }catch (e:Exception){
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

}