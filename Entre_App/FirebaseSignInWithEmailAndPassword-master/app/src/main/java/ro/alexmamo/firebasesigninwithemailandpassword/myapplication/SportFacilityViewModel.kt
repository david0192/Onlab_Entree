package ro.alexmamo.firebasesigninwithemailandpassword.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SportFacilityViewModel: ViewModel() {
    private val _sportFacilityList = mutableStateListOf<SportFacility>()
    var errorMessage: String by mutableStateOf("")
    val sportFacilityList: List<SportFacility>
        get() = _sportFacilityList

    fun getSportFacilityList() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _sportFacilityList.clear()
                _sportFacilityList.addAll(apiService.getSportFacilities())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}