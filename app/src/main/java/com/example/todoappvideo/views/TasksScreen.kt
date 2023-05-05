package com.example.todoappvideo.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.todoappvideo.R
import com.example.todoappvideo.models.Task
import com.example.todoappvideo.viewmodels.TasksViewModel

@Composable
fun TaskScreen(
    navController: NavController,
    tasksViewModel: TasksViewModel
) {

    val uiState by tasksViewModel.taskScreenUiState.collectAsState()

    taskList(
        tasks = uiState.allTasks,
        onCompletedChange = {task, isCompleted ->
        tasksViewModel.onTaskIsCompletedChange(task, isCompleted)
        },
        onEditTask = {tasksViewModel.editTask(it, navController)}
    )
}

@Composable
fun taskList(
    tasks: List<Task>,
    onCompletedChange: (Task, Boolean) -> Unit,
    onEditTask: (Task) -> Unit

) {
    LazyColumn(){
        items(tasks) { task->
            TaskEntry(task = task, onCompletedChange = {onCompletedChange(task, it)}, onEditTask = {onEditTask(task)})
            }
        }
    }

@Composable
fun TaskEntry(
    task: Task,
    onCompletedChange: (Boolean) -> Unit,
    onEditTask: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clickable { onEditTask() }, elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Row() {
                if(task.isImportant) {
                    Icon(painter = painterResource(id = R.drawable.baseline_priority_high_24), contentDescription = "high priority")
                }
                Text(text = task.name)
            }
            Checkbox(checked = task.isCompleted, onCheckedChange = onCompletedChange)
        }
    }
}

