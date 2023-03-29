package com.example.entreeapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class TicketTypeViewModel : ViewModel() {
    private val _ticketTypeListDaily = mutableStateListOf<TicketType>()
    private val _ticketTypeListMonthly = mutableStateListOf<TicketType>()
    private val _ticketTypeListGroup = mutableStateListOf<TicketType>()
    private val _ticketTypeListTrainer = mutableStateListOf<TicketType>()
    var errorMessage: String by mutableStateOf("")
    val ticketTypeList_Daily: List<TicketType>
        get() = _ticketTypeListDaily
    val ticketTypeList_Monthly: List<TicketType>
        get() = _ticketTypeListMonthly
    val ticketTypeList_Group: List<TicketType>
        get() = _ticketTypeListGroup
    val ticketTypeList_Trainer: List<TicketType>
        get() = _ticketTypeListTrainer

    fun getTicketTypeList(id:Int?) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _ticketTypeListDaily.clear()
                _ticketTypeListDaily.addAll(apiService.getSportFacilitiesByIdCatId(id, 1))
                _ticketTypeListMonthly.clear()
                _ticketTypeListMonthly.addAll(apiService.getSportFacilitiesByIdCatId(id, 2))
                _ticketTypeListGroup.clear()
                _ticketTypeListGroup.addAll(apiService.getSportFacilitiesByIdCatId(id, 3))
                _ticketTypeListTrainer.clear()
                _ticketTypeListTrainer.addAll(apiService.getSportFacilitiesByIdCatId(id, 4))
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}