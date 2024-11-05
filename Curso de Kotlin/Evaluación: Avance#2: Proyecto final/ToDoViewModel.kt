package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel para gestionar las tareas en la aplicación.
// Proporciona flujos de datos para las tareas activas y las de la papelera,
// y funciones para manejar las operaciones CRUD sobre las tareas.
class ToDoViewModel(private val repository: TaskRepository) : ViewModel() {

    // Flujo de tareas activas (no en la papelera).
    val tasks: StateFlow<List<Task>> = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Flujo de tareas en la papelera.
    val trashTasks: StateFlow<List<Task>> = repository.getTrashTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Agrega una nueva tarea al repositorio.
    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    // Mueve una tarea a la papelera en lugar de eliminarla.
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.moveToTrash(task)
        }
    }

    // Restaura una tarea de la papelera a las tareas activas.
    fun restoreTask(task: Task) {
        viewModelScope.launch {
            repository.restoreTask(task)
        }
    }

    // Elimina permanentemente una tarea del repositorio.
    fun deleteTaskPermanently(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    // Elimina todas las tareas activas del repositorio.
    fun clearTasks() {
        viewModelScope.launch {
            repository.clearTasks()
        }
    }

    // Limpia todas las tareas de la papelera.
    fun clearTrash() {
        viewModelScope.launch {
            repository.clearTrash()
        }
    }

    // Actualiza una tarea existente en el repositorio.
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}
