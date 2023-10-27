package com.example.saki.domain.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ListItemDataModel(
    var id:Int = 0,
    var selected:MutableState<Boolean> = mutableStateOf(false)
)