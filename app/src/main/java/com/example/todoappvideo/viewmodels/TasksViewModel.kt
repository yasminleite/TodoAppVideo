package com.example.todoappvideo.viewmodels

import android.provider.ContactsContract.Intents.Insert
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.todoappvideo.models.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.todoappvideo.R

class TasksViewModel: ViewModel() {

    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    private var _taskScreenUiState: MutableStateFlow<TaskScreenUiState> = MutableStateFlow(
        TaskScreenUiState()
    )
    val taskScreenUiState: StateFlow<TaskScreenUiState> = _taskScreenUiState.asStateFlow()

    private var _insertEditTaskScreenUiState: MutableStateFlow<InsertEditScreenUiState> =
        MutableStateFlow(
            InsertEditScreenUiState()
        )
    val insertEditScreenUiState: StateFlow<InsertEditScreenUiState> = _insertEditTaskScreenUiState.asStateFlow()

    var editTask: Boolean = false
    var taskToEdit: Task = Task("")

    fun onTaskNameChange(newTaskName: String) {
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(taskName = newTaskName)
        }
    }

    fun onTaskImportantChange(newTaskImportance: Boolean) {
        _insertEditTaskScreenUiState.update { currentState ->
            currentState.copy(isImportant = newTaskImportance)
        }
    }

    fun onTaskIsCompletedChange(updatedTask: Task, newTaskCompleted: Boolean) {
        val allTasksTemp = _taskScreenUiState.value.allTasks.toMutableList()
        var pos = -1
        allTasksTemp.forEachIndexed { index, task ->
            if (updatedTask == task) {
                pos = index
            }
        }
        allTasksTemp.removeAt(pos)
        allTasksTemp.add(pos, updatedTask.copy(isCompleted = newTaskCompleted))
        _taskScreenUiState.update { currentState ->
            currentState.copy(allTasks = allTasksTemp.toList())
        }
    }

    fun navigate(navController: NavController) {
        if (_mainScreenUiState.value.screenName == "Task List"){
            if(editTask){
                _mainScreenUiState.update { currentState->
                    currentState.copy(
                        screenName = "Update Task",
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            }else{
                _mainScreenUiState.update { currentState->
                    currentState.copy(
                        screenName = "Insert New Task",
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            }
            navController.navigate("insert_edit_task")
        }else {
            _mainScreenUiState.update { currentState->
                currentState.copy(
                    screenName = "Task List",
                    fabIcon = R.drawable.baseline_add_24,
                )
            }
            if(editTask){
                val allTasksTemp = _taskScreenUiState.value.allTasks.toMutableList()
                var pos = -1
                allTasksTemp.forEachIndexed { index, task ->
                    if (taskToEdit == task) {
                        pos = index
                    }
                }
                allTasksTemp.removeAt(pos)
                allTasksTemp.add(pos, taskToEdit.copy(
                    name = _insertEditTaskScreenUiState.value.taskName,
                    isImportant = _insertEditTaskScreenUiState.value.isImportant
                    ))
                _taskScreenUiState.update { currentState ->
                    currentState.copy(allTasks = allTasksTemp.toList())

                }
            }else{
                _taskScreenUiState.update { currentState->
                    currentState.copy(
                        allTasks = currentState.allTasks + Task(
                            name = _insertEditTaskScreenUiState.value.taskName,
                            isImportant = _insertEditTaskScreenUiState.value.isImportant
                        )
                    )

                }
            }
            _insertEditTaskScreenUiState.update {
                InsertEditScreenUiState()
            }
            editTask = false
            taskToEdit = Task("")
            navController.navigate("task_list"){
                popUpTo("task_list"){
                    inclusive = true
                }
            }
        }
    }

    fun editTask(task: Task, navController: NavController){
        editTask = true
        taskToEdit = task
        _insertEditTaskScreenUiState.update { currentState->
            currentState.copy(
                taskName = task.name,
                isImportant = task.isImportant,
            )
        }
        navigate(navController)
    }
}

