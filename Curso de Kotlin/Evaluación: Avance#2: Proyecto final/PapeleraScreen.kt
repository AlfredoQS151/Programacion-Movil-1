// PapeleraScreen.kt
package com.example.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable para mostrar la pantalla de la papelera de tareas eliminadas.
// Permite restaurar tareas, eliminarlas permanentemente o limpiar toda la papelera.
@Composable
fun PapeleraScreen(
    trashTasks: List<Task>, // Lista de tareas en la papelera
    onRestoreTask: (Task) -> Unit, // Acción para restaurar una tarea
    onDeleteTaskPermanently: (Task) -> Unit, // Acción para eliminar una tarea permanentemente
    onClearTrash: () -> Unit // Acción para limpiar todas las tareas de la papelera
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Papelera",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de tareas en la papelera
        Column(modifier = Modifier.fillMaxWidth()) {
            trashTasks.forEach { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = task.title, modifier = Modifier.weight(1f)) // Título de la tarea
                    Button(onClick = { onRestoreTask(task) }) { Text("Restaurar") } // Botón para restaurar la tarea
                    Button(onClick = { onDeleteTaskPermanently(task) }) { Text("Eliminar") } // Botón para eliminar definitivamente
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para limpiar todas las tareas de la papelera
        Button(onClick = onClearTrash) {
            Text("Limpiar Papelera")
        }
    }
}
