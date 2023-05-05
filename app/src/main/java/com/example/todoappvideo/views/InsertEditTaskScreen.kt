package com.example.todoappvideo.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.todoappvideo.viewmodels.TasksViewModel

@Composable
fun InsertEditTaskScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel
){
    val uiState by tasksViewModel.insertEditScreenUiState.collectAsState()
    InsertEditForm(
        name = uiState.taskName,
        important = uiState.isImportant,
        onNameChange = {tasksViewModel.onTaskNameChange(it)},
        onImportantChange = {tasksViewModel.onTaskImportantChange(it)}
    )
}

@Composable
fun InsertEditForm(
    name: String,
    important: Boolean,
    onNameChange: (String) -> Unit,
    onImportantChange: (Boolean) -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                label = { Text(text = "Task Name") },
                value = name, onValueChange = onNameChange
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = important, onCheckedChange = onImportantChange)
                Text(text = "Important Task")
            }
        }
    }
}


