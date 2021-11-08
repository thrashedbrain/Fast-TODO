package com.fast.todoapp.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fast.todoapp.data.models.TaskEntity
import com.fast.todoapp.data.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {

    fun getTasks(): LiveData<List<TaskEntity>> {
        return tasksRepository.getAllTasks()
    }

    fun insert(entity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.insertTask(entity)
        }
    }

    fun delete(entity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.deleteTask(entity)
        }
    }

    fun update(entity: TaskEntity) {
        viewModelScope.launch {
            tasksRepository.updateTask(entity)
        }
    }

}