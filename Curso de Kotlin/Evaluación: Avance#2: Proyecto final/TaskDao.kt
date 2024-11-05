package com.example.inventory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Interfaz de acceso a datos (DAO) para realizar operaciones CRUD en la entidad Task.
@Dao
interface TaskDao {

    // Inserta una nueva tarea en la base de datos.
    @Insert
    suspend fun insertTask(task: Task)

    // Elimina una tarea específica de la base de datos.
    @Delete
    suspend fun deleteTask(task: Task)

    // Actualiza una tarea existente en la base de datos.
    @Update
    suspend fun updateTask(task: Task)

    // Recupera todas las tareas activas (no en la papelera) como un flujo de datos.
    @Query("SELECT * FROM tasks WHERE isTrashed = 0")
    fun getAllTasks(): Flow<List<Task>>

    // Recupera todas las tareas en la papelera como un flujo de datos.
    @Query("SELECT * FROM tasks WHERE isTrashed = 1")
    fun getTrashedTasks(): Flow<List<Task>>
}
