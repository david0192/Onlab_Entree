package com.example.entreeapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class UserViewModel : ViewModel() {
    private val _userList = mutableStateListOf<User>()
    var errorMessage: String by mutableStateOf("")
    val userList: List<User>
        get() = _userList

    fun getUserList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _userList.clear()
                _userList.addAll(apiService.getUsers())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}