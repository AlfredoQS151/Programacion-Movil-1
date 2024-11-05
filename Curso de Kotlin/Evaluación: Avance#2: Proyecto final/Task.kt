package com.example.inventory

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa la entidad de una tarea en la base de datos, con campos relevantes para almacenar información de cada tarea.
// Se usa Room para gestionar el almacenamiento persistente de esta entidad.
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID único de la tarea, generado automáticamente
    val title: String, // Título de la tarea
    val description: String, // Descripción de la tarea
    val date: String, // Fecha asociada a la tarea
    val time: String, // Hora asociada a la tarea
    val isTrashed: Boolean = false // Estado que indica si la tarea está en la papelera
)
