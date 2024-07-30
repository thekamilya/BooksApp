package com.example.books.presentation.ui.screens.home.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BookSelectionViewModel: ViewModel() {

    private val _chosenBookId = mutableStateOf("")
    val chosenBookId:State<String> = _chosenBookId


    fun setId(id:String){
        _chosenBookId.value = id
    }

}