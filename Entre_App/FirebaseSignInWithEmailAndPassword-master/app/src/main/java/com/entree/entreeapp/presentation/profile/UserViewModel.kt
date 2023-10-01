package com.entree.entreeapp.presentation.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.entree.entreeapp.apiservice.APIService
import com.entree.entreeapp.apiservice.User


class UserViewModel : ViewModel() {
    var errorMessage: String by mutableStateOf("")

     suspend fun addGuestUser(user:User){
         errorMessage=""
        val apiService = APIService.getInstance()
        try {
                apiService.AddGuestUser(user)
            }
        catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}