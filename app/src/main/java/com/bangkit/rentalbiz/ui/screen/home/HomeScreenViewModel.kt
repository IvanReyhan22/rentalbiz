package com.bangkit.rentalbiz.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeScreenViewModel() : ViewModel() {
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun updateQuery(newQuery:String){
        _query.value = newQuery
    }
}