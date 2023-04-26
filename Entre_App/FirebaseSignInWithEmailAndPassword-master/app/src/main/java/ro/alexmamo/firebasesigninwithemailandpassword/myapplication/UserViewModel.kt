package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class UserViewModel : ViewModel() {
    private val _userList = mutableStateListOf<User>()
    var errorMessage: String by mutableStateOf("")
    private var _addeduser= mutableStateOf<User>(User(email = "", role="", passwordhash = ""))
    val addedUser: MutableState<User>
        get()=_addeduser
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

    suspend fun addGuestUser(user:User){
        val apiService = APIService.getInstance()
        try {
            _addeduser=apiService.AddGuestUser(user)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}