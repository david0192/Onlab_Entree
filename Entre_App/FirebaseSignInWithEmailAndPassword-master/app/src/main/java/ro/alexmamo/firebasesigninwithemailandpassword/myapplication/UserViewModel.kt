package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import androidx.compose.runtime.*
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

     fun addGuestUser(user:User){
        val apiService = APIService.getInstance()
        try {
            viewModelScope.launch {
                apiService.AddGuestUser(user)
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}