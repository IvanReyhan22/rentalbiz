package com.bangkit.rentalbiz.di

import android.content.Context
import com.bangkit.rentalbiz.data.UserRepository
import com.bangkit.rentalbiz.data.remote.retrofit.ApiConfig
import com.bangkit.rentalbiz.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}
