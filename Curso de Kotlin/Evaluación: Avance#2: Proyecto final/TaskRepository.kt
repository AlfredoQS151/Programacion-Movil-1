package com.example.inventory

import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    fun getTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getTrashTasks(): Flow<List<Task>> = taskDao.getTrashedTasks()

    suspend fun addTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun moveToTrash(task: Task) {
        val trashedTask = task.copy(isTrashed = true)
        taskDao.updateTask(trashedTask)
    }

    suspend fun restoreTask(task: Task) {
        val restoredTask = task.copy(isTrashed = false)
        taskDao.updateTask(restoredTask)
    }

    suspend fun deleteTaskPermanently(task: Task) = taskDao.deleteTask(task)

    suspend fun clearTasks() {
        val allTasks = taskDao.getAllTasks()
        allTasks.collect { tasks ->
            tasks.forEach { task ->
                taskDao.deleteTask(task)
            }
        }
    }

    suspend fun clearTrash() {
        val trashTasks = taskDao.getTrashedTasks()
        trashTasks.collect { tasks ->
            tasks.forEach { task ->
                taskDao.deleteTask(task)
            }
        }
    }

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
}
