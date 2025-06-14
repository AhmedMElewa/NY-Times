package com.elewa.nytimes.core.di

import android.content.Context
import androidx.room.Room
import com.elewa.nytimes.core.data.soruce.local.NYNewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Volatile
    private var instance: NYNewsDatabase? = null
    private const val DATABASE_NAME = "News_NY"

    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): NYNewsDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }
    }

    private fun buildDatabase(context: Context): NYNewsDatabase {
        return Room.databaseBuilder(context, NYNewsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }
}