package com.bangkit.rentalbiz.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.rentalbiz.data.local.entity.CartItem
import com.bangkit.rentalbiz.data.local.entity.FavoriteItem

@Database(
    entities = [FavoriteItem::class, CartItem::class],
    version = 2,
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java, "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}