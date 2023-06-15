package com.bangkit.rentalbiz.data.local.room

import androidx.room.*
import com.bangkit.rentalbiz.data.local.entity.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartItem: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(carts: List<CartItem>)

    @Query("SELECT * FROM cart")
    suspend fun getAllCart(): List<CartItem>

    @Update
    suspend fun updateItem(cart:CartItem)

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getCartItemById(id: String): List<CartItem>

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteItem(id: String)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCart()
}