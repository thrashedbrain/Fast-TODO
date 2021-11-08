package com.fast.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fast.todoapp.data.dao.TaskDao
import com.fast.todoapp.data.models.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    abstract fun tasksDao(): TaskDao
}