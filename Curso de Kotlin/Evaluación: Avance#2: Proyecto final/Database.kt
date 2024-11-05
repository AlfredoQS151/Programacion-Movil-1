package com.example.inventory

import androidx.room.Database
import androidx.room.RoomDatabase

// Define la base de datos de la aplicación con Room.
// Incluye la entidad Task y establece la versión de la base de datos.
@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Proporciona acceso al DAO de tareas (TaskDao) para realizar operaciones de base de datos.
    abstract fun taskDao(): TaskDao
}
