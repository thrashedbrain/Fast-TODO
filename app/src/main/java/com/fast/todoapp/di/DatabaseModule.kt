package com.fast.todoapp.di

import android.content.Context
import androidx.room.Room
import com.fast.todoapp.data.dao.TaskDao
import com.fast.todoapp.data.db.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MainDatabase {
        return Room.databaseBuilder(
            appContext,
            MainDatabase::class.java,
            "name"
        ).build()
    }

    @Provides
    fun provideArticleDao(mainDatabase: MainDatabase): TaskDao = mainDatabase.tasksDao()

}