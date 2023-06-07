package com.bangkit.rentalbiz.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFavorite(product: FavoriteItem)

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorite(): List<FavoriteItem>

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun getFavoriteById(id: String): List<FavoriteItem>

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavorite(id: String)

    @Query("DELETE FROM favorite")
    suspend fun deleteAllFavorite()
}