package com.example.todoappvideo.views

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoappvideo.viewmodels.TasksViewModel
import com.example.todoappvideo.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    tasksViewModel: TasksViewModel = viewModel()
) {
    val navController = rememberNavController()

    val uiState by tasksViewModel.mainScreenUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar() {
               Text(text = uiState.screenName)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                tasksViewModel.navigate(navController)
            }) {
                Icon(painter = painterResource(id = uiState.fabIcon), contentDescription = null)
            }
        },

    ) {
        NavHost(navController = navController, startDestination = "task_list") {
            composable("task_list"){
                TaskScreen(navController = navController, tasksViewModel = tasksViewModel)
            }
            composable("insert_edit_task"){
                InsertEditTaskScreen(navController = navController, tasksViewModel = tasksViewModel)

            }
        }
    }
}
