package com.example.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable que muestra la pantalla principal de tareas activas.
// Permite agregar, editar, eliminar y limpiar tareas, así como ver los detalles de una tarea.
@Composable
fun TareasScreen(
    tasks: List<Task>, // Lista de tareas activas
    onAddTask: (Task) -> Unit, // Acción para agregar una nueva tarea
    onDeleteTask: (Task) -> Unit, // Acción para eliminar una tarea
    onEditTask: (Task) -> Unit, // Acción para editar una tarea
    onClearTasks: () -> Unit, // Acción para eliminar todas las tareas
    onTaskClick: (Task) -> Unit // Acción al hacer clic en una tarea para ver detalles
) {
    var showDialog by remember { mutableStateOf(false) } // Estado para controlar la visibilidad del diálogo
    var selectedTask by remember { mutableStateOf<Task?>(null) } // Tarea seleccionada para editar o ver
    var isEditing by remember { mutableStateOf(false) } // Estado para controlar si el diálogo está en modo edición

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para agregar una nueva tarea
        Button(onClick = {
            showDialog = true
            isEditing = false
            selectedTask = null
        }) {
            Text("Añadir Tarea")
        }

        // Botón para limpiar todas las tareas
        Button(onClick = onClearTasks) { Text("Limpiar Tareas") }

        // Título de la sección
        Text(
            text = "Mis Tareas",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de tareas activas
        Column(modifier = Modifier.fillMaxWidth()) {
            tasks.forEach { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onTaskClick(task) }, // Llama a onTaskClick para mostrar detalles
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = task.title, modifier = Modifier.weight(1f)) // Título de la tarea
                    Button(onClick = {
                        selectedTask = task
                        showDialog = true
                        isEditing = true
                    }) {
                        Text("Editar")
                    }
                    Button(onClick = { onDeleteTask(task) }) {
                        Text("Eliminar")
                    }
                }
            }
        }

        // Diálogo para agregar o editar una tarea
        if (showDialog) {
            TaskDialog(
                onDismiss = { showDialog = false },
                onSaveTask = { task ->
                    if (isEditing) {
                        onEditTask(task)
                    } else {
                        onAddTask(task)
                    }
                    showDialog = false
                },
                task = selectedTask // Pasa la tarea seleccionada en modo edición si aplica
            )
        }
    }
}
