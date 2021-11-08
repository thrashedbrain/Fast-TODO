package com.fast.todoapp.data.repository

import com.fast.todoapp.data.dao.TaskDao
import com.fast.todoapp.data.models.TaskEntity
import javax.inject.Inject

class TasksRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun insertTask(entity: TaskEntity) = taskDao.insert(entity)

    suspend fun deleteTask(entity: TaskEntity) = taskDao.delete(entity)

    suspend fun updateTask(entity: TaskEntity) = taskDao.update(entity)
}