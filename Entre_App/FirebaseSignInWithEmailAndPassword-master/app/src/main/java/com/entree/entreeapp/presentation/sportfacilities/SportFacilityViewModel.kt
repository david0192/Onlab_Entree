package com.entree.entreeapp.presentation.sportfacilities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.entree.entreeapp.apiservice.APIService
import com.entree.entreeapp.apiservice.SportFacility

class SportFacilityViewModel: ViewModel() {
    private val _sportFacilityList = mutableStateListOf<SportFacility>()
    var errorMessage: String by mutableStateOf("")
    val sportFacilityList: List<SportFacility>
        get() = _sportFacilityList

    suspend fun getSportFacilityList() {
            val apiService = APIService.getInstance()
            try {
                errorMessage=""
                _sportFacilityList.clear()
                _sportFacilityList.addAll(apiService.getSportFacilities())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
    }
}