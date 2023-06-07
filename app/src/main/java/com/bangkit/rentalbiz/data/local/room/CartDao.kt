package com.bangkit.rentalbiz.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.rentalbiz.data.local.entity.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCart(cartItem: CartItem)

    @Query("SELECT * FROM cart")
    suspend fun getAllCart(): List<CartItem>

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getCartItemById(id: String): List<CartItem>

    @Query("DELETE FROM cart WHERE id = :id")
    suspend fun deleteItem(id: String)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCart()
}