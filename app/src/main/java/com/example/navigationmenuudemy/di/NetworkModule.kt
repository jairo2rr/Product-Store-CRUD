package com.example.navigationmenuudemy.di

import com.example.navigationmenuudemy.data.StoreRepository
import com.example.navigationmenuudemy.data.database.dao.CategoryDao
import com.example.navigationmenuudemy.data.database.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRepository(categoryDao: CategoryDao,productDao: ProductDao):StoreRepository{
        return StoreRepository(categoryDao,productDao)
    }
}