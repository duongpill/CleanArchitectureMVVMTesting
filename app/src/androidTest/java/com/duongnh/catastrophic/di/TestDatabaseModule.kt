package com.duongnh.catastrophic.di

import android.content.Context
import androidx.room.Room
import com.duongnh.catastrophic.data.data_source.local.AppDatabase
import com.duongnh.catastrophic.data.data_source.local.dao.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule{
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideCatDao(appDatabase: AppDatabase): CatDao {
        return appDatabase.catDao
    }
}