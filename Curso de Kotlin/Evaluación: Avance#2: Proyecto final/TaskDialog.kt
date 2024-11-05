package com.example.inventory

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Locale

// Composable que muestra un cuadro de diálogo para agregar o editar una tarea.
// Permite ingresar título, descripción, fecha y hora, ya sea para una tarea nueva o para editar una existente.
@Composable
fun TaskDialog(
    onDismiss: () -> Unit, // Acción a ejecutar al cerrar el diálogo
    onSaveTask: (Task) -> Unit, // Acción a ejecutar al guardar la tarea
    task: Task? = null // Tarea opcional para prellenar los campos si se está editando
) {
    // Variables para almacenar los valores de los campos
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var date by remember { mutableStateOf(task?.date ?: "") }
    var time by remember { mutableStateOf(task?.time ?: "") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Configuración del selector de fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Configuración del selector de hora
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    AlertDialog(
        onDismissRequest = { onDismiss() }, // Cierra el diálogo al hacer clic fuera de él
        title = { Text(text = if (task == null) "Añadir Tarea" else "Editar Tarea", fontSize = 20.sp) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Campo de entrada para el título
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de entrada para la descripción
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Botón para abrir el selector de fecha
                Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = if (date.isEmpty()) "Seleccionar fecha" else "Fecha: $date")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botón para abrir el selector de hora
                Button(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = if (time.isEmpty()) "Seleccionar hora" else "Hora: $time")
                }
            }
        },
        // Botón para confirmar y guardar la tarea si todos los campos están llenos
        confirmButton = {
            Button(onClick = {
                if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                    onSaveTask(Task(id = task?.id ?: 0, title = title, description = description, date = date, time = time))
                    onDismiss()
                }
            }) {
                Text("Guardar")
            }
        },
        // Botón para cancelar y cerrar el diálogo
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
