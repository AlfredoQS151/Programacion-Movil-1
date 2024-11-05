package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.example.inventory.ui.theme.ListaAppTheme

class MainActivity : ComponentActivity() {

    // Instancia de la base de datos utilizando Room, creada solo cuando se accede por primera vez.
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, // Especifica la clase de base de datos
            "DB_Task" // Nombre de la base de datos
        ).fallbackToDestructiveMigration().build() // Destruye y recrea la base de datos si hay una migración incompatible (solo para desarrollo)
    }

    // Instancia de ViewModel, creada usando ToDoViewModelFactory para inyectar el repositorio de tareas.
    private val viewModel: ToDoViewModel by viewModels {
        ToDoViewModelFactory(TaskRepository(database.taskDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplica el tema de la aplicación y establece el contenido principal.
            ListaAppTheme {
                ToDoListApp(viewModel = viewModel)
            }
        }
    }
}
