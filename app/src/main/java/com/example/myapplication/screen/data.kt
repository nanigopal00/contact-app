package com.example.myapplication.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class dataseanglescreen(
    var image: MutableState<ByteArray> = mutableStateOf(ByteArray(0)),

)
