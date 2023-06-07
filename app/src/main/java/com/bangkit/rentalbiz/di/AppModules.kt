package com.bangkit.rentalbiz.di

import android.content.Context
import com.bangkit.rentalbiz.data.ProductRepository
import com.bangkit.rentalbiz.data.UserRepository
import com.bangkit.rentalbiz.data.local.room.CartDao
import com.bangkit.rentalbiz.data.local.room.FavoriteDao
import com.bangkit.rentalbiz.data.local.room.ProductDatabase
import com.bangkit.rentalbiz.data.remote.retrofit.ApiConfig
import com.bangkit.rentalbiz.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object ContextModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideApi(@ApplicationContext context: Context): ApiService =
        ApiConfig.getApiService(context)
}

@Module
@InstallIn(ViewModelComponent::class)
object AppModules {
    @Provides
    fun providesUserRepository(apiService: ApiService): UserRepository = UserRepository(apiService)

    @Provides
    fun providesProductDatabase(@ApplicationContext context: Context): ProductDatabase =
        ProductDatabase.getDatabase(context)

    @Provides
    fun providesFavoriteDao(database: ProductDatabase): FavoriteDao = database.favoriteDao()

    @Provides
    fun providesCartDao(database: ProductDatabase): CartDao = database.cartDao()

    @Provides
    fun providesProductRepository(
        apiService: ApiService,
        favoriteDao: FavoriteDao,
        cartDao: CartDao,
    ): ProductRepository =
        ProductRepository(apiService = apiService, favoriteDao = favoriteDao, cartDao = cartDao)
}
