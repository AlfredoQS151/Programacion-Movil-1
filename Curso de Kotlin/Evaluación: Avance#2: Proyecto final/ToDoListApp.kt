package com.example.inventory

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToDoListApp(viewModel: ToDoViewModel) {
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600 && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isTablet) {
        TabletLayout(viewModel = viewModel)
    } else {
        PhoneLayout(viewModel = viewModel)
    }
}

@Composable
fun TabletLayout(viewModel: ToDoViewModel) {
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showAddTaskDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) } // Variable para el estado de edición

    // Observa las tareas y la papelera usando collectAsState
    val tasks by viewModel.tasks.collectAsState()
    val trashTasks by viewModel.trashTasks.collectAsState()

    Row(modifier = Modifier.fillMaxSize()) {
        // Columna para la Papelera
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.trash), fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))

            if (trashTasks.isEmpty()) {
                Text(stringResource(id = R.string.no_trash_tasks))
            } else {
                trashTasks.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = task.title, modifier = Modifier.weight(1f))
                        Button(onClick = { viewModel.restoreTask(task) }) {
                            Text(stringResource(id = R.string.restore))
                        }
                        Button(onClick = { viewModel.deleteTaskPermanently(task) }) {
                            Text(stringResource(id = R.string.delete_permanently))
                        }
                    }
                }
            }
        }

        // Columna para Tareas Activas
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = R.string.my_tasks),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(onClick = {
                showAddTaskDialog = true
                isEditing = false
                selectedTask = null
            }, modifier = Modifier.padding(bottom = 8.dp)) {
                Text(stringResource(id = R.string.add_task))
            }

            Button(onClick = { viewModel.clearTasks() }, modifier = Modifier.padding(bottom = 16.dp)) {
                Text(stringResource(id = R.string.clear_tasks))
            }

            if (tasks.isEmpty()) {
                Text(stringResource(id = R.string.no_tasks))
            } else {
                tasks.forEach { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedTask = task },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = task.title, modifier = Modifier.weight(1f))
                        Button(onClick = {
                            selectedTask = task
                            showAddTaskDialog = true
                            isEditing = true
                        }) {
                            Text("Editar")
                        }
                        Button(onClick = { viewModel.deleteTask(task) }) {
                            Text(stringResource(id = R.string.delete))
                        }
                    }
                }
            }
        }

        // Columna para los detalles de la tarea seleccionada
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            selectedTask?.let { task ->
                Text(text = stringResource(id = R.string.task_details), fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "${stringResource(id = R.string.description)}: ${task.description}")
                Text(text = "${stringResource(id = R.string.date)}: ${task.date}")
                Text(text = "${stringResource(id = R.string.time)}: ${task.time}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { selectedTask = null }) {
                    Text(stringResource(id = R.string.close))
                }
            } ?: Text(text = stringResource(id = R.string.select_task_to_view_details))
        }
    }

    if (showAddTaskDialog) {
        TaskDialog(
            onDismiss = { showAddTaskDialog = false },
            onSaveTask = { task ->
                if (isEditing) {
                    viewModel.updateTask(task)
                } else {
                    viewModel.addTask(task)
                }
                showAddTaskDialog = false
            },
            task = selectedTask // Pasa la tarea seleccionada para edición si está en modo edición
        )
    }
}


@Composable
fun PhoneLayout(viewModel: ToDoViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.my_tasks),
        stringResource(id = R.string.trash)
    )

    // Observa las tareas y la papelera usando collectAsState
    val tasks by viewModel.tasks.collectAsState()
    val trashTasks by viewModel.trashTasks.collectAsState()

    var selectedTask by remember { mutableStateOf<Task?>(null) } // Variable para la tarea seleccionada

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(16.dp)
        )

        TabRow(selectedTabIndex = selectedTab, containerColor = MaterialTheme.colorScheme.surface) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        when (selectedTab) {
            0 -> TareasScreen(
                tasks = tasks,
                onAddTask = { newTask -> viewModel.addTask(newTask) },
                onDeleteTask = { task -> viewModel.deleteTask(task) },
                onEditTask = { task -> viewModel.updateTask(task) },
                onClearTasks = { viewModel.clearTasks() },
                onTaskClick = { task -> selectedTask = task } // Asigna la tarea seleccionada al hacer clic
            )
            1 -> PapeleraScreen(
                trashTasks = trashTasks,
                onRestoreTask = { task -> viewModel.restoreTask(task) },
                onDeleteTaskPermanently = { task -> viewModel.deleteTaskPermanently(task) },
                onClearTrash = { viewModel.clearTrash() }
            )
        }
    }

    // Dialogo para mostrar los detalles de la tarea seleccionada
    selectedTask?.let { task ->
        AlertDialog(
            onDismissRequest = { selectedTask = null },
            title = { Text("Detalles de Tarea") },
            text = {
                Column {
                    Text("Título: ${task.title}")
                    Text("Descripción: ${task.description}")
                    Text("Fecha: ${task.date}")
                    Text("Hora: ${task.time}")
                }
            },
            confirmButton = {
                Button(onClick = { selectedTask = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}
