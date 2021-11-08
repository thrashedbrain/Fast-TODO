package com.fast.todoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fast.todoapp.data.models.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(entity: TaskEntity)

    @Update
    suspend fun update(entity: TaskEntity)

    @Delete
    suspend fun delete(entity: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table ORDER BY checked ASC")
    fun getAllTasks(): LiveData<List<TaskEntity>>
}