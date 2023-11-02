package com.entree.entreeapp.presentation.sportfacilities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.entree.entreeapp.apiservice.APIService
import com.entree.entreeapp.apiservice.Ticket
import com.entree.entreeapp.apiservice.TicketType


class TicketTypeViewModel : ViewModel() {
    private val _ticketTypeListDaily = mutableStateListOf<TicketType>()
    private val _ticketTypeListTrainer = mutableStateListOf<TicketType>()
    private val _ticketsOfGuest= mutableStateListOf<Ticket>()
    var errorMessage: String by mutableStateOf("")
    val ticketTypeList_Daily: List<TicketType>
        get() = _ticketTypeListDaily
    val ticketTypeList_Trainer: List<TicketType>
        get() = _ticketTypeListTrainer
    val ticketsofGuest:List<Ticket>
        get()=_ticketsOfGuest

    fun getTicketTypeList(id:Int?) {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                errorMessage=""
                _ticketTypeListDaily.clear()
                _ticketTypeListDaily.addAll(apiService.getSportFacilitiesByIdCatId(id, 1))
                _ticketTypeListTrainer.clear()
                _ticketTypeListTrainer.addAll(apiService.getSportFacilitiesByIdCatId(id, 3))
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getTicketsByEmail(email:String?){
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                errorMessage=""
                _ticketsOfGuest.clear()
                _ticketsOfGuest.addAll(apiService.getTicketsByEmail(email))

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun AddTicketToUser(ticketTypeId:Int?, email: String?){
        val apiService = APIService.getInstance()
        try {
            errorMessage=""
            viewModelScope.launch {
                apiService.AddTicketToUser(ticketTypeId, email)
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}