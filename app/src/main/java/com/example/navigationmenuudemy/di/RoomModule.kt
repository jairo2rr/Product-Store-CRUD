package com.example.navigationmenuudemy.di

import android.content.Context
import androidx.room.Room
import com.example.navigationmenuudemy.data.database.StoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "store_db"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, StoreDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideCategoryDao(db:StoreDatabase)=db.getCategoryDao()

    @Provides
    @Singleton
    fun provideProductDao(db:StoreDatabase)=db.getProductDao()

    @Provides
    @Singleton
    fun provideSaleDao(db:StoreDatabase)=db.getSaleDao()

    @Provides
    @Singleton
    fun provideSaleDescriptionDao(db:StoreDatabase)=db.getSaleDescriptionDao()
}