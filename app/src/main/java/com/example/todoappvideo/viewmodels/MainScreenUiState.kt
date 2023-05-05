package com.example.todoappvideo.viewmodels

import androidx.annotation.DrawableRes
import com.example.todoappvideo.R

data class MainScreenUiState(
    val screenName: String = "Task List",
    @DrawableRes val fabIcon: Int = R.drawable.baseline_add_24
)
