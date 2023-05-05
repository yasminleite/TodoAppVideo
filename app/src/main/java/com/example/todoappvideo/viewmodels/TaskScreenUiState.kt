package com.example.todoappvideo.viewmodels

import com.example.todoappvideo.models.Task

data class TaskScreenUiState(
    val allTasks: List<Task> = listOf(
        Task("Preparar aula de PDM", isImportant = true),
        Task("Gravar aula de PDM"),
        Task("Corrigir prova de ADC", isImportant = true, isCompleted = true),
        Task("Elaborar projeto de ADC")
)
)
